package hs.sample.paymentorder.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.KafkaSendCallback;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class ClipProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public ClipProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void async(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
        future.addCallback(new KafkaSendCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                //
                System.out.println("Success send to message");
            }

            @Override
            public void onFailure(KafkaProducerException e) {
                //
                ProducerRecord<Object, Object> failedProducerRecord = e.getFailedProducerRecord();
                System.out.println("Fail to send message. record " +  failedProducerRecord);
            }
        });
    }

}
