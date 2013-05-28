package com.wtj.DGIM;

import java.util.Map;
import java.util.Random;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import datasalt.storm.SimpleSpout;

public class TickSpout extends SimpleSpout {
	private static final long serialVersionUID = 1L;
	private Random ran= new Random();
	
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		super.open(conf, context, collector);
	}

	@Override
	public void nextTuple() {
			Utils.sleep(ran.nextInt(10)*100);
			collector.emit(new Values(1));
	}

	@Override
	public void ack(Object msgId) {
	}

	@Override
	public void fail(Object msgId) {
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("tick"));
	}
}
