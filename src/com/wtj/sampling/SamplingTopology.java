package com.wtj.sampling;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;


public class SamplingTopology {
	public static StormTopology buildTopology() {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("UserQuerySpout", new UserQuerySpout(Constants.userQueries), 4);
		builder.setBolt("samplingBolt", new SamplingBolt(), 8).shuffleGrouping("UserQuerySpout");

		return builder.createTopology();
	}

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		Config conf = new Config();
		conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("samplingTopology", conf, buildTopology());
    
        Thread.sleep(10000);

        cluster.shutdown();
	}
}
