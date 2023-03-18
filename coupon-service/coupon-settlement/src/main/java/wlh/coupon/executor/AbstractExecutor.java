package wlh.coupon.executor;

import com.alibaba.fastjson.JSON;
import wlh.coupon.vo.GoodsInfo;
import wlh.coupon.vo.SettlementInfo;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Rule executor abstract class, defining common methods
 */
public abstract class AbstractExecutor {

    /**
     * Check whether the product type matches the coupon
     * 1.The verification of single-category coupons is implemented here,
     *      and this method is overloaded for multi-category coupons
     * 2. The product only needs to have a product type required by the coupon to match
     * */
    @SuppressWarnings("all")
    protected boolean isGoodsTypeSatisfy(SettlementInfo settlement) {

        List<Integer> goodsType = settlement.getGoodsInfos()
                .stream().map(GoodsInfo::getType)
                .collect(Collectors.toList());
        List<Integer> templateGoodsType = JSON.parseObject(
                settlement.getCouponAndTemplateInfos().get(0).getTemplate()
                        .getRule().getUsage().getGoodsType(),
                List.class
        );

        return CollectionUtils.isNotEmpty(
                CollectionUtils.intersection(goodsType, templateGoodsType)
        );
    }

    /**
     * Handle case where item type doesn't match coupon limit
     * @param settlement {@link SettlementInfo} Billing information passed by the user
     * @param goodsSum Total price
     * @return {@link SettlementInfo} Billing information that has been revised
     * */
    protected SettlementInfo processGoodsTypeNotSatisfy(
            SettlementInfo settlement, double goodsSum
    ) {

        boolean isGoodsTypeSatisfy = isGoodsTypeSatisfy(settlement);

        // When the product type is not satisfied, directly return the total price and clear the coupon
        if (!isGoodsTypeSatisfy) {
            settlement.setCost(goodsSum);
            settlement.setCouponAndTemplateInfos(Collections.emptyList());
            return settlement;
        }

        return null;
    }

    /**
     * Total price
     * */
    protected double goodsCostSum(List<GoodsInfo> goodsInfos) {

        return goodsInfos.stream().mapToDouble(
                g -> g.getPrice() * g.getCount()
        ).sum();
    }

    /**
     * two decimal places
     * */
    protected double retain2Decimals(double value) {

        return new BigDecimal(value).setScale(
                2, BigDecimal.ROUND_HALF_UP
        ).doubleValue();
    }

    /**
     * minimum payment fee
     * */
    protected double minCost() {

        return 0.1;
    }
}
