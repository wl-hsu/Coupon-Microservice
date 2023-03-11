package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Coupon kafka message object definition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponKafkaMessage {

    /** coupon status */
    private Integer status;

    /** Coupon primary key */
    private List<Integer> ids;
}
