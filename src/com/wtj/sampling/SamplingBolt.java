package com.wtj.sampling;

import java.util.Map;
import java.util.TreeSet;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SamplingBolt implements IRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private TreeSet<String> wholeSet = new TreeSet<String>();
	private TreeSet<String> samplingSet = new TreeSet<String>();

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		String username = input.getStringByField("username");
		String query = input.getStringByField("query");
		int time = input.getIntegerByField("time");

		if(RSHash(username)%10==1){
			synchronized (samplingSet) {
				samplingSet.add(username);
			}
			collector.emit(input,new Values(username,query,time));
			collector.ack(input);
		}
		synchronized (query) {
			wholeSet.add(username);
		}
	}

	@Override
	public void cleanup() {
		int samplingcount = 0;
		synchronized (samplingSet) {
			samplingcount = samplingSet.size();
			System.out.println("totally "+samplingcount+" element in samplingset");
			System.out.println("these are usernames in smplingset:");
			for(String username:samplingSet){
				System.out.print(username+" ");
			}
			System.out.println();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		synchronized (wholeSet) {
			System.out.println("totally "+wholeSet.size()+" element in wholeset");
			System.out.println("these are usernames in wholeset:");
			for(String username:wholeSet){
				System.out.print(username+" ");
			}
			System.out.println();
			System.out.println("the sanpling proportion is:"+(double)samplingcount/wholeSet.size());
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("username","query","time"));
	}
	
	public long RSHash(String str) {  
	      int b     = 378551;  
	      int a     = 63689;  
	      long hash = 0;  
	      for(int i = 0; i < str.length(); i++)  
	      {  
	         hash = hash * a + str.charAt(i);  
	         a    = a * b;  
	      }  
	      return Math.abs(hash);  
	} 

}
