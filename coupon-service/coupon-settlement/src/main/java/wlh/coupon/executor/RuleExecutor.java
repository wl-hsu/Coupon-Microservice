package wlh.coupon.executor;

import wlh.coupon.constant.RuleFlag;
import wlh.coupon.vo.SettlementInfo;

/**
 * Coupon template rule processor interface definition
 */
public interface RuleExecutor {

    /**
     * Rule Type Tag
     * @return {@link RuleFlag}
     * */
    RuleFlag ruleConfig();

    /**
     * Calculation of Coupon Rules
     * @param settlement {@link SettlementInfo} Contains selected coupons
     * @return {@link SettlementInfo} Revised billing information
     * */
    SettlementInfo computeRule(SettlementInfo settlement);
}
