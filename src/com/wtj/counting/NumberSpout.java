package com.wtj.counting;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import datasalt.storm.SimpleSpout;

public class NumberSpout extends SimpleSpout {

	private static final long serialVersionUID = 1L;
	public List<Integer> nums = new ArrayList<Integer>();
	public Queue<Integer> numsQueue = new LinkedList<Integer>();

	public NumberSpout() {
		this.nums =Constants.nums;
	}

	
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		super.open(conf, context, collector);
		numsQueue.addAll(nums);
	}

	@Override
	public void nextTuple() {
		if(!numsQueue.isEmpty()){
			Integer num = numsQueue.poll();
			collector.emit(new Values(num),num);
		}
	}

	@Override
	public void ack(Object msgId) {
		numsQueue.add((Integer)msgId);
	}

	@Override
	public void fail(Object msgId) {
		numsQueue.add((Integer)msgId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("number"));
	}

}
