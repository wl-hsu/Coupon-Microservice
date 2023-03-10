package wlh.coupon.service;

import wlh.coupon.entity.CouponTemplate;
import wlh.coupon.exception.CouponException;
import wlh.coupon.vo.TemplateRequest;

/**
 * create coupon template interface definition
 */
public interface IBuildTemplateService {

    /**
     * create coupon template
     * @param request {@link TemplateRequest} template information request object
     * @return {@link CouponTemplate} return coupon template object
     * */
    CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException;
}
