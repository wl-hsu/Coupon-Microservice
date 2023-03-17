package wlh.coupon.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * user Role enum
 */
@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN("admin"),
    SUPER_ADMIN("super admin"),
    CUSTOMER("user");

    private String roleName;
}
