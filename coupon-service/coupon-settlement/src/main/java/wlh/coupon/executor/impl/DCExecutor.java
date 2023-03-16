package wlh.coupon.executor.impl;

import wlh.coupon.constant.RuleFlag;
import wlh.coupon.executor.AbstractExecutor;
import wlh.coupon.executor.RuleExecutor;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * DC coupon settlement rule executor
 */
@Slf4j
@Component
public class DCExecutor extends AbstractExecutor implements RuleExecutor {

    /**
     * Rule Type Tag
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.DC;
    }

    /**
     * Calculation of Coupon Rules
     * @param settlement {@link SettlementInfo} Included selected coupons
     * @return {@link SettlementInfo} Revised billing information
     */
    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {

        double goodsSum = retain2Decimals(goodsCostSum(
                settlement.getGoodsInfos()
        ));
        SettlementInfo probability = processGoodsTypeNotSatisfy(
                settlement, goodsSum
        );
        if (null != probability) {
            log.debug("DC Template Is Not Match To GoodsType!");
            return probability;
        }

        // Instant discount coupons can be used directly, there is no threshold
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos()
                .get(0).getTemplate();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // Calculate the price after applying the coupon - checkout
        settlement.setCost(
                retain2Decimals(goodsSum - quota) > minCost() ?
                        retain2Decimals(goodsSum - quota) : minCost()
        );

        log.debug("Use DC Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }
}
