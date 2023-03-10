package wlh.coupon.service.impl;

import wlh.coupon.dao.CouponTemplateDao;
import wlh.coupon.entity.CouponTemplate;
import wlh.coupon.exception.CouponException;
import wlh.coupon.service.IAsyncService;
import wlh.coupon.service.IBuildTemplateService;
import wlh.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Build coupon template interface implementation
 */
@Slf4j
@Service
public class BuildTemplateServiceImpl implements IBuildTemplateService {

    /** asynchronous service */
    private final IAsyncService asyncService;

    /** CouponTemplate Dao */
    private final CouponTemplateDao templateDao;

    @Autowired
    public BuildTemplateServiceImpl(IAsyncService asyncService,
                                    CouponTemplateDao templateDao) {
        this.asyncService = asyncService;
        this.templateDao = templateDao;
    }

    /**
     * Create coupon templates
     * @param request {@link TemplateRequest} template information request object
     * @return {@link CouponTemplate} Coupon template entity
     */
    @Override
    public CouponTemplate buildTemplate(TemplateRequest request)
            throws CouponException {

        // Parameter validity check
        if (!request.validate()) {
            throw new CouponException("BuildTemplate Param Is Not Valid!");
        }

        // check whether a coupon template with the same name exists
        if (null != templateDao.findByName(request.getName())) {
            throw new CouponException("Exist Same Name Template!");
        }

        // Construct a CouponTemplate and save it to the database
        CouponTemplate template = requestToTemplate(request);
        template = templateDao.save(template);

        // Asynchronously generate coupon codes based on coupon templates
        asyncService.asyncConstructCouponByTemplate(template);

        return template;
    }

    /**
     * Convert TemplateRequest to CouponTemplate
     * */
    private CouponTemplate requestToTemplate(TemplateRequest request) {

        return new CouponTemplate(
                request.getName(),
                request.getLogo(),
                request.getDesc(),
                request.getCategory(),
                request.getProductLine(),
                request.getCount(),
                request.getUserId(),
                request.getTarget(),
                request.getRule()
        );
    }
}
