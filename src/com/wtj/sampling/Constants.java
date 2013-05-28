package com.wtj.sampling;

import java.util.Random;

public class Constants {
	public static final String [] names = new String[26];
	public static final String [] queries = new String[26];
	public static final UserQuery [] userQueries = new UserQuery[500];
	
	static{
		for(int i = 0;i<26;i++){
			char a = 'a';
			a+=i;
			names[i] = "user-"+a;
		}
		
		for(int i = 0;i<26;i++){
			char a = 'a';
			a+=i;
			queries[i] = "link-"+a+".html";
		}
		
		Random ran = new Random(13);
		for(int i = 0;i<500;i++){
			userQueries[i] = new UserQuery(names[ran.nextInt(26)], queries[ran.nextInt(26)], ran.nextInt());
		}
	}
	
	public static long RSHash(String str) {  
	      int b     = 378551;  
	      int a     = 63689;  
	      long hash = 0;  
	      for(int i = 0; i < str.length(); i++)  
	      {  
	         hash = hash * a + str.charAt(i);  
	         a    = a * b;  
	      }  
	      return hash;  
	} 
	
	public static void main(String [] args){
		for(int i = 0;i<26;i++){
			System.out.println(names[i]+" hash ans:"+Math.abs(RSHash(names[i]))%10);
		}
	}
}
