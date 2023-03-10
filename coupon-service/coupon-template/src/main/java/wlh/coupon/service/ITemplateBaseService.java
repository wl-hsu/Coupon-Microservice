package wlh.coupon.service;

import wlh.coupon.entity.CouponTemplate;
import wlh.coupon.exception.CouponException;
import wlh.coupon.vo.CouponTemplateSDK;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * oupon template basic service definition(view, delete...)
 */
public interface ITemplateBaseService {

    /**
     * Get coupon template information according to the coupon template id
     * @param id template id
     * @return {@link CouponTemplate} Coupon template entity
     * */
    CouponTemplate buildTemplateInfo(Integer id) throws CouponException;

    /**
     * Find all available coupon templates
     * @return {@link CouponTemplateSDK}s
     * */
    List<CouponTemplateSDK> findAllUsableTemplate();

    /**
     * Get the mapping from template ids to CouponTemplateSDK
     * @param ids templates ids
     * @return Map<key: template id， value: CouponTemplateSDK>
     * */
    Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(Collection<Integer> ids);
}
