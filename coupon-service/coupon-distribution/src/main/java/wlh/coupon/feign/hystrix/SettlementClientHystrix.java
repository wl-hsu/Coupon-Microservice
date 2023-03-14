package wlh.coupon.feign.hystrix;

import wlh.coupon.exception.CouponException;
import wlh.coupon.feign.SettlementClient;
import wlh.coupon.vo.CommonResponse;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * settlement microservice circuit breaker strategy
 */
@Slf4j
@Component
public class SettlementClientHystrix implements SettlementClient {

    /**
     * Coupon rule calculation
     * @param settlement {@link SettlementInfo}
     */
    @Override
    public CommonResponse<SettlementInfo> computeRule(SettlementInfo settlement)
            throws CouponException {

        log.error("[eureka-client-coupon-settlement] computeRule" +
                "request error");

        settlement.setEmploy(false);
        settlement.setCost(-1.0);

        return new CommonResponse<>(
                -1,
                "[eureka-client-coupon-settlement] request error",
                settlement
        );
    }
}
