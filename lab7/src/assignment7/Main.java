package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

	static List<String> file_list;
	Hashtable<Integer, LinkedList<Integer>> table;
	private static String myPackage = Main.class.getPackage().toString().split(" ")[1];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		//get file list
//		file_list = getFileList();
//		
//		//pre-process each file
//		preProcess(file_list);
//		
//		//parse each file and fill up the hashtable
//		for(String file_name : file_list) {
//			
//		}
		
		List<String> lol = new ArrayList<String>();
		lol.add("text");
		
		preProcess(lol);

	}
	
	public static List<String> getFileList(){
		
		
		List<String> file_list = new ArrayList<String>();

	    // Get a File object for the package
	    File directory = null;
	    String fullPath;
	    String relativePath = myPackage.replace('.', '/');

	    URL resource = ClassLoader.getSystemClassLoader().getResource(relativePath);
	    fullPath = resource.getFile();
	    try {
			directory = new File(resource.toURI());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

	    String[] files = directory.list();
        for (int i = 0; i < files.length; i++) {

            // We are only interested in .class files
            if (files[i].endsWith(".txt")) {

                // Removes the .txt extension
                String name = myPackage + '.' + files[i].substring(0, files[i].length() - 4);
                
                file_list.add(name);
            }
        }
		return file_list;
	}
	
	public static void parseFile(String file_name, int file_code, int offset) {
		FileReader input = null;
		try {
			input = new FileReader(new File(file_name));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;
		StringBuilder sb = new StringBuilder();

		int startIndex = 0;
		try {
			while ( (myLine = bufRead.readLine()) != null)
			{    
				if(startIndex >= offset) {
					sb.append(myLine);
				}
				else {
					//scrap word
					startIndex++;					
				}
				//HASH
				
				//ADD FILE INDEX AS NODE WITH HASH AS THE KEY
				
//			    String[] array1 = myLine.split(":");
//			    // check to make sure you have valid data
//			    String[] array2 = array1[1].split(" ");
			    
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void preProcess(List<String> file_list) {
		
		for(String file_name : file_list) {

			File file = new File(file_name);  // create File object to read from
			Scanner scanner = null;
			PrintWriter writer = null;
			
			try {
				scanner = new Scanner(file);
				writer = new PrintWriter(file_name);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}       // create scanner to read
			
			
			while(scanner.hasNextLine()){  // while there is a next line
			    String line = scanner.nextLine();  // line = that next line
			    
			    String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
			    
			    // do something with that line
			    String[] newLine = words;
			
			    // print to another file.
			    writer.println(newLine);
			}
		}
	}
	

}
