package Escaner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class escaner {

	public static void main(String[] args) {

		//

		File folder = new File("C:/Users/FBRUSCO/Documents/");

		// Kept all of them in a array because they are many files
		File[] files = folder.listFiles();
		for (File f : files) {
			System.out.println("Files  Available are " + f.getName());
		}
		
		// How many Files in a folder
		int fileCount = folder.list().length;
		System.out.println("Number of files  Present are "+fileCount);
		for (File f: files) {
			try {
				Scanner scan = new Scanner(f);
				while(scan.hasNextLine()) {
					String output = scan.nextLine().toLowerCase();
					System.out.println("Data From File"+f.getName());
					System.out.println(output);
				}
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		}
		
		
	  

	}
}
