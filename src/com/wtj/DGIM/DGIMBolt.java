package com.wtj.DGIM;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class DGIMBolt implements IRichBolt{

	private static final long serialVersionUID = 1L;
	private OutputCollector collector;
	private DGIM dgim;

	@Override
	public void prepare(@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		dgim = new DGIM(10);
	}

	@Override
	public void execute(Tuple input) {
		dgim.add();
		collector.emit(new Values(dgim.getCount()));
	}

	@Override
	public void cleanup() {}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ans"));
	}

}
