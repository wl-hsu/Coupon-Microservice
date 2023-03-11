package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * fake product information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsInfo {

    /** Product Types*/
    private Integer type;

    /** commodity price */
    private Double price;

    /** Product Quantity */
    private Integer count;


}
