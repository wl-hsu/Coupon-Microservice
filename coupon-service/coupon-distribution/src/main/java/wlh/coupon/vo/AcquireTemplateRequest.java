package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Get coupon request object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcquireTemplateRequest {

    /** user id */
    private Long userId;

    /** Coupon Template Information */
    private CouponTemplateSDK templateSDK;
}
