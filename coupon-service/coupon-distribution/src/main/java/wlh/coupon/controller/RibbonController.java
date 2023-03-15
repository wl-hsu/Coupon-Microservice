package wlh.coupon.controller;

import wlh.coupon.annotation.IgnoreResponseAdvice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Ribbon application controller
 */
@Slf4j
@RestController
public class RibbonController {

    /** rest client */
    private final RestTemplate restTemplate;

    @Autowired
    public RibbonController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Call template microservices through Ribbon components
     * /coupon-distribution/info
     * */
    @GetMapping("/info")
    @IgnoreResponseAdvice
    public TemplateInfo getTemplateInfo() {

        String infoUrl = "http://eureka-client-coupon-template" +
                "/coupon-template/info";
        return restTemplate.getForEntity(infoUrl, TemplateInfo.class).getBody();
    }

    /**
     * Meta information for template microservices
     * */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class TemplateInfo {

        private Integer code;
        private String message;
        private List<Map<String, Object>> data;
    }
}
