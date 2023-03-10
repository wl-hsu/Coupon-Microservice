package wlh.coupon.service;

import wlh.coupon.entity.Coupon;
import wlh.coupon.exception.CouponException;

import java.util.List;

/**
 * Redis related operation service interface
 * 1. User's three status coupon Cache related operations
 * 2. Coupon code Cache operation generated by coupon template
 */
public interface IRedisService {

    /**
     * Find the cached coupon list data based on userId and status
     * @param userId user id
     * @param status coupon status {@link wlh.coupon.constant.CouponStatus}
     * @return {@link Coupon}s, May return null, which means there has never been a record
     * */
    List<Coupon> getCachedCoupons(Long userId, Integer status);

    /**
     * Save empty coupon list to cache
     * @param userId
     * @param status coupon status
     * */
    void saveEmptyCouponListToCache(Long userId, List<Integer> status);

    /**
     * Try to grab a coupon code from Cache<
     * @param templateId Coupon Template Primary Key
     * @return coupon code
     * */
    String tryToAcquireCouponCodeFromCache(Integer templateId);

    /**
     * Save Coupon to Cache
     * @param userId  user id
     * @param coupons {@link Coupon}s
     * @param status coupon status
     * @return number of successfully saved
     * */
    Integer addCouponToCache(Long userId, List<Coupon> coupons,
                             Integer status) throws CouponException;
}
