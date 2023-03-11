package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Status of user coupons
 */
@Getter
@AllArgsConstructor
public enum CouponStatus {

    USABLE("can be used", 1),
    USED("has been used", 2),
    EXPIRED("expired(hadn't been used)", 3);

    /** status description */
    private String description;

    /** status code */
    private Integer code;

    /**
     * <h2>according to code to get CouponStatus</h2>
     * */
    public static CouponStatus of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists")
                );
    }
}
