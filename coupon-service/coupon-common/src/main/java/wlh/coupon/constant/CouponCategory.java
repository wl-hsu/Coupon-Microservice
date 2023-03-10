package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Type of Coupon
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {

    /**
     * DTOC  Dollars-Off-Total-Order Coupons
     * It is something like if you purchase 100 prices, this coupon can give you 5 dollars off
     * PTC Percentages-off Coupon
     * It is the simple Percentages-off coupon
     * DC Dollars-off Coupon
     * It is simple dollars off coupon
     */
    DTOC("Dollars-Off-Total-Order Coupons", "001"),
    PTC("Percentages-off Coupon", "002"),
    DC("Dollars-off Coupon", "003");

    /** Coupon Description (Category)*/
    private String description;

    /** Coupon Category Code */
    private String code;

    public static CouponCategory of(String code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
