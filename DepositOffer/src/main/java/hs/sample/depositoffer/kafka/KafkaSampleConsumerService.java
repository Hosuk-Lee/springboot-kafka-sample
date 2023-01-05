package hs.sample.depositoffer.kafka;

import java.io.IOException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaSampleConsumerService {

    @KafkaListener(topics = "spring-sample-topic", groupId = "group-id-hosuk")
    public void consume(String message) throws IOException {
        System.out.println("receive message : " + message);
    }
}
