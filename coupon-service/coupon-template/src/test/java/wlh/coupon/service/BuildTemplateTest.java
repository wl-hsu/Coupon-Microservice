package wlh.coupon.service;

import com.alibaba.fastjson.JSON;
import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.DistributeTarget;
import wlh.coupon.constant.PeriodType;
import wlh.coupon.constant.ProductLine;
import wlh.coupon.vo.TemplateRequest;
import wlh.coupon.vo.TemplateRule;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * Construct coupon template service test
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BuildTemplateTest {

    @Autowired
    private IBuildTemplateService buildTemplateService;

    @Test
    public void testBuildTemplate() throws Exception {

        System.out.println(JSON.toJSONString(
                buildTemplateService.buildTemplate(fakeTemplateRequest())
        ));
        /**
         * Since BuildTemplate contains asynchronous services, a
         * synchronous services will generate coupon codes and put them in redis.
         * If the main thread ends too early, it will cause errors,
         * so let the main thread sleep for five seconds.
         * This is only written for test cases here,
         * and there is no need to do this in web services.
         */
        Thread.sleep(5000);
    }

    /**
     * <h2>fake TemplateRequest</h2>
     * */
    private TemplateRequest fakeTemplateRequest() {

        TemplateRequest request = new TemplateRequest();
        request.setName("coupon template" + new Date().getTime());
        request.setLogo("https://upload.wikimedia.org/wikipedia/en/4/4b/Pok%C3%A9mon_Mew_art.png");
        request.setDesc("This is a coupon template");
        request.setCategory(CouponCategory.DTOC.getCode());
        request.setProductLine(ProductLine.WEFD.getCode());
        request.setCount(10000);
        request.setUserId(10001L);  // fake user id
        request.setTarget(DistributeTarget.SINGLE.getCode());

        TemplateRule rule = new TemplateRule();
        rule.setExpiration(new TemplateRule.Expiration(
                PeriodType.SHIFT.getCode(),
                1, DateUtils.addDays(new Date(), 60).getTime()
        ));
        rule.setDiscount(new TemplateRule.Discount(5, 1));
        rule.setLimitation(1);
        rule.setUsage(new TemplateRule.Usage(
                "MD", "College Park",
                JSON.toJSONString(Arrays.asList("Beef", "Sea food"))
        ));
        rule.setWeight(JSON.toJSONString(Collections.EMPTY_LIST));

        request.setRule(rule);

        return request;
    }
}
