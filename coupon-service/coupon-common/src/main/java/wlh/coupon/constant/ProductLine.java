package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * product line enumeration
 * For example: in amazon website, they have general amazon seller and Whole Foods Market.
 * This enumeration class is use to distinguish different product line.
 * It can help to implement that specific coupon can only be used in specific product line.
 */
@Getter
@AllArgsConstructor
public enum ProductLine {

    GLAN("general amazon", 1),
    WEFD("whole food", 2);

    /** product line description */
    private String description;

    /** product line code */
    private Integer code;

    public static ProductLine of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " not exists!"));
    }
}
