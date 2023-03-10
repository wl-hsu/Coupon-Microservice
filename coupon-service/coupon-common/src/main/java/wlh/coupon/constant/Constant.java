package wlh.coupon.constant;

/**
 * Generally used constant definitions
 */
public class Constant {

    /** Kafka message Topic */
    public static final String TOPIC = "user_coupon_op";

    /**
     * Redis Key prefix definition
     * */
    public static class RedisPrefix {

        /** coupon code key prefix*/
        public static final String COUPON_TEMPLATE =
                "coupon_template_code_";

        /** All currently available coupon key prefixes for the user */
        public static final String USER_COUPON_USABLE =
                "user_coupon_usable_";

        /** All currently used coupon key prefixes of the user*/
        public static final String USER_COUPON_USED =
                "user_coupon_used_";

        /** The key prefix of all the user's current expired coupons */
        public static final String USER_COUPON_EXPIRED =
                "user_coupon_expired_";
    }
}
