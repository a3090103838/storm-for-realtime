package com.wtj.bloomfilter;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;


public class FilterTopology {
	public static StormTopology buildTopology() {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("emailSpout", new EmailSpout(), 4);
		builder.setBolt("filterBolt", new FilterBolt(), 8).shuffleGrouping("emailSpout");

		return builder.createTopology();
	}

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		Config conf = new Config();
		conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("filterTopology", conf, buildTopology());
    
        Thread.sleep(20000);

        cluster.shutdown();
	}

}
