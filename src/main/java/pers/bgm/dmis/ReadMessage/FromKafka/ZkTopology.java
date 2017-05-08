package pers.bgm.dmis.ReadMessage.FromKafka;

/**
 * Created by Administrator on 2017/4/30.
 */

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.Bolt;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.ArrayList;
import java.util.List;

public class ZkTopology {

    public static void main(String[] args) {
        //        供参考的一个路径格式
        //        [zk: localhost:2181(CONNECTED) 0] ls /kafka08
        //                [bk, admin, consumers, config, controller, brokers, controller_epoch]
        //        [zk: localhost:2181(CONNECTED) 1] ls /kafka08/brokers
        //                [consumers, test0804, topics, ids]

        //这个地方其实就是kafka配置文件里边的zookeeper.connect这个参数，可以去那里拿过来。
        String brokerZkStr = "10.100.90.201:2181/kafka_online_sample";
        String brokerZkPath = "/brokers";
        ZkHosts zkHosts = new ZkHosts(brokerZkStr, brokerZkPath);
        String topic = "mars-wap";
        //以下：将offset汇报到哪个zk集群,相应配置
        String offsetZkServers = "10.199.203.169";
        String offsetZkPort = "2181";
        List<String> zkServersList = new ArrayList<String>();
        zkServersList.add(offsetZkServers);
        //汇报offset信息的root路径
        String offsetZkRoot = "/stormExample";
        //存储该spout id的消费offset信息,譬如以topoName来命名
        String offsetZkId = "storm-example";


        SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, topic, offsetZkRoot, offsetZkId);
        kafkaConfig.zkPort = Integer.parseInt(offsetZkPort);
        kafkaConfig.zkServers = zkServersList;
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());

        KafkaSpout spout = new KafkaSpout(kafkaConfig);

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("spout", spout, 1);
        //builder.setBolt("bolt", new Bolt(), 1).shuffleGrouping("spout");

        Config config = new Config();

        if (args.length > 0) {
            // cluster submit.
            try {
                StormSubmitter.submitTopology("storm-kafka-example", config, builder.createTopology());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("test", config, builder.createTopology());
        }
    }
}
