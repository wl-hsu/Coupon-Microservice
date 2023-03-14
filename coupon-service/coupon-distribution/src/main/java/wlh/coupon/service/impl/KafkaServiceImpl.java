package wlh.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import wlh.coupon.constant.Constant;
import wlh.coupon.constant.CouponStatus;
import wlh.coupon.dao.CouponDao;
import wlh.coupon.entity.Coupon;
import wlh.coupon.service.IKafkaService;
import wlh.coupon.vo.CouponKafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Kafka related function
 * Synchronize the status changes of Coupon in Cache to DB
 */
@Slf4j
@Component
public class KafkaServiceImpl implements IKafkaService {

    /** Coupon Dao */
    private final CouponDao couponDao;

    @Autowired
    public KafkaServiceImpl(CouponDao couponDao) {
        this.couponDao = couponDao;
    }

    /**
     * Consumer Coupon Kafka Messages
     * @param record {@link ConsumerRecord}
     */
    @Override
    @KafkaListener(topics = {Constant.TOPIC}, groupId = "coupon-1")
    public void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            CouponKafkaMessage couponInfo = JSON.parseObject(
                    message.toString(),
                    CouponKafkaMessage.class
            );

            log.info("Receive CouponKafkaMessage: {}", message.toString());

            CouponStatus status = CouponStatus.of(couponInfo.getStatus());

            switch (status) {
                case USABLE:
                    break;
                case USED:
                    processUsedCoupons(couponInfo, status);
                    break;
                case EXPIRED:
                    processExpiredCoupons(couponInfo, status);
                    break;
            }
        }
    }

    /**
     * Handle used coupons
     * */
    private void processUsedCoupons(CouponKafkaMessage kafkaMessage,
                                    CouponStatus status) {
        // TODO Send notification to user
        processCouponsByStatus(kafkaMessage, status);
    }

    /**
     * Handle Expired User Coupons
     * */
    private void processExpiredCoupons(CouponKafkaMessage kafkaMessage,
                                       CouponStatus status) {
        // TODO push notification to user
        processCouponsByStatus(kafkaMessage, status);
    }

    /**
     * Process coupon information based on state
     * */
    private void processCouponsByStatus(CouponKafkaMessage kafkaMessage,
                                        CouponStatus status) {
        List<Coupon> coupons = couponDao.findAllById(
                kafkaMessage.getIds()
        );
        if (CollectionUtils.isEmpty(coupons)
                || coupons.size() != kafkaMessage.getIds().size()) {
            log.error("Can Not Find Right Coupon Info: {}",
                    JSON.toJSONString(kafkaMessage));
            // TODO send notification to user
            return;
        }

        coupons.forEach(c -> c.setStatus(status));
        log.info("CouponKafkaMessage Op Coupon Count: {}",
                couponDao.saveAll(coupons).size());
    }
}
