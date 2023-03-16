package wlh.coupon.executor.impl;

import wlh.coupon.constant.RuleFlag;
import wlh.coupon.executor.AbstractExecutor;
import wlh.coupon.executor.RuleExecutor;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * DC rule executor
 * */
@Slf4j
@Component
public class PTCExecutor extends AbstractExecutor implements RuleExecutor {

    /**
     * Rule Type Tag
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.PTC;
    }

    /**
     * Calculation of Coupon Rules
     * @param settlement {@link SettlementInfo} include selected coupons
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
            log.debug("PTC Template Is Not Match GoodsType!");
            return probability;
        }

        // DC can be used directly without threshold
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos()
                .get(0).getTemplate();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // Calculate the price after using the coupon
        settlement.setCost(
                retain2Decimals((goodsSum * (quota * 1.0 / 100))) > minCost() ?
                        retain2Decimals((goodsSum * (quota * 1.0 / 100)))
                        : minCost()
        );
        log.debug("Use PTC Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }
}
