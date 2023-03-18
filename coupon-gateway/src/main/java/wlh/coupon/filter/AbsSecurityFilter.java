package wlh.coupon.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract permission filter
 */
@Slf4j
public abstract class AbsSecurityFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();

        // If the previous filter fails to execute, the subsequent filter will not be called

        return response.getStatus() == 0
                || response.getStatus() == HttpStatus.SC_OK;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        HttpServletResponse response = context.getResponse();

        log.info("filter {} begin check request {}.",
                this.getClass().getSimpleName(), request.getRequestURI());

        Boolean result = null;
        try {
            result = interceptCheck(request, response);
        } catch (Exception ex) {
            log.error("filter {} check request {}, throws exception {}.",
                    this.getClass().getSimpleName(),
                    request.getRequestURI(), ex.getMessage());
        }

        log.info("filter {} finish check, result {}",
                this.getClass().getSimpleName(), result);

        if (result == null) {
            log.debug("Filter {} finish check, result is null.",
                    this.getClass().getSimpleName());

            // Do not route the current request
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(getHttpStatus());
            return null;
        }

        if (!result) {

            try {
                // Do not route the current request
                context.setSendZuulResponse(false);
                context.setResponseStatusCode(getHttpStatus());
                response.setHeader("Content-type",
                        "application/json;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(getErrorMsg());
                context.setResponse(response);
            } catch (IOException ex) {
                log.error("filter {} check request {}, result is false," +
                        "setResponse throws Exception {}",
                        this.getClass().getSimpleName(),
                        request.getRequestURI(), ex.getMessage());
            }
        }

        return null;
    }

    /**
     * Sub-Filter implements this method and fills in the validation logic
     * @return true: pass Validation; false: Validation failed
     * */
    protected abstract Boolean interceptCheck(HttpServletRequest request,
                                              HttpServletResponse response)
        throws Exception;

    protected abstract int getHttpStatus();

    protected abstract String getErrorMsg();
}
