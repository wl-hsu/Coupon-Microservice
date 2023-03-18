package wlh.coupon.controller;

import com.alibaba.fastjson.JSON;
import wlh.coupon.annotation.CouponPermission;
import wlh.coupon.annotation.IgnorePermission;
import wlh.coupon.entity.CouponTemplate;
import wlh.coupon.exception.CouponException;
import wlh.coupon.service.IBuildTemplateService;
import wlh.coupon.service.ITemplateBaseService;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.TemplateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Coupon template related function controller
 */
@Slf4j
@RestController
public class CouponTemplateController {

    /** Build Coupon Template Service */
    private final IBuildTemplateService buildTemplateService;

    /** Coupon Template Basic Service */
    private final ITemplateBaseService templateBaseService;

    @Autowired
    public CouponTemplateController(IBuildTemplateService buildTemplateService,
                                    ITemplateBaseService templateBaseService) {
        this.buildTemplateService = buildTemplateService;
        this.templateBaseService = templateBaseService;
    }

    /**
     * Build a Coupon Template
     * 127.0.0.1:7001/coupon-template/template/build
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/build
     * */
    @PostMapping("/template/build")
    @CouponPermission(description = "build coupon template", readOnly = false)
    public CouponTemplate buildTemplate(@RequestBody TemplateRequest request)
            throws CouponException {
        log.info("Build Template: {}", JSON.toJSONString(request));
        return buildTemplateService.buildTemplate(request);
    }

    /**
     * Construct Coupon Template Details
     * 127.0.0.1:7001/coupon-template/template/info?id=1
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/info?id=x
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/info?id=1
     * */
    @GetMapping("/template/info")
    @CouponPermission(description = "get template info")
    public CouponTemplate buildTemplateInfo(@RequestParam("id") Integer id)
            throws CouponException {
        log.info("Build Template Info For: {}", id);
        return templateBaseService.buildTemplateInfo(id);
    }

    /**
     * Find all available coupon templates
     * 127.0.0.1:7001/coupon-template/template/sdk/all
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/sdk/all
     * */
    @GetMapping("/template/sdk/all")
    @IgnorePermission
    public List<CouponTemplateSDK> findAllUsableTemplate() {
        log.info("Find All Usable Template.");
        return templateBaseService.findAllUsableTemplate();
    }

    /**
     * Get the mapping from template ids to CouponTemplateSDK
     * 127.0.0.1:7001/coupon-template/template/sdk/infos?ids=1,2
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/sdk/infos?ids=x,y
     * 127.0.0.1:9000/coupon-microservice/coupon-template/template/sdk/infos?ids=1,2
     * */
    @GetMapping("/template/sdk/infos")
    public Map<Integer, CouponTemplateSDK> findIds2TemplateSDK(
            @RequestParam("ids") Collection<Integer> ids
    ) {
        log.info("FindIds2TemplateSDK: {}", JSON.toJSONString(ids));
        return templateBaseService.findIds2TemplateSDK(ids);
    }
}
