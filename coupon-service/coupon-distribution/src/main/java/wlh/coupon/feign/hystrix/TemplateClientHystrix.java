package wlh.coupon.feign.hystrix;

import wlh.coupon.feign.TemplateClient;
import wlh.coupon.vo.CommonResponse;
import wlh.coupon.vo.CouponTemplateSDK;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Circuit breaker downgrade strategy for coupon template Feign interface
 */
@Slf4j
@Component
public class TemplateClientHystrix implements TemplateClient {

    /**
     * Find all available coupon templates
     */
    @Override
    public CommonResponse<List<CouponTemplateSDK>> findAllUsableTemplate() {

        log.error("[eureka-client-coupon-template] findAllUsableTemplate " +
                "request error");
        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                Collections.emptyList()
        );
    }

    /**
     * Get the mapping from template ids to CouponTemplateSDK
     * @param ids coupon template id
     */
    @Override
    public CommonResponse<Map<Integer, CouponTemplateSDK>>
    findIds2TemplateSDK(Collection<Integer> ids) {

        log.error("[eureka-client-coupon-template] findIds2TemplateSDK" +
                "request error");

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-template] request error",
                new HashMap<>()
        );
    }
}
