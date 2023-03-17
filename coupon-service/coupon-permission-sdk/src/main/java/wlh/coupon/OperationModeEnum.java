package wlh.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * operation mode enum
 */
@Getter
@AllArgsConstructor
public enum OperationModeEnum {

    READ("read"),
    WRITE("write");

    private String mode;
}
