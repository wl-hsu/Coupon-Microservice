package wlh.coupon.service;

import wlh.coupon.entity.CouponTemplate;

/**
 * Asynchronous service interface definition
 */
public interface IAsyncService {

    /**
     * Asynchronously create coupon codes based on templates
     * @param template {@link CouponTemplate} Coupon template entity
     * */
    void asyncConstructCouponByTemplate(CouponTemplate template);
}
