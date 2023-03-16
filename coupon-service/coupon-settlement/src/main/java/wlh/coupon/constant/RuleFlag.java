package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Rule Type Enumeration Definition
 */
@Getter
@AllArgsConstructor
public enum RuleFlag {

    // Single Category Coupon Definition
    DTOC("Calculation rules for Dollars-Off-Total-Order Coupons"),
    PTC("Calculation rules for Percentages-off Coupon"),
    DC("Calculation rules Dollars-off Coupon"),


    // Multi-Category Coupon Definition
    DTOC_PTC("Calculation rules for DTOC + PTC");

    // TODO add more combination

    /** rule description */
    private String description;
}
