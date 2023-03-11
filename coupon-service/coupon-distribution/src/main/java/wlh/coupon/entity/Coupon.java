package wlh.coupon.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import wlh.coupon.constant.CouponStatus;
import wlh.coupon.converter.CouponStatusConverter;
import wlh.coupon.serialization.CouponSerialize;
import wlh.coupon.vo.CouponTemplateSDK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Coupons (records of coupons received by users) entity table
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "coupon")
@JsonSerialize(using = CouponSerialize.class)
public class Coupon {

    /** auto increment primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    /** The primary key (logical foreign key) of the associated coupon template */
    @Column(name = "template_id", nullable = false)
    private Integer templateId;

    /** received coupon user */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /** coupon code */
    @Column(name = "coupon_code", nullable = false)
    private String couponCode;

    /** pick up time */
    @CreatedDate
    @Column(name = "assign_time", nullable = false)
    private Date assignTime;

    /** coupon status */
    @Column(name = "status", nullable = false)
    @Convert(converter = CouponStatusConverter.class)
    private CouponStatus status;

    /** Template information corresponding to user coupons */
    @Transient
    private CouponTemplateSDK templateSDK;

    /**
     * returns an invalid Coupon object
     * */
    public static Coupon invalidCoupon() {

        Coupon coupon = new Coupon();
        coupon.setId(-1);
        return coupon;
    }

    /**
     * Constructing coupons
     * */
    public Coupon(Integer templateId, Long userId, String couponCode,
                  CouponStatus status) {

        this.templateId = templateId;
        this.userId = userId;
        this.couponCode = couponCode;
        this.status = status;
    }
}
