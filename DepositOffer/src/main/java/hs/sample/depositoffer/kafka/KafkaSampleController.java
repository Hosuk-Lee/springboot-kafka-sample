package hs.sample.depositoffer.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaSampleController {


    @Autowired
    private KafkaSampleProducerService kafkaSampleProducerService;

    @PostMapping(value = "/kafka/sample/{id}")
    public ResponseEntity<String> event(@PathVariable String id,
            String message) {

        System.out.println(id + "/" + message);
        kafkaSampleProducerService.sendMessage(id + "/" + message);

        return ResponseEntity.ok("ok");

    }

    @PostMapping(value = "/kafka/sample/{id}/object")
    public ResponseEntity<String> objectMessage(@PathVariable String id,
            String message) {

        kafkaSampleProducerService.sendObject(KafkaMessageObject.builder()
                .id(id)
                .result("SUCCESS")
                .message(message)
                .build());

        return ResponseEntity.ok("ok");

    }

    @PostMapping(value = "/kafka/sample/{id}/object/key")
    public ResponseEntity<String> objectMessageKey(@PathVariable String id,
            String message) {

        kafkaSampleProducerService.sendObjectKey(KafkaMessageObject.builder()
                .id(id)
                .result("SUCCESS")
                .message(message)
                .build());

        return ResponseEntity.ok("ok");

    }

}
