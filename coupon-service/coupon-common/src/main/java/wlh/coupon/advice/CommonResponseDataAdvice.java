package wlh.coupon.advice;

import wlh.coupon.annotation.IgnoreResponseAdvice;
import wlh.coupon.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


/**
 * Common Response
 */
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

    /**
     * Determine whether the response needs to be processed
     * */
    @Override
    @SuppressWarnings("all")
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {

        /**
         * If the class where the current method is located identifies the
         * @IgnoreResponseAdvice annotation, no processing is required
         */
        if (methodParameter.getDeclaringClass().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        /**
         * If the current method identifies the
         * @IgnoreResponseAdvice annotation, no processing is required
         */
        if (methodParameter.getMethod().isAnnotationPresent(
                IgnoreResponseAdvice.class
        )) {
            return false;
        }

        // Process the response and execute the beforeBodyWrite method
        return true;
    }

    /**
     * Process object Before it's returned
     * */
    @Override
    @SuppressWarnings("all")
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        // Define the final return object
        CommonResponse<Object> response = new CommonResponse<>(
                0, ""
        );

        // If o is null, response does not need to set data
        if (null == o) {
            return response;
            // If o is already a CommonResponse, no need to process it again
        } else if (o instanceof CommonResponse) {
            response = (CommonResponse<Object>) o;
            // Otherwise, make the response object as the data part of CommonResponse
        } else {
            response.setData(o);
        }

        return response;
    }
}
