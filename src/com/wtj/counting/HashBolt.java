package com.wtj.counting;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class HashBolt implements IRichBolt{

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private int mod;
	private HashFunction hashFunction;

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	public HashBolt(int mod) {
		super();
		this.mod = mod;
		hashFunction = new HashFunction(mod);
	}

	@Override
	public void execute(Tuple input) {
		String number = String.valueOf(input.getIntegerByField("number"));
		collector.emit(new Values((Integer)mod,(Integer)(int)Math.pow(2, countRightZero(hashFunction.hash(number)))));
		collector.ack(input);
	}

	@Override
	public void cleanup() {}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("mod","value"));
	}
	
	public int countRightZero(int num){
		int ans = 0;
		if(num==0)
			return 0;
		while((num&1)!=1){
			ans++;
			num=num>>1;
		}
		return ans;
	}
}
