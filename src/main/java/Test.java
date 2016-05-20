import consumer.KafkaConsumer;
import producer.KafkaProducer;

/**
 * Created by zhangxu on 16/5/6.
 */
public class Test {

    public static void main(String[] args) throws Exception {
//        KafkaProducer kafkaProducer = KafkaProducer.getInstance();
//        kafkaProducer.send("zxTest", "12345");

        KafkaConsumer kafkaConsumer = new KafkaConsumer();
        kafkaConsumer.start();

//        kafkaProducer.destory();
//        kafkaConsumer.stop();
    }

}
