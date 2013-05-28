package com.wtj.bloomfilter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Constants {
	
	public static final List<String> rubbishEmails = new ArrayList<String>();
	public static final List<String> commonEmails = new ArrayList<String>();

	static{
		try {
			Scanner in = new Scanner(new FileReader("/home/wangtianju/address.txt"));
			int counter = 0;
			while(in.hasNext()){
				counter++;
				String line = in.nextLine();
				line = line.trim();
				if(line.length()>8){
					if(counter%10==1){
						commonEmails.add(line);
					}
					else
						rubbishEmails.add(line);
				}
					
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(commonEmails.size());
		System.out.println(rubbishEmails.size());
	}

}
