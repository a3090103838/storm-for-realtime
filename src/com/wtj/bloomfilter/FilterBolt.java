package com.wtj.bloomfilter;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class FilterBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	private BloomFilter bf = new BloomFilter();
	private OutputCollector collector;
	private int rightcounter = 0;
	private int wrongcounter = 0;
	
	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		for(String address:Constants.rubbishEmails){
			bf.add(address);
		}
		
	}

	@Override
	public void execute(Tuple input) {
		String commonAddress = input.getStringByField("address");
		if(bf.contains(commonAddress)){
			wrongcounter++;
			collector.emit(new Values(commonAddress,"rubbish"));
		}
		else{
			rightcounter++;
			collector.emit(new Values(commonAddress,"common"));
		}
		collector.ack(input);
	}

	@Override
	public void cleanup() {
		System.out.println("wrongcounter: "+wrongcounter);
		System.out.println("rightcounter: "+rightcounter);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("address","class"));
	}

}
