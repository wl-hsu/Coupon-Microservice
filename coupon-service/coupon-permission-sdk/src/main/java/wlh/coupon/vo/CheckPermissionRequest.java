package wlh.coupon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * permission verification request object definition
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPermissionRequest {

   private Long userId;
   private String uri;
   private String httpMethod;
}
