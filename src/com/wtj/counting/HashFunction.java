package com.wtj.counting;

import java.io.Serializable;

public class HashFunction implements Serializable{
	private static final long serialVersionUID = 1L;
	int mod = 0;
	
	public HashFunction(int mod) {
		super();
		this.mod = mod;
	}

	public int hash(String str){
		return RSHash(str)%mod;
	}
	
	public int RSHash(String str){  
	      int b     = 378551;  
	      int a     = 63689;  
	      long hash = 0;  
	      for(int i = 0; i < str.length(); i++)  
	      {  
	         hash = hash * a + str.charAt(i);  
	         a    = a * b;  
	      }  
	      return (int) (Math.abs(hash));   
	} 
}
