//Brandon Forster
//CIS 3360 Fall 2012
//1 October 2012
//Assignment 1: Hill Cipher

import java.util.*;
import java.io.*;

public class hillCipher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner stdin= new Scanner(System.in);
		
		//Read in filenames. Handle errors later.
		System.out.println("Please ensure your files are all in this program's directory.\n" +
				"Please enter the filename of your encryption key.");
		
		String keyFilename= stdin.next();
		File keyFile= new File(keyFilename);
		
		System.out.println("Please enter the filename of your plaintext.");
		
		String plaintextFilename= stdin.next();
		File plaintextFile = new File(plaintextFilename);
		
		System.out.println("Please enter the desired filename of your ciphertext.");
		
		String outputFilename= stdin.next();
		File outputFile = new File(outputFilename);
		
		//Begin reading files. Start with the key. Handle errors here.
		try {
			Scanner keyScanner = new Scanner(keyFile);
			
			//Key matrices are always squares, and here we're reading in
			//the number of rows and columns.
			int keyRowsCols= keyScanner.nextInt();
			
			//double loop that inputs the key matrix into a 2D array that should exactly
			//match the input.
			int key[][]= new int[keyRowsCols][keyRowsCols];
			for (int i= 0; i < keyRowsCols; i++)
			{
				for (int j= 0; j < keyRowsCols; j++)
					key[i][j]= keyScanner.nextInt();
			}
		}
		//Handle bad filename. Just exit program if error found, no recovery.
		catch (FileNotFoundException fnf)
		{
			System.out.println("Error: bad filename for key. Ending program...");
			System.exit(1);
		}

	}

}
