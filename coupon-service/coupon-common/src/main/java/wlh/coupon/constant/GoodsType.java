package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Product type enumeration
 * For free to add what's your need
 */
@Getter
@AllArgsConstructor
public enum GoodsType {

    VIDEO("video", 1),
    MUSIC("music", 2),
    EBOOK("eBook", 3),
    OTHERS("others", 4),
    ALL("all", 5);

    /** Product type description*/
    private String description;

    /** Commodity Type Code */
    private Integer code;

    public static GoodsType of(Integer code) {

        Objects.requireNonNull(code);

        return Stream.of(values())
                .filter(bean -> bean.code.equals(code))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(code + " not exists!")
                );
    }
}
