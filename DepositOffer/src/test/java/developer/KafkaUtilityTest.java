package developer;

import java.util.UUID;
import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Test;

public class KafkaUtilityTest {

    @Test
    void KTest(){
        final String uid1 = Uuid.randomUuid().toString();
        final String uid2 = UUID.randomUUID().toString();
        System.out.println(uid1);
        System.out.println(uid2);

    }

}
