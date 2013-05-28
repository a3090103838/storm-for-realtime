package com.wtj.counting;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Constants {
	
	public static final  List<Integer> nums = new ArrayList<Integer>();
	public static final  Set<Integer> numsSet = new HashSet<Integer>();
	
	static{
		Random ran = new Random();
		for(int i = 0;i<30;i++){
			int num = ran.nextInt(100);
			nums.add(num);
			numsSet.add(num);
		}
		for(int num:nums){
				System.out.println(" "+num);
			}
		
	}

	public static void main(String[] args) {

	}

}
