package com.wtj.bloomfilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;


import datasalt.storm.SimpleSpout;

public class EmailSpout extends SimpleSpout implements IRichSpout {

	private static final long serialVersionUID = 1L;
	//private  List<String> rubbishEmails = new ArrayList<String>();
	private  List<String> commonEmails = new ArrayList<String>(); 
	private Queue<String> addressList = new LinkedList<String>(); 

	public EmailSpout() {
		//rubbishEmails = Constants.rubbishEmails;
		commonEmails = Constants.commonEmails;
	}
	
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		super.open(conf, context, collector);
		
		for(String address:commonEmails){
			addressList.offer(address);
		}
	}

	@Override
	public void nextTuple() {
		String address = addressList.poll();
		if(address!=null){
			collector.emit(new Values(address),address);
		}
	}

	@Override
	public void ack(Object msgId) {
		addressList.add((String)msgId);
	}

	@Override
	public void fail(Object msgId) {
		addressList.add((String)msgId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("address"));
	}

}
