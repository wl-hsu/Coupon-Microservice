package wlh.coupon.service;

import com.alibaba.fastjson.JSON;
import wlh.coupon.exception.CouponException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Test of coupon template base service
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TemplateBaseTest {

    @Autowired
    private ITemplateBaseService baseService;

    /**
     * Before running this test case, run BuildTemplateTest to create coupon in DB
     */
    @Test
    public void testBuildTemplateInfo() throws CouponException {

        System.out.println(JSON.toJSONString(
                baseService.buildTemplateInfo( 10)));
        System.out.println(JSON.toJSONString(
                baseService.buildTemplateInfo( 1)));
    }

    @Test
    public void testFindAllUsableTemplate() {

        System.out.println(JSON.toJSONString(
                baseService.findAllUsableTemplate()
        ));
    }

    @Test
    public void testFindId2TemplateSDK() {

        System.out.println(JSON.toJSONString(
                baseService.findIds2TemplateSDK(Arrays.asList(10, 11, 12))
        ));
    }
}
