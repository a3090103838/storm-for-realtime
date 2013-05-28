package com.wtj.counting;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;


public class GatherBolt implements IRichBolt{

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private Map<String,Integer> modToValues = new HashMap<String,Integer>();

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		int mod = input.getIntegerByField("mod");
		int value = input.getIntegerByField("value");
		updateMap(mod, value);
		int ans = getAns();
		collector.emit(new Values(ans));
		//collector.ack(input);
	}
	
	private void updateMap(int mod,int value){
		String key = "mod-"+mod;
		if(modToValues.get(key)==null){
			modToValues.put(key, value);
			return;
		}
		int oldValue = modToValues.get(key);
		if(oldValue<value)
			modToValues.put(key, value);
	}
	
	private int getAns(){
		double ans = 0;
		for(Integer value:modToValues.values()){
			ans+=value;
		}
		return (int) (ans/modToValues.size());
	}

	@Override
	public void cleanup() {}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ans"));
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
