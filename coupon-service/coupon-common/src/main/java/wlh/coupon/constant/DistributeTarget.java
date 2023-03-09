package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Distribute target
 * This enumeration is used to classify different coupon distribution policy.
 * Coupon need to be taken by user.
 * Coupons are distributed to many users automatically.
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    SINGLE("Coupon is taken by a single user", 1),
    MULTI("Coupons are distributed to many users automatically", 2);

    /** Distribute target description*/
    private String description;

    /** Distribute target code*/
    private Integer code;

    public static DistributeTarget of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
