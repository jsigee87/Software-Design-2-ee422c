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
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Cheaters {

	static List<String> file_list;
	static Integer len;
//	Hashtable<Integer, LinkedList<Integer>> table;
	private static String myPackage = Cheaters.class.getPackage().toString().split(" ")[1];
	
	public static void main(String[] args) {
				
		//get list of files
		String path = args[2];
		len = null;
		
		try {
			len = Integer.valueOf(args[3]);
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
		}
		
		file_list = getFileList(path);
		
		//preProcess each file
		preProcess(file_list, path);
		
		//create and initialize model
		new Model(file_list.size());
		
		//parse each file and fill up the hashtable
		for(int i = 0; i < file_list.size(); i++) {
			parseFile(file_list.get(i),path,i);			
		}
		
		//build matrix
		Model.buildMatrix();
		Model.buildDictionary();
		Model.printDictionary(len);
		
		
	}
	
	public static List<String> getFileList(String path){
		
		
		List<String> results = new ArrayList<String>();
		
		System.out.println(path); 
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.endsWith(".txt"); } });
		
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		
		return results;
	}
	
	public static void parseFile(String file_name, String path, int file_code) {
		try {
			File f = new File(path + "\\" + file_name);
			
			@SuppressWarnings("resource")
			String content = new Scanner(f).useDelimiter("\\Z").next();
			
			String [] words = content.split(" ");
			
			for(int i = 0; i < words.length; i++) {
				
				String subString = "";
				for(int j = i; j < len; j++) {
					subString += words[j];
				}
				//create hash
				int hash = subString.hashCode();
				
				//check if hash already exists, if so add to end of list
				if(Model.hashtable.containsKey(hash)) {
					Model.hashtable.get(hash).add(file_code);
				}
				else {
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
	
	public static void preProcess(List<String> file_list, String path) {
		
		for(String file_name : file_list) {
			
			try {
				File f = new File(path + "\\" + file_name);
				
				FileReader fr = new FileReader(f);
				
				BufferedReader br = new BufferedReader(fr);
				
				String [] words;
				String verify, putData;		
				ArrayList<String> lines = new ArrayList<String>();
				
				while( (verify=br.readLine()) != null ){
				     if(verify != null){
				         words = verify.replaceAll("[^a-z A-Z]", "").toLowerCase().split("\\s+");
				         putData = String.join(" ", words);
				         lines.add(putData);
				     }
				 }
				fr.close();
				br.close();
				
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
