package consumer;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhangxu on 16/5/5.
 */
public class KafkaConsumer{
    private final int threadCount = 1;
    private final String topicName = "zxTest";

    private static Properties prop = new Properties();

    private static ConsumerConfig config;
    private static ConsumerConnector connector;

    static {
        try {
            prop.load(KafkaConsumer.class.getClassLoader().getResourceAsStream("kafka/kafka-consumer.properties"));
            config = new ConsumerConfig(prop);
            connector = Consumer.createJavaConsumerConnector(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从kafka上拉消息
     */
    public void start(){
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        try {
            Map<String, Integer> topicCount = new HashMap<String, Integer>();
            topicCount.put(topicName, threadCount);
            Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = connector.createMessageStreams(topicCount);
            List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topicName);

            for(final KafkaStream<byte[], byte[]> stream : streams){
                ConsumerIterator<byte[], byte[]> it = stream.iterator();
                while(it.hasNext()){
                    try {
                        MessageAndMetadata<byte[], byte[]> item = it.next();
                        String json = new String(item.message(), "UTF-8");
                        System.out.println("从topicName:" + topicName + ",partition:" + item.partition() + "中获取消息：" + json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if(executor != null){
                executor.shutdown();
            }
        }
    }

    /**
     * 关闭连接
     */
    public void stop(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        connector.shutdown();
    }

}
