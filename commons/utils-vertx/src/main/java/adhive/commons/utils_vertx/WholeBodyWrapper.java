package adhive.commons.utils_vertx;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.buffer.impl.BufferImpl;

import java.nio.charset.StandardCharsets;


/**
 *    Used with Vertx client, wraps http response body and buffer it.
 *  When you use simple handler without buffering you need to think
 *  about async receiving body by pieces from network if it's large.
 *  This class solve this problem
 *
 *  Example:
 *    1. Without this wrapper.
 *         StringBuffer sb = new StringBuffer();
 *         BlockingQueue<String> q = new ArrayBlockingQueue<>(1);
 *         client.request(HttpMethod.GET, "/api/example/get_heavy_body", response ->
 *                 response.handler(body -> {
 *                     sb.append(body.toString());
 *                     if (Long.parseLong(response.getHeader("Content-Length")) == sb.length()) {
 *                         try {
 *                             q.put(sb.toString());
 *                         } catch (InterruptedException e) {
 *                             LOGGER.error(e.getMessage(), e);
 *                             Thread.currentThread().interrupt();
 *                         }
 *                     }
 *                 })).end();
 *          String fullBody = q.poll(10,TimeUnit.SECONDS);
 *
 *    2. Using  adhive.commons.utils_vertx.WholeBodyWrapper
 *         int size = RestRequestUtil.getBodyLength(response);
 *         boolean chunked = response.headers().contains(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED,true);
 *         response.handler(
 *               new WholeBodyWrapper(size, chunked, body -> {
 *                    //Here you work woth full body
 *                }));
 */
public class WholeBodyWrapper implements Handler<Buffer> {
    private final int size;
    private final boolean chunked;
    private final StringBuffer response;
    private final Buffer wholeBuffer;
    private Handler<Buffer> endHandler;

    public WholeBodyWrapper(int size, boolean chunked, Handler<Buffer> endHandler) {
        this.size = size;
        this.chunked = chunked;
        this.endHandler = endHandler;
        if (chunked) {
            wholeBuffer = new BufferImpl();
            response = null;
        } else {
            wholeBuffer = null;
            response = new StringBuffer();
        }
    }

    public String getChunked(){
        return wholeBuffer.toString(StandardCharsets.UTF_8);
    }

    @Override
    public void handle(Buffer buffer) {
        if (chunked) {
            wholeBuffer.appendBuffer(buffer);
        } else {
            response.append(buffer.toString());
            if (size == response.length()) {
                endHandler.handle(Buffer.buffer(response.toString()));
            }
        }
    }
}
