package wlh.coupon.converter;

import wlh.coupon.constant.CouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Coupon status enum field converter
 */
@Converter
public class CouponStatusConverter implements
        AttributeConverter<CouponStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CouponStatus status) {
        return status.getCode();
    }

    @Override
    public CouponStatus convertToEntityAttribute(Integer code) {
        return CouponStatus.of(code);
    }
}
