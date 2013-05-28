package com.wtj.bloomfilter;

import java.io.Serializable;
import java.util.BitSet;

public class BloomFilter implements Serializable{
	private static final long serialVersionUID = 1L;
	private int defaultSize=2<<24;
	 private int basic = defaultSize -1;
	 private BitSet bits;
	 
	 public BloomFilter(){
		 bits = new BitSet(defaultSize);
	 }
	 
	 public boolean contains(String url){
		  if(url==null){
		   return true;
		  }
		  int pos1 = hash1(url); 
		  int pos2 = hash2(url); 
		  int pos3 = hash3(url);
		  if(bits.get(pos1)&&bits.get(pos2) &&bits.get(pos3)){
			  return true;
		  }
		  return false; 
	 }


	 public void add(String url){
		  if(url==null){
		   return;
		  }
		  int pos1 = hash1(url); 
		  int pos2 = hash2(url); 
		  int pos3 = hash3(url); 
		  bits.set(pos1);
		  bits.set(pos2);
		  bits.set(pos3); 
	 }
	 
	 private int hash3(String line) { 
		  int h = 0;
		  int len = line.length();
		  for (int i = 0; i < len; i++) {
		   h = 37 * h + line.charAt(i);
		  }
		  return check(h);
	 }
	 private int hash2(String line) {
		  int h = 0;
		  int len = line.length();
		  for (int i = 0;i<len; i++) {
		   h = 33 * h + line.charAt(i);
		  }
		  return check(h);
	 }
	 
	 private int hash1(String line) {
		  int h = 0;
		  int len = line.length();
		  for (int i = 0; i <len; i++) {
		   h = 31 * h + line.charAt(i);
		  }
		  return check(h);
	 }
	 private int check(int h) {
		 return basic & h;
	 }
	 public void test(){
		  String url="http://www.pp.tv";
		  System.out.println(contains(url));
		  add(url);
		  System.out.println(contains(url));
	 }
	 
	 public static void main(String[] args){
		 BloomFilter bf = new BloomFilter();
		 bf.test();
	 }
}
