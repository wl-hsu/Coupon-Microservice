package wlh.coupon.service;

import wlh.coupon.entity.Coupon;
import wlh.coupon.exception.CouponException;
import wlh.coupon.vo.AcquireTemplateRequest;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.SettlementInfo;

import java.util.List;

/**
 *  user services Interface
 * 1. Coupon information display service for three types of user status
 * 2. view the coupon templates that users can currently receive
 *      - coupon-template microservices cooperating to implement
 * 3. User receives coupon service
 * 4. coupon service for User consumption
 *      - coupon-settlement microservice cooperating to implement
 * Created by Qinyi.
 */
public interface IUserService {

    /**
     * Query coupon records based on user id and status
     * @param userId user id
     * @param status coupon status
     * @return {@link Coupon}s
     * */
    List<Coupon> findCouponsByStatus(Long userId, Integer status)
            throws CouponException;

    /**
     * Find the coupon templates that can currently be claimed according to the user id
     * @param userId user id
     * @return {@link CouponTemplateSDK}s
     * */
    List<CouponTemplateSDK> findAvailableTemplate(Long userId)
        throws CouponException;

    /**
     * User receives coupon
     * @param request {@link AcquireTemplateRequest}
     * @return {@link Coupon}
     * */
    Coupon acquireTemplate(AcquireTemplateRequest request)
            throws CouponException;

    /**
     * Settlement
     * @param info {@link SettlementInfo}
     * @return {@link SettlementInfo}
     * */
    SettlementInfo settlement(SettlementInfo info) throws CouponException;

}
