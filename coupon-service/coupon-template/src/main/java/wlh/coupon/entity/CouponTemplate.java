package wlh.coupon.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.DistributeTarget;
import wlh.coupon.constant.ProductLine;
import wlh.coupon.converter.CouponCategoryConverter;
import wlh.coupon.serialization.CouponTemplateSerialize;
import wlh.coupon.vo.TemplateRule;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Coupon template entity class definition: basic attributes + rule attributes
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name= "coupon_template")
@JsonSerialize(using = CouponTemplateSerialize.class)
public class CouponTemplate {

    /** self increase primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Transient
    private Integer id;

    /** available */
    @Column(name = "available", nullable = false)
    private Boolean available;

    /** expired */
    @Column(name = "expired", nullable = false)
    private Boolean expired;

    /** coupon name */
    @Column(name = "name", nullable = false)
    private String name;

    /** coupon logo */
    @Column(name = "logo", nullable = false)
    private String logo;

    /** coupon description */
    @Column(name = "intro", nullable = false)
    private String desc;

    /** coupon category */
    @Column(name = "category", nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private CouponCategory category;

    /** product line */
    @Column(name = "product_line", nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private ProductLine productLine;

    /** total count */
    @Column(name = "coupon_count", nullable = false)
    private Integer count;

    /** createTime */
    @CreatedDate
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /** create user */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** coupon template code */
    @Column(name = "template_key", nullable = false)
    private String key;

    /** distributed target */
    @Column(name = "target", nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private DistributeTarget target;

    /** template rule */
    @Column(name = "rule", nullable = false)
    @Convert(converter = CouponCategoryConverter.class)
    private TemplateRule rule;

    /**
     * constructor
     * */
    public CouponTemplate(String name, String logo, String desc, String category,
                          Integer productLine, Integer count, Long userId,
                          Integer target, TemplateRule rule) {

        this.available = false;
        this.expired = false;
        this.name = name;
        this.logo = logo;
        this.desc = desc;
        this.category = CouponCategory.of(category);
        this.productLine = ProductLine.of(productLine);
        this.count = count;
        this.userId = userId;
        // coupon code = 4digit(product line and goods type) + 8(Date 20220309) + id(four digits)
        this.key = productLine.toString() + category +
                new SimpleDateFormat("yyyyMMdd").format(new Date());
        this.target = DistributeTarget.of(target);
        this.rule = rule;
    }
}
