package wlh.coupon.dao;

import wlh.coupon.constant.CouponStatus;
import wlh.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Coupon Dao
 */
public interface CouponDao extends JpaRepository<Coupon, Integer> {

    /**
     * according to userId + Status to look for Coupon Records
     * where userId = ... and status = ...
     * */
    List<Coupon> findAllByUserIdAndStatus(Long userId, CouponStatus status);
}
