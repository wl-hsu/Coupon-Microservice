package wlh.coupon.vo;

import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.DistributeTarget;
import wlh.coupon.constant.ProductLine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Coupon template creation request object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRequest {

    /** coupon name */
    private String name;

    /** coupon logo */
    private String logo;

    /** coupon description */
    private String desc;

    /** coupon category */
    private String category;

    /** productLine */
    private Integer productLine;

    /** total amount */
    private Integer count;

    /** create user */
    private Long userId;

    /** target user */
    private Integer target;

    /** coupon rule */
    private TemplateRule rule;

    /**
     * <verify object
     * */
    public boolean validate() {

        boolean stringValid = StringUtils.isNotEmpty(name)
                && StringUtils.isNotEmpty(logo)
                && StringUtils.isNotEmpty(desc);
        boolean enumValid = null != CouponCategory.of(category)
                && null != ProductLine.of(productLine)
                && null != DistributeTarget.of(target);
        boolean numValid = count > 0 && userId > 0;

        return stringValid && enumValid && numValid && rule.validate();
    }
}
