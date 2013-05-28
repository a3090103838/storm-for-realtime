package com.wtj.DGIM;

public class Bucket {
	
	private long time;
	private int size;
	
	public Bucket(long time2, int size) {
		this.time = time2;
		this.size = size;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Bucket:time:"+this.getTime()+",size:"+this.getSize();
	}

}
