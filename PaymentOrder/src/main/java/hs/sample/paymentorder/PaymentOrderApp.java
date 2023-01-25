package hs.sample.paymentorder;

import hs.sample.paymentorder.producer.ClipProducer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class PaymentOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(PaymentOrderApp.class, args);
    }

    @Bean
    public ApplicationRunner runner(
            KafkaTemplate<String,String> kafkaTemplate,
            ClipProducer clipProducer){
        return args -> {
            // kafkaTemplate.send("clip3", "Hello, Clip3");
            clipProducer.async("clip3", "Hello, Clip3-async");
            Thread.sleep(1000L);
        };
    }

}
