package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Settlement information
 * include
 *  1. userId
 *  2. Product information (list)
 *  3. Coupon List
 *  4. Settlement result amount
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementInfo {

    /** user id */
    private Long userId;

    /** product info */
    private List<GoodsInfo> goodsInfos;

    /** Coupon List */
    private List<CouponAndTemplateInfo> couponAndTemplateInfos;

    /** can be settlement? */
    private Boolean employ;

    /** Result of settlement /
     * After coupon used, how many money that customer should pay*/
    private Double cost;

    /**
     * Coupon and template information
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CouponAndTemplateInfo {

        /** Coupon id */
        private Integer id;

        private CouponTemplateSDK template;
    }
}
