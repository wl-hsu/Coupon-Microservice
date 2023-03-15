package wlh.coupon.controller;

import com.alibaba.fastjson.JSON;
import wlh.coupon.entity.Coupon;
import wlh.coupon.exception.CouponException;
import wlh.coupon.service.IUserService;
import wlh.coupon.vo.AcquireTemplateRequest;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User Service Controller
 */
@Slf4j
@RestController
public class UserServiceController {

    /** user service*/
    private final IUserService userService;

    @Autowired
    public UserServiceController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Find user coupon records based on user id and coupon status
     * */
    @GetMapping("/coupons")
    public List<Coupon> findCouponsByStatus(
            @RequestParam("userId") Long userId,
            @RequestParam("status") Integer status) throws CouponException {

        log.info("Find Coupons By Status: {}, {}", userId, status);
        return userService.findCouponsByStatus(userId, status);
    }

    /**
     * Find the coupon templates that can currently be claimed according to the user id
     * */
    @GetMapping("/template")
    public List<CouponTemplateSDK> findAvailableTemplate(
            @RequestParam("userId") Long userId) throws CouponException {

        log.info("Find Available Template: {}", userId);
        return userService.findAvailableTemplate(userId);
    }

    /**
     * User receives coupon
     * */
    @PostMapping("/acquire/template")
    public Coupon acquireTemplate(@RequestBody AcquireTemplateRequest request)
            throws CouponException {

        log.info("Acquire Template: {}", JSON.toJSONString(request));
        return userService.acquireTemplate(request);
    }

    /**
     * Settlement with coupon
     * */
    @PostMapping("/settlement")
    public SettlementInfo settlement(@RequestBody SettlementInfo info)
            throws CouponException {

        log.info("Settlement: {}", JSON.toJSONString(info));
        return userService.settlement(info);
    }
}
