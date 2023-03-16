package wlh.coupon.service;

import com.alibaba.fastjson.JSON;
import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.GoodsType;
import wlh.coupon.exception.CouponException;
import wlh.coupon.executor.ExecuteManager;
import wlh.coupon.vo.CouponTemplateSDK;
import wlh.coupon.vo.GoodsInfo;
import wlh.coupon.vo.SettlementInfo;
import wlh.coupon.vo.TemplateRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

/**
 * Settlement Rules Execution Manager Test Cases
 * Test the distribution and settlement logic of Executor
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ExecuteManagerTest {

    /** fake a UserId */
    private Long fakeUserId = 20005L;

    @Autowired
    private ExecuteManager manager;

    @Test
    public void testDTOCComputeRuleSuccess() throws CouponException {

        // DTOC settlement test
        log.info("DTOC Coupon Executor Test");
        SettlementInfo DTOCInfo = fakeDTOCCouponSettlementSuccess();
        SettlementInfo result = manager.computeRule(DTOCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }

    @Test
    public void testDTOCComputeRuleFailure() throws CouponException {

        // DTOC settlement test
        log.info("DTOC Coupon Executor Test");
        SettlementInfo DTOCInfo = fakeDTOCCouponSettlementFailure();
        SettlementInfo result = manager.computeRule(DTOCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }

    @Test
    public void testPTCComputeRuleSuccess() throws CouponException {

        // PTC settlement test
        log.info("PTC Coupon Executor Test");
        SettlementInfo PTCInfo = fakePTCCouponSettlementSuccess();
        SettlementInfo result = manager.computeRule(PTCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }


    @Test
    public void testPTCComputeRuleFailure() throws CouponException {

        // DTOC settlement test
        log.info("PTC Coupon Executor Test");
        SettlementInfo PTCInfo = fakePTCCouponSettlementFailure();
        SettlementInfo result = manager.computeRule(PTCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }

    @Test
    public void testDCComputeRuleSuccess() throws CouponException {

        // DC settlement test
        log.info("PTC Coupon Executor Test");
        SettlementInfo PTCInfo = fakeDCCouponSettlementSuccess();
        SettlementInfo result = manager.computeRule(PTCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }

    @Test
    public void testDCComputeRuleFailure() throws CouponException {

        // DC settlement test
        log.info("PTC Coupon Executor Test");
        SettlementInfo PTCInfo = fakeDCCouponSettlementFailure();
        SettlementInfo result = manager.computeRule(PTCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }

    @Test
    public void testDTOCPTCComputeRuleSuccess() throws CouponException {

        log.info("DTOC + PTC Coupon Executor Test");
        SettlementInfo DTOCPTCInfo = fakeDTOCAndPTCCouponSettlementSuccess();

        SettlementInfo result = manager.computeRule(DTOCPTCInfo);

        log.info("{}", result.getCost());
        log.info("{}", result.getCouponAndTemplateInfos().size());
        log.info("{}", result.getCouponAndTemplateInfos());
    }


    /**
     * fake(mock) DOTC coupon settlement that base reach the standard
     * */
    private SettlementInfo fakeDTOCCouponSettlementSuccess() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(5);
        goodsInfo01.setPrice(15.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());
        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(5);
        goodsInfo02.setPrice(25.99);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(1);
        templateSDK.setCategory(CouponCategory.DTOC.getCode());
        templateSDK.setKey("100120220831");

        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(10, 199));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                        ))));
        templateSDK.setRule(rule);

        ctInfo.setTemplate(templateSDK);

        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }

    /**
     *  DOTC coupon settlement that base doesn't reach standard
     * */
    private SettlementInfo fakeDTOCCouponSettlementFailure() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(2);
        goodsInfo01.setPrice(9.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(5);
        goodsInfo02.setPrice(15.99);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(1);
        templateSDK.setCategory(CouponCategory.DTOC.getCode());
        templateSDK.setKey("100120220831");

        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(25, 299));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));
        templateSDK.setRule(rule);

        ctInfo.setTemplate(templateSDK);

        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }

    /**
     * fake PTC coupon settlement that can not be used for checkout
     * Due to the goods type limitation
     * */
    private SettlementInfo fakePTCCouponSettlementSuccess() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(2);
        goodsInfo01.setPrice(99.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(5);
        goodsInfo02.setPrice(55.55);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(2);
        templateSDK.setCategory(CouponCategory.PTC.getCode());
        templateSDK.setKey("100220220712");

        // set TemplateRule
        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(50, 1));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));

        templateSDK.setRule(rule);
        ctInfo.setTemplate(templateSDK);
        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }

    /**
     * fake PTC coupon settlement that can be used for checkout
     * */
    private SettlementInfo fakePTCCouponSettlementFailure() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(2);
        goodsInfo01.setPrice(99.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(5);
        goodsInfo02.setPrice(55.55);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(2);
        templateSDK.setCategory(CouponCategory.PTC.getCode());
        templateSDK.setKey("100220220712");

        // set TemplateRule
        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(50, 1));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.MUSIC.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));

        templateSDK.setRule(rule);
        ctInfo.setTemplate(templateSDK);
        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }


    /**
     * fake situation that can use DC
     * */
    private SettlementInfo fakeDCCouponSettlementSuccess() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(5);
        goodsInfo01.setPrice(9.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(10);
        goodsInfo02.setPrice(12.99);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(3);
        templateSDK.setCategory(CouponCategory.DC.getCode());
        templateSDK.setKey("200320220712");

        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(5, 1));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));
        templateSDK.setRule(rule);
        ctInfo.setTemplate(templateSDK);

        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }

    /**
     * fake situation that can't use DC
     * */
    private SettlementInfo fakeDCCouponSettlementFailure() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(2);
        goodsInfo01.setPrice(10.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(10);
        goodsInfo02.setPrice(20.99);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        SettlementInfo.CouponAndTemplateInfo ctInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        ctInfo.setId(1);

        CouponTemplateSDK templateSDK = new CouponTemplateSDK();
        templateSDK.setId(3);
        templateSDK.setCategory(CouponCategory.DC.getCode());
        templateSDK.setKey("200320220712");

        TemplateRule rule = new TemplateRule();
        rule.setDiscount(new TemplateRule.Discount(5, 1));
        rule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.MUSIC.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));
        templateSDK.setRule(rule);
        ctInfo.setTemplate(templateSDK);

        info.setCouponAndTemplateInfos(Collections.singletonList(ctInfo));

        return info;
    }

    /**
     * fake DTOC + PTC
     * */
    private SettlementInfo fakeDTOCAndPTCCouponSettlementSuccess() {

        SettlementInfo info = new SettlementInfo();
        info.setUserId(fakeUserId);
        info.setEmploy(false);
        info.setCost(0.0);

        GoodsInfo goodsInfo01 = new GoodsInfo();
        goodsInfo01.setCount(4);
        goodsInfo01.setPrice(15.99);
        goodsInfo01.setType(GoodsType.VIDEO.getCode());

        GoodsInfo goodsInfo02 = new GoodsInfo();
        goodsInfo02.setCount(10);
        goodsInfo02.setPrice(19.99);
        goodsInfo02.setType(GoodsType.VIDEO.getCode());

        info.setGoodsInfos(Arrays.asList(goodsInfo01, goodsInfo02));

        // DTOC
        SettlementInfo.CouponAndTemplateInfo DTOCInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        DTOCInfo.setId(1);

        CouponTemplateSDK DTOCTemplate = new CouponTemplateSDK();
        DTOCTemplate.setId(1);
        DTOCTemplate.setCategory(CouponCategory.DTOC.getCode());
        DTOCTemplate.setKey("100120220712");

        TemplateRule DTOCRule = new TemplateRule();
        DTOCRule.setDiscount(new TemplateRule.Discount(20, 249));
        DTOCRule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));
        DTOCRule.setWeight(JSON.toJSONString(Collections.emptyList()));
        DTOCTemplate.setRule(DTOCRule);
        DTOCInfo.setTemplate(DTOCTemplate);

        // PTC
        SettlementInfo.CouponAndTemplateInfo PTCInfo =
                new SettlementInfo.CouponAndTemplateInfo();
        PTCInfo.setId(1);

        CouponTemplateSDK PTCTemplate = new CouponTemplateSDK();
        PTCTemplate.setId(2);
        PTCTemplate.setCategory(CouponCategory.PTC.getCode());
        PTCTemplate.setKey("100220220712");

        TemplateRule PTCRule = new TemplateRule();
        PTCRule.setDiscount(new TemplateRule.Discount(85, 1));
        PTCRule.setUsage(new TemplateRule.Usage("MD", "College Park",
                JSON.toJSONString(Arrays.asList(
                        GoodsType.VIDEO.getCode(),
                        GoodsType.EBOOK.getCode()
                ))));
        PTCRule.setWeight(JSON.toJSONString(
                Collections.singletonList("1001202207120001")
        ));
        PTCTemplate.setRule(PTCRule);
        PTCInfo.setTemplate(PTCTemplate);

        info.setCouponAndTemplateInfos(Arrays.asList(
                DTOCInfo, PTCInfo
        ));

        return info;
    }
}


