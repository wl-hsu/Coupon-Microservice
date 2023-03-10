package wlh.coupon.converter;

import wlh.coupon.constant.CouponCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Coupon Category Enum Property Converter
 * AttributeConverter<X, Y>
 * X: is the type of the entity property
 * Y: is the type of the database field
 */
@Converter
public class CouponCategoryConverter implements AttributeConverter<CouponCategory, String> {

    /**
     * Convert the entity attribute X to Y and store it in the database,
     * the actions performed when inserting and updating
     * */
    @Override
    public String convertToDatabaseColumn(CouponCategory couponCategory) {
        return couponCategory.getCode();
    }

    /**
     * Convert the field Y in the database to the entity attribute X,
     * the action performed during the query operation
     * */
    @Override
    public CouponCategory convertToEntityAttribute(String code) {
        return CouponCategory.of(code);
    }
}
