package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Coupon template information definition used between microservices
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateSDK {

    /** coupon template primary key */
    private Integer id;

    /** coupon template name */
    private String name;

    /** coupon logo */
    private String logo;

    /** coupon description */
    private String desc;

    /** coupon category */
    private String category;

    /** productline */
    private Integer productLine;

    /** coupon template code */
    private String key;

    /** target user */
    private Integer target;

    /** coupon rule */
    private TemplateRule rule;
}
