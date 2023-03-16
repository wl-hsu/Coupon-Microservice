package wlh.coupon.executor.impl;

import com.alibaba.fastjson.JSON;
import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.RuleFlag;
import wlh.coupon.executor.AbstractExecutor;
import wlh.coupon.executor.RuleExecutor;
import wlh.coupon.vo.GoodsInfo;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTOC + PTC

 */
@Slf4j
@Component
public class DTOCPTCExecutor extends AbstractExecutor
        implements RuleExecutor {

    /**
     * Rule Type Tag
     * @return {@link RuleFlag}
     */
    @Override
    public RuleFlag ruleConfig() {
        return RuleFlag.DTOC_PTC;
    }

    /**
     * Check whether the product type matches the coupon
     * Note:
     * 1. The verification of DTOC + PTC coupons implemented here
     * 2.If want to use multiple types of coupons, must include all product types,
     *   that is, the difference set is empty
     * @param settlement {@link SettlementInfo} Calculation information passed by the user
     */
    @Override
    @SuppressWarnings("all")
    protected boolean isGoodsTypeSatisfy(SettlementInfo settlement) {

        log.debug("Check DTOC And PTC Is Match Or Not!");
        List<Integer> goodsType = settlement.getGoodsInfos().stream()
                .map(GoodsInfo::getType).collect(Collectors.toList());
        List<Integer> templateGoodsType = new ArrayList<>();

        settlement.getCouponAndTemplateInfos().forEach(ct -> {

            templateGoodsType.addAll(JSON.parseObject(
                    ct.getTemplate().getRule().getUsage().getGoodsType(),
                    List.class
            ));

        });

        // If want to use multiple types of coupons, must include all product types,
        // that is, the difference set is empty
        return CollectionUtils.isEmpty(CollectionUtils.subtract(
                goodsType, templateGoodsType
        ));
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
        // Product type verification
        SettlementInfo probability = processGoodsTypeNotSatisfy(
                settlement, goodsSum
        );
        if (null != probability) {
            log.debug("DTOC And PTC Template Is Not Match To GoodsType!");
            return probability;
        }

        SettlementInfo.CouponAndTemplateInfo DTOC = null;
        SettlementInfo.CouponAndTemplateInfo PTC = null;

        for (SettlementInfo.CouponAndTemplateInfo ct :
                settlement.getCouponAndTemplateInfos()) {
            if (CouponCategory.of(ct.getTemplate().getCategory()) ==
                    CouponCategory.DTOC) {
                DTOC = ct;
            } else {
                PTC = ct;
            }
        }

        assert null != DTOC;
        assert null != PTC;

        // If the current discount coupon and full discount coupon cannot be shared (used together),
        // clear the coupon and return to the original price of the product
        if (!isTemplateCanShared(DTOC, PTC)) {
            log.debug("Current DTOC And PTC Can Not Shared!");
            settlement.setCost(goodsSum);
            settlement.setCouponAndTemplateInfos(Collections.emptyList());
            return settlement;
        }

        List<SettlementInfo.CouponAndTemplateInfo> ctInfos = new ArrayList<>();
        double DTOCBase = (double) DTOC.getTemplate().getRule()
                .getDiscount().getBase();
        double DTOCQuota = (double) PTC.getTemplate().getRule()
                .getDiscount().getQuota();

        // final price
        double targetSum = goodsSum;

        // First calculate the DTOC
        if (targetSum >= DTOCBase) {
            targetSum -= DTOCQuota;
            ctInfos.add(DTOC);
        }

        // and then calculate PTC
        double PTCQuota = (double) PTC.getTemplate().getRule()
                .getDiscount().getQuota();
        targetSum *= PTCQuota * 1.0 / 100;
        ctInfos.add(PTC);

        settlement.setCouponAndTemplateInfos(ctInfos);
        settlement.setCost(retain2Decimals(
                targetSum > minCost() ? targetSum : minCost()
        ));

        log.debug("Use DTOC And PTC Coupon Make Goods Cost From {} To {}",
                goodsSum, settlement.getCost());

        return settlement;
    }

    /**
     * Whether the current two coupons can be used together
     * That is to check whether the weight in TemplateRule meets the conditions
     * */
    @SuppressWarnings("all")
    private boolean
    isTemplateCanShared(SettlementInfo.CouponAndTemplateInfo DTOC,
                        SettlementInfo.CouponAndTemplateInfo PTC) {

        String DTOCKey = DTOC.getTemplate().getKey()
                + String.format("%04d", DTOC.getTemplate().getId());
        String PTCKey = PTC.getTemplate().getKey()
                + String.format("%04d", PTC.getTemplate().getId());

        List<String> allSharedKeysForDTOC = new ArrayList<>();
        allSharedKeysForDTOC.add(DTOCKey);
        allSharedKeysForDTOC.addAll(JSON.parseObject(
                DTOC.getTemplate().getRule().getWeight(),
                List.class
        ));

        List<String> allSharedKeysForPTC = new ArrayList<>();
        allSharedKeysForPTC.add(PTCKey);
        allSharedKeysForPTC.addAll(JSON.parseObject(
                PTC.getTemplate().getRule().getWeight(),
                List.class
        ));

        return CollectionUtils.isSubCollection(
                Arrays.asList(DTOCKey, PTCKey), allSharedKeysForDTOC)
                || CollectionUtils.isSubCollection(
                Arrays.asList(DTOCKey, PTCKey), allSharedKeysForPTC
        );
    }
}
