package wlh.coupon.executor.impl;

import wlh.coupon.constant.RuleFlag;
import wlh.coupon.executor.AbstractExecutor;
import wlh.coupon.executor.RuleExecutor;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * DTOC coupon settlement rule executor
 */
@Slf4j
@Component
public class DTOCExecutor extends AbstractExecutor implements RuleExecutor {

    /**
     * Rule Type Tag
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.DTOC;
    }

    /**
     * Calculation of Coupon Rules
     * @param settlement {@link SettlementInfo} included selected coupons
     * @return {@link SettlementInfo} Revised billing information
     */
    @Override
    public SettlementInfo computeRule(SettlementInfo settlement) {

        double goodsSum = retain2Decimals(
                goodsCostSum(settlement.getGoodsInfos())
        );
        SettlementInfo probability = processGoodsTypeNotSatisfy(
                settlement, goodsSum
        );
        if (null != probability) {
            log.debug("DTOC Template Is Not Match To GoodsType!");
            return probability;
        }

        // Determine whether discount meets the discount standard(DTOC)
        CouponTemplateSDK templateSDK = settlement.getCouponAndTemplateInfos()
                .get(0).getTemplate();
        double base = (double) templateSDK.getRule().getDiscount().getBase();
        double quota = (double) templateSDK.getRule().getDiscount().getQuota();

        // If the standard is not met, the total price of the product will be returned directly
        if (goodsSum < base) {
            log.debug("Current Goods Cost Sum < DTOC Coupon Base!");
            settlement.setCost(goodsSum);
            settlement.setCouponAndTemplateInfos(Collections.emptyList());
            return settlement;
        }

        // Calculate the price after applying the coupon - checkout
        settlement.setCost(retain2Decimals(
                (goodsSum - quota) > minCost() ? (goodsSum - quota) : minCost()
        ));
        log.debug("Use DTOC Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }
}
