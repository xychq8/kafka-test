package producer;

import java.io.IOException;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class KafkaProducer {
    private static Properties prop = new Properties();
    private static ProducerConfig config;
    private static Producer<String, String> producer;

    private KafkaProducer(){
        try {
            prop.load(KafkaProducer.class.getClassLoader().getResourceAsStream("kafka/kafka-producer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        config = new ProducerConfig(prop);
        producer = new Producer<String, String>(config);
    }

    private static class SingletonHolder {
        public static final KafkaProducer instance = new KafkaProducer();
    }

    public static KafkaProducer getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * 实现方法，向队列中放入一条消息
     */
    public boolean send(String topicName, String message) {
        if (topicName == null || message == null) {
            return false;
        }
        KeyedMessage<String, String> km = new KeyedMessage<String, String>(topicName, message);
        producer.send(km);

        System.out.println("向" + topicName + "中发送消息:" + message + "成功!!");
        return true;
    }

    /**
     * 关闭连接
     */
    public void destory(){
        producer.close();
    }

}
