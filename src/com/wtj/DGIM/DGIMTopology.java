package com.wtj.DGIM;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.TopologyBuilder;

public class DGIMTopology {

	public static StormTopology buildTopology() {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("tickSpout", new TickSpout(), 1);
		builder.setBolt("DGIMBolt", new DGIMBolt(), 1).allGrouping("tickSpout");

		return builder.createTopology();
	}

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		Config conf = new Config();
		conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("DGIMTopology", conf, buildTopology());
    
        Thread.sleep(100000);

        cluster.shutdown();
	}

}