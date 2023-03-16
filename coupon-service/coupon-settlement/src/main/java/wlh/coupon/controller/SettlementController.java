package wlh.coupon.controller;

import com.alibaba.fastjson.JSON;
import wlh.coupon.exception.CouponException;
import wlh.coupon.executor.ExecuteManager;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  Controller
 */
@Slf4j
@RestController
public class SettlementController {

    /** Settlement Rule Execution Manager */
    private final ExecuteManager executeManager;

    @Autowired
    public SettlementController(ExecuteManager executeManager) {
        this.executeManager = executeManager;
    }

    /**
     * <h2>Coupon Settlement</h2>
     * 127.0.0.1:7003/coupon-settlement/settlement/compute
     * 127.0.0.1:9000/coupon-settlement/settlement/compute
     * */
    @PostMapping("/settlement/compute")
    public SettlementInfo computeRule(@RequestBody SettlementInfo settlement)
            throws CouponException {
        log.info("settlement: {}", JSON.toJSONString(settlement));
        return executeManager.computeRule(settlement);
    }
}
