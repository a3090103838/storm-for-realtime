package com.wtj.counting;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.generated.StormTopology;
import backtype.storm.topology.InputDeclarer;
import backtype.storm.topology.TopologyBuilder;
 
public class CountingTopology {
	public static boolean isPrime(int num) { 
		boolean isPrime = true;; 
		for(int i=2;i<num/2;i++) { 
			if(num % i == 0) { 
				isPrime = false; 
				break;
			} 
		} 
		return isPrime; 
	}

	public static StormTopology buildTopology() {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("numberSpout", new NumberSpout(), 1);
		InputDeclarer id = builder.setBolt("gatherBolt", new GatherBolt(), 1);
		for(int i = 32;i<100;i++){
			if(isPrime(i))
				builder.setBolt("hashBolt"+i, new HashBolt(i), 1).allGrouping("numberSpout");
				id.shuffleGrouping("hashBolt"+i);
		}

		return builder.createTopology();
	}

	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		Config conf = new Config();
		conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("countingTopology", conf, buildTopology());
    
        Thread.sleep(100000);

        cluster.shutdown();
	}

}
