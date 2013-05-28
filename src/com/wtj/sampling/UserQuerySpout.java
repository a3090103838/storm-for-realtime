package com.wtj.sampling;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import datasalt.storm.SimpleSpout;

public class UserQuerySpout extends SimpleSpout {

	private static final long serialVersionUID = 1L;
	private UserQuery[] userQueries= new UserQuery[500];
	private Queue<UserQuery> userQueriesList = new LinkedList<UserQuery>();

	public UserQuerySpout(UserQuery[] userQueries) {
		super();
		this.userQueries = userQueries;
	}

	
	@Override
	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		super.open(conf, context, collector);
		
		for(UserQuery userQuery:userQueries){
			userQueriesList.offer(userQuery);
		}
	}

	@Override
	public void nextTuple() {
		UserQuery userQuery = userQueriesList.poll();
		if(userQuery!=null){
			collector.emit(new Values(userQuery.getUsername(),userQuery.getQuery(),userQuery.getTime()),userQuery);
		}
	}

	@Override
	public void ack(Object msgId) {
		userQueriesList.add((UserQuery)msgId);
	}

	@Override
	public void fail(Object msgId) {
		userQueriesList.add((UserQuery)msgId);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("username","query","time"));
	}

}
