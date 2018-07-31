package adhive.commons.utils_vertx.testing;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Factory for mocking Vertx routingContext
 */
public class HttpContextFactory {

    public static RoutingContext buildContext(HttpServerRequest request, HttpServerResponse response) {
        RoutingContext routingContext = mock(RoutingContext.class);
        when(routingContext.request())
                .thenReturn(request);
        when(routingContext.response())
                .thenReturn(response);
        return routingContext;
    }

    public static HttpServerResponse buildResponse() {
        HttpServerResponse response = mock(HttpServerResponse.class);
        when(response.setStatusCode(anyInt())).thenReturn(response);
        return response;
    }

    public static HttpServerRequest buildRequest(Map<String, String> params) {
        HttpServerRequest request = mock(HttpServerRequest.class);

        params.forEach((k, v) -> when(request.getParam(k)).thenReturn(v));

        return request;
    }
}
