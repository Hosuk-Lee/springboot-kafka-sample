package hs.sample.depositoffer.kafka;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.kafka.common.Uuid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class KafkaMessageObject {

    final String uid = Uuid.randomUuid().toString();
    String id;
    String message;
    String result;

}

