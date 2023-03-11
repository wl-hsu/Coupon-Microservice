package wlh.coupon.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Kafka related service interface
 */
public interface IKafkaService {

    /**
     * Consumer coupon Kafka messages
     * @param record {@link ConsumerRecord}
     * */
    void consumeCouponKafkaMessage(ConsumerRecord<?, ?> record);
}
