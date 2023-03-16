package wlh.coupon.executor;

import wlh.coupon.constant.CouponCategory;
import wlh.coupon.constant.RuleFlag;
import wlh.coupon.exception.CouponException;
import wlh.coupon.vo.SettlementInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Coupon Settlement Rule Execution Manager
 * According to the user's request (SettlementInfo),
 * find the corresponding Executor and do the settlement
 * BeanPostProcessor: Bean Post Processor
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class ExecuteManager implements BeanPostProcessor {

    /** Rule Executor Mapping */
    private static Map<RuleFlag, RuleExecutor> executorIndex =
            new HashMap<>(RuleFlag.values().length);

    /**
     * Coupon Settlement Rules Calculation Entrance
     * Note: Be sure number of coupons passed in is >= 1
     * */
    public SettlementInfo computeRule(SettlementInfo settlement)
            throws CouponException {

        SettlementInfo result = null;

        // Single coupons
        if (settlement.getCouponAndTemplateInfos().size() == 1) {

            // Get coupons categories
            CouponCategory category = CouponCategory.of(
                    settlement.getCouponAndTemplateInfos().get(0)
                            .getTemplate().getCategory()
            );

            switch (category) {
                case DTOC:
                    result = executorIndex.get(RuleFlag.DTOC)
                            .computeRule(settlement);
                    break;
                case PTC:
                    result = executorIndex.get(RuleFlag.PTC)
                            .computeRule(settlement);
                    break;
                case DC:
                    result = executorIndex.get(RuleFlag.DC)
                            .computeRule(settlement);
                    break;
            }
        } else {

            // Various Coupons
            List<CouponCategory> categories = new ArrayList<>(
                    settlement.getCouponAndTemplateInfos().size()
            );

            settlement.getCouponAndTemplateInfos().forEach(ct ->
                    categories.add(CouponCategory.of(
                            ct.getTemplate().getCategory()
                    )));
            if (categories.size() != 2) {
                throw new CouponException("Not Support For More " +
                        "Template Category");
            } else {
                if (categories.contains(CouponCategory.DTOC)
                        && categories.contains(CouponCategory.PTC)) {
                    result = executorIndex.get(RuleFlag.DTOC_PTC)
                            .computeRule(settlement);
                } else {
                    throw new CouponException("Not Support For Other " +
                            "Template Category");
                }
            }
        }

        return result;
    }

    /**
     * To execute before the bean is initialized (before)
     * */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {

        if (!(bean instanceof RuleExecutor)) {
            return bean;
        }

        RuleExecutor executor = (RuleExecutor) bean;
        RuleFlag ruleFlag = executor.ruleConfig();

        if (executorIndex.containsKey(ruleFlag)) {
            throw new IllegalStateException("There is already an executor" +
                    "for rule flag: " + ruleFlag);
        }

        log.info("Load executor {} for rule flag {}.",
                executor.getClass(), ruleFlag);
        executorIndex.put(ruleFlag, executor);

        return null;
    }

    /**
     * To execute after bean initialization (after)
     * */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        return bean;
    }
}
