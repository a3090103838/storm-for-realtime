package com.wtj.DGIM;

import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class DGIM implements Serializable{
	private static final long serialVersionUID = 1L;
	//包含所有桶的一个queue
	private static Deque<Bucket> queue = new LinkedList<Bucket>();
	private int size = 10*1000;

	public DGIM(int size) {
		this.size = size*1000;
	}

	//查询k时间内的总个数
	public int getCount(){
		int ans = 0;
		int addValue=0;
		Iterator<Bucket> iter = queue.iterator();
		long time = new Date().getTime();
		while(iter.hasNext()){
			Bucket bucket = iter.next();
			if((bucket.getTime()+size)>time){
				addValue = bucket.getSize();
				ans+=addValue;
			}
			else
				break;
		}
		ans-=addValue/2;
		return ans;
	}
	
	//加入一个元素
	public void add(){
		long time = new Date().getTime();
		Bucket bucket = new Bucket(time, 1);
		queue.addFirst(bucket);
		if((time-queue.getLast().getTime())>size){
			queue.removeLast();
		}
		
		Iterator<Bucket> iter = queue.iterator();
		
		if(needMerge(iter)){
			Iterator<Bucket> iter1 = queue.iterator();
			merge(iter1);
		}
	}
	
	//合并size相同的两个桶
	private static void merge(Iterator<Bucket> iter) {
		iter.next();
		Bucket next = iter.next();
		Bucket nextToNext = iter.next();
		int firstValue = next.getSize();
		next.setSize(next.getSize()+nextToNext.getSize());
		iter.remove();
		Iterator<Bucket> iter1 = getIterator(firstValue);
		if(needMerge(iter1)){
			Iterator<Bucket> iter2 = getIterator(firstValue);
			merge(iter2);
		}
	}
	
	private static Iterator<Bucket> getIterator(int firstValue){
		Iterator<Bucket> iter = queue.iterator();
		while(iter.hasNext()){
			Bucket bucket = iter.next();
			if(bucket.getSize()==firstValue)
				return iter;
		}
		return null;
	}

	//检测是否需要桶合并
	private static  boolean needMerge(Iterator<Bucket> iter) {
		Bucket head,next,nextToNext;
		if(iter.hasNext()){
			head = iter.next();
			if(iter.hasNext()){
				next = iter.next();
				if(next.getSize()!=head.getSize())
					return false;
				else{
					if(iter.hasNext()){
						nextToNext = iter.next();
						if(next.getSize()==nextToNext.getSize())
							return true;
						else
							return false;
					}
					else
						return false;
				}
			}
			else
				return false;
		}
		else	
			return false;
	}

	
	
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		DGIM dgim = new DGIM(2);
		for(int i =0;i<30;i++){
			dgim.add();
			Thread.sleep(100);
		}
		
		Iterator<Bucket> iter = queue.iterator();
		while(iter.hasNext())
			System.out.println(iter.next());
		
		System.out.println(dgim.getCount());
	}

}

