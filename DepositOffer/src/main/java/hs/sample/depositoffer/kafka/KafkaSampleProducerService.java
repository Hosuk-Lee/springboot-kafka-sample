package hs.sample.depositoffer.kafka;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSampleProducerService {

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    KafkaTemplate<String, KafkaMessageObject> kafkaTemplateObject;


    public void sendMessage(String message) {
        System.out.println("send message : " +  message);
//        this.kafkaTemplate.send("spring-sample-topic", message);
    }

    public void sendObject(KafkaMessageObject message) {
        System.out.println("send message : " +  message);
        this.kafkaTemplateObject.send("spring-sample-topic", message);
    }

    public void sendObjectKey(KafkaMessageObject message) {
        System.out.println("send message : " +  message);
        this.kafkaTemplateObject.send("spring-sample-topic","topic-key" , message);
    }
}
