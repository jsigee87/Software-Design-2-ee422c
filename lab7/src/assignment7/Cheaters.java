/* CHEATERS Cheaters.java
 * EE422C Project 7 submission by
 *
 * <John Sigmon>
 * <js85773>
 * <15455>
 * <Daniel Diamont>
 * <dd28977>
 * <15455>
 * Slip days used: <0>
 * Spring 2018
 */

package assignment7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Daniel Diamont and John Sigmon
 *
 */
public class Cheaters {

	static List<String> file_list;
	static Integer substring_len;
	static Integer threshold;
	private static String myPackage = Cheaters.class.getPackage().toString().split(" ")[1];
	
	public static void main(String[] args) {
		long start, end;
		start = System.nanoTime();
		assert args.length == 3;
		
		//get list of files
		String path = args[0];
		substring_len = null;
		
		// Parse arg 2
		try {
			substring_len = Integer.valueOf(args[1]);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		// Parse arg 3
		try {
			threshold = Integer.valueOf(args[2]);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		file_list = getFileList(path);
		
		//preProcess each file
		start = System.nanoTime();
		preProcess(file_list, path);
		System.out.println("pre-process time: " + (System.nanoTime()-start)/1000000000.0);
		
		//create and initialize model
		new Model(file_list.size());
		
		//parse each file and fill up the hashtable
		start = System.nanoTime();
		for(int i = 0; i < file_list.size(); i++) {
			parseFile(file_list.get(i),path,i);			
		}
		System.out.println("parse-file time: " + (System.nanoTime()-start)/1000000000.0);
		
		//build matrix and dictionary
		start = System.nanoTime();
		Model.buildMatrix();
		System.out.println("build matrix time: " + (System.nanoTime()-start)/1000000000.0);
		
		start = System.nanoTime();
		Model.buildDictionary();
		System.out.println("build dict time: " + (System.nanoTime()-start)/1000000000.0);
		
		//print dictionary
		start = System.nanoTime();
		Model.printDictionary();
		System.out.println("pint dict time: " + (System.nanoTime()-start)/1000000000.0);
		
//		long end = System.nanoTime();
//		System.out.println("run time: " + (end-start)/1000000000.0);
		
	}
	
	/**
	 * This function takes an absolute file path and returns a list of
	 * names of the .txt files in the filepath
	 * @param path is the filepath to use for the .txt file look-up
	 * @return list of files
	 */
	public static List<String> getFileList(String path){
		
		
		List<String> results = new ArrayList<String>();
		
		//get all files that end in .txt
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.endsWith(".txt"); } });
 
		//add the file names to the list of files we want to return
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		
		return results;
	}
	
	/**
	 * parses a file to generate hashes for each of the possible subquences of
	 * substring_len words.
	 * 
	 * @param file_name is the name of the particular .txt file to parse
	 * @param path is the absolute filepath to the group of .txt files
	 * @param file_code is the index of the file we will use for the later measure of similarity tests
	 */
	public static void parseFile(String file_name, String path, int file_code) {
		try {
			File f = new File(path + "/" + file_name);
			
			//get all the words in the file and put them in a String
			FileReader fr = new FileReader(f);			
			BufferedReader br = new BufferedReader(fr);
			
			String line;		
			ArrayList<String> lines = new ArrayList<String>();
			
			//read in the words
			while( (line=br.readLine()) != null ){
			     if(line != null){
			         lines.add(line);
			     }
			 }
			fr.close();
			br.close();
			
			//create one long string with all the words in the file 
			String content = "";			
			for(String s : lines) {
				content += s + "\n";
			}			
			
			//split up the words
			String [] words = content.split(" ");
			
			//for all the words, create hashes for all possible combinations of all word chunks
			//of size substring_len
			for(int i = 0; i < words.length-substring_len; i++) {	
				String subString = "";
				for(int j = i; j < substring_len + i; j++) {
					subString += words[j] + " ";
				}
				
				//create hash
				int hash = subString.hashCode();
				
				//check if hash already exists, if so add to end of list
				if(Model.hashtable.containsKey(hash)) {
					Model.hashtable.get(hash).add(file_code);
				}
				else {//create a new list and add the hash
					LinkedList<Integer> list = new LinkedList<Integer>();
					list.add(file_code);		
					Model.hashtable.put(hash, list);					
				}				
			}
		}
		catch(IOException e) {
			e.getStackTrace();
		}
	}
	
	/**
	 * 
	 * @param file_list is the list of .txt files that we want to pre-process
	 * @param path is the the absolute filepath to the group of .txt files
	 */
	public static void preProcess(List<String> file_list, String path) {
		
		//for each file in the file_list, delete all punctuation and turn to uppercase
		for(String file_name : file_list) {
			
			try {
				File f = new File(path + "/" + file_name);				
				FileReader fr = new FileReader(f);				
				BufferedReader br = new BufferedReader(fr);
				
				String [] words;
				String verify, putData;		
				ArrayList<String> lines = new ArrayList<String>();
				
				//use regex for pre-processing
				while( (verify=br.readLine()) != null ){
				     if(verify != null){
				         words = verify.replaceAll("[^a-zA-Z ]", "").toUpperCase().split("\\s+");
				         putData = String.join(" ", words) + "\n";
				         lines.add(putData);
				     }
				 }
				fr.close();
				br.close();
				
				//write result back out to file
				FileWriter fw = new FileWriter(f);
	            BufferedWriter out = new BufferedWriter(fw);
	            for(String s : lines)
	                 out.write(s);
	            out.flush();
	            out.close();
			}
			catch (IOException e) {
				
			}
			
		}
	}
	
	
}
