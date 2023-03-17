package wlh.coupon.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permission Description Annotation: Define the permissions of the Controller
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CouponPermission {

    /**
     * description
     * */
    String description() default "";

    /**
     * read Only ?  default is true
     * */
    boolean readOnly() default true;

    /**
     * extended attributes
     * */
    String extra() default "";
}
