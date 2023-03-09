package wlh.coupon.vo;

import wlh.coupon.constant.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Coupon Rule Object Definition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateRule {

    /** coupon expired day */
    private Expiration expiration;

    /** discount*/
    private Discount discount;

    /** How many coupon can be taken by a single user.*/
    private Integer limitation;

    /** Usage range: location(City, State) + goods type */
    private Usage usage;

    /** Which coupon can be used with other one?
        The same kind of coupon cannot be used with in one trading.*/
    private String weight;

    /**
     * validation
     * */
    public boolean validate() {

        return expiration.validate() && discount.validate()
                && limitation > 0 && usage.validate()
                && StringUtils.isNotEmpty(weight);
    }

    /**
     * expired day rule
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Expiration {

        /** Validity period rules, corresponding to the code field of PeriodType*/
        private Integer period;

        /** Valid Interval: Valid only for variable validity periods */
        private Integer gap;

        /** The expiration date of the coupon template,
         *  both types of rules are valid.
         *  both types is mean the regular and shift in wlh.coupon.constant.PeriodType
         *  */
        private Long deadline;

        boolean validate() {
            return null != PeriodType.of(period) && gap > 0 && deadline > 0;
        }
    }

    /**
     * Discount need to consider the situation that user use many types of coupon
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {

        /** Total of discount quota
         *  We need to calculate total dollars off. For example,
         *  When user use Dollars-Off-Total-Order(discount 20 dollars),
         *  use Percentages-off Coupon(10% off), and
         *  use Dollars-off Coupon(5 dollars off) */
        private Integer quota;

        /** This field only use when users use Dollars-Off-Total-Order Coupons */
        private Integer base;

        boolean validate() {

            return quota > 0 && base > 0;
        }
    }

    /**
     * Coupon using range
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Usage {

        /**
         *  State
         *  The coupon can be used in which state
         * */
        private String state;
        /**
         *  City
         *  The coupon can be used in which city
         */
        private String city;

        /**
         *  The coupon can be used in which goods type
         *  list[soft drink, fresh, all goods]
         * */
        private String goodsType;

        boolean validate() {

            return StringUtils.isNotEmpty(state)
                    && StringUtils.isNotEmpty(city)
                    && StringUtils.isNotEmpty(goodsType);
        }
    }
}
