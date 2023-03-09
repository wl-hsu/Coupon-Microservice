package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * validity period type
 */
@Getter
@AllArgsConstructor
public enum PeriodType {
    //The expired day is fixed
    REGULAR("regular", 1),
    //The expired day depends on when the user takes the coupon.
    SHIFT("shift", 2);

    /** validity period type description */
    private String description;

    /** validity period type code */
    private Integer code;

    public static PeriodType of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
