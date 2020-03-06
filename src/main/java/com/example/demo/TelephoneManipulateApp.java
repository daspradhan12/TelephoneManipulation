package com.example.demo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;



@SpringBootApplication
@RestController
public class TelephoneManipulateApp {
	public static Map<String, Object> responseMap = new HashMap<>();
	public int numOfLetters;
	private int totalManipulations;
	private int pageSize = 100;
	private int pageNumber;
	public static void main(String[] args) {
		SpringApplication.run(TelephoneManipulateApp.class, args);
	}

	@RequestMapping("/manipulate")
	public Map<String, Object> manipulateNumber(@RequestParam(value="telephone") String telephone, @RequestParam(value="page") int page) {
		if(!telephone.isEmpty()){
			clearList();
			this.pageNumber = page;
			numOfLetters = telephone.length();
			scrambleTelephone(telephone, "");
			responseMap.put("page", getPageSet());
			responseMap.put("total", totalManipulations);
		}
		return responseMap;
	}

	private void clearList(){
		totalManipulations = 0;
		listOfTelephones = new HashSet<>();
	}

	private Set<String> listOfTelephones = new HashSet<>();

	private void scrambleTelephone(String telephone, String prefix){
		int length = telephone.length();  //Stores the length of the String telephone inside length variable of integer data-type
		if(prefix.length() == numOfLetters){        //We check if length of String prefix is equal to numOfLetters
			if(!listOfTelephones.contains(prefix))  //if listOfTelephones does not contains the prefix, it is added to the Set listOfTelephones and totalManipulations is incremented by 1.
			{ 
				listOfTelephones.add(prefix);
				totalManipulations++;
			}
		}
		
		
		for(int i=0; i < length;i++){                //We are using recursion algorithm where the parameter telephone has all characters except i-th character and the prefix string is being concatenated with character at position i of telephone
			scrambleTelephone(telephone.substring(0,i) + telephone.substring(i+1,length), prefix + telephone.charAt(i));
		}
	}

			

	private ArrayList<String> getPageSet(){
		int start = (this.pageNumber-1) * 100 ;
		int end = this.pageNumber * 100 > listOfTelephones.size() ? listOfTelephones.size() : this.pageNumber * 100;
		ArrayList<String> list = new ArrayList<>(listOfTelephones);
		list.addAll(listOfTelephones);
		ArrayList<String> set = new ArrayList<>(list.subList(start, end));
		return set;
	}

}
