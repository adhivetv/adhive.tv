package adhive.commons.utils_vertx;

import io.vertx.core.http.HttpClientResponse;

/**
 * Util methods to work with Rest response
 */
public class RestRequestUtil {

    public static boolean hasBody(HttpClientResponse response) {
        String headerContentLength = response.getHeader("Content-Length");
        if (headerContentLength == null) {
            return false;
        }
        return Integer.parseInt(headerContentLength) != 0;
    }

    public static int getBodyLength(HttpClientResponse response) {
        if (hasBody(response)) {
            return Integer.parseInt(response.getHeader("Content-Length"));
        }
        return 0;
    }
}

