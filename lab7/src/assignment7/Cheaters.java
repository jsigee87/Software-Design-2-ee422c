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

public class Cheaters {

	static List<String> file_list;
	static Integer substring_len;
	static Integer threshold;
//	Hashtable<Integer, LinkedList<Integer>> table;
	private static String myPackage = Cheaters.class.getPackage().toString().split(" ")[1];
	
	public static void main(String[] args) {
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
		Model.printDictionary();
		
		
	}
	
	public static List<String> getFileList(String path){
		
		
		List<String> results = new ArrayList<String>();
		
		//System.out.println(path); 
		File[] files = new File(path).listFiles(new FilenameFilter() {
			@Override public boolean accept(File dir, String name) {
				return name.endsWith(".txt"); } });
		//System.out.println(files); 
		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		
		return results;
	}
	
	public static void parseFile(String file_name, String path, int file_code) {
		try {
			File f = new File(path + "/" + file_name);
			
//			System.out.println("\nfile name: " + file_name);
			
			//get all the words in the file and put them in a big String
			FileReader fr = new FileReader(f);
			
			BufferedReader br = new BufferedReader(fr);
			
			String line;		
			ArrayList<String> lines = new ArrayList<String>();
			
			while( (line=br.readLine()) != null ){
			     if(line != null){
			         lines.add(line);
			     }
			 }
			fr.close();
			br.close();
			
			String content = "";
			
			for(String s : lines) {
				content += s + "\n";
			}
			
			String [] words = content.split(" ");
//			System.out.println("words length: " + words.length);
			int end;
			
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
				File f = new File(path + "/" + file_name);
				
				FileReader fr = new FileReader(f);
				
				BufferedReader br = new BufferedReader(fr);
				
				String [] words;
				String verify, putData;		
				ArrayList<String> lines = new ArrayList<String>();
				
				while( (verify=br.readLine()) != null ){
				     if(verify != null){
				         words = verify.replaceAll("[^a-zA-Z ]", "").toUpperCase().split("\\s+");
				         putData = String.join(" ", words) + "\n";
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
