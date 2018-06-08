package adhive.commons.utils_vertx;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClientResponse;

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
 *    2. Using  adhive.commons.utils_vertx.WholeResponseWrapper
 *         client.request(HttpMethod.GET, "/api/example/get_heavy_body", response ->
 *              adhive.commons.utils_vertx.WholeResponseWrapper.builder(body -> {
 *                  String fullBody = body.toString();  //Inside this block you can work with full body don't think about async
 *              }).handle(response)).end();
 *
 *
 */
public class WholeResponseWrapper implements Handler<HttpClientResponse> {

    private final StringBuffer total = new StringBuffer();
    private Handler<Buffer> endHandler;

    public static WholeResponseWrapper builder(Handler<Buffer> endHandler) {
        WholeResponseWrapper handler = new WholeResponseWrapper();
        handler.endHandler = endHandler;
        return handler;
    }

    @Override
    public void handle(HttpClientResponse response) {
        int contentLength = RestRequestUtil.getBodyLength(response);
        response.handler(buffer -> {
            total.append(buffer.toString());
            if (contentLength == total.length()) {
                endHandler.handle(Buffer.buffer(total.toString()));
            }
        });
    }
}

