//Brandon Forster
//CIS 3360 Fall 2012
//1 October 2012
//Assignment 1: Hill Cipher

import java.util.*;
import java.io.*;

public class hillCipher {

	private static final int MAX_INPUT_SIZE= 10000;
	private static final int LINE_LENGTH= 80;
	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		Scanner stdin= new Scanner(System.in);

		//Read in filenames. Handle errors later.
		System.out.println("Please ensure your files are all in this program's directory.\n" +
				"Please enter the filename of your encryption key.");

		String keyFilename= stdin.next();
		
		if (DEBUG) //just to save myself from typing all this over and over
			keyFilename= "key";
		
		File keyFile= new File(keyFilename);

		System.out.println("Please enter the filename of your plaintext.");

		String plaintextFilename= stdin.next();
		
		if (DEBUG)
			plaintextFilename= "in";
		
		File plaintextFile = new File(plaintextFilename);

		System.out.println("Please enter the desired filename of your ciphertext.");

		String outputFilename= stdin.next();
		
		if (DEBUG)
			outputFilename= "out";
		
		File outputFile = new File(outputFilename);
		int keyRowsCols = 0;
		int[][] key= new int[500][500];
		
		//Begin reading files. Start with the key. Handle errors here.
		try {
			Scanner keyScanner = new Scanner(keyFile);

			//Key matrices are always squares, and here we're reading in
			//the number of rows and columns.
			keyRowsCols= keyScanner.nextInt();

			//double loop that inputs the key matrix into a 2D array that should exactly
			//match the input.
			for (int i= 0; i < keyRowsCols; i++)
			{
				for (int j= 0; j < keyRowsCols; j++)
					key[i][j]= keyScanner.nextInt();
			}
		}
		//Handle bad filename. Just exit program if error found, no recovery.
		catch (FileNotFoundException e)
		{
			System.out.println("Error: bad filename for key. Ending program...");
			System.exit(1);
		}

		//read in the plaintext to a String. later, we'll parse it out into chars.
		String plaintextInput= "";
		try
		{
			Scanner plaintextScanner = new Scanner(plaintextFile);
			
			//read in tokens from the plaintext until we've exhausted it. just concat for now
			while (plaintextScanner.hasNext() == true)
				plaintextInput+= plaintextScanner.next();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			
			System.out.println("Error: bad filename for plaintext. Ending program...");
			System.exit(1);
		}
		
		//make sure the input is not too long.
		if (plaintextInput.length() > MAX_INPUT_SIZE)
		{
			System.out.println("Error: input too large. Ending program...");
			System.exit(1);
		}
		
		//turn the trimmed and lowered string into a char array. We will now process
		//this array and put it in a final array.
		char[] rawPlaintext= plaintextInput.trim().toLowerCase().toCharArray();
		char[] plaintext= new char[rawPlaintext.length];
		
		int j= 0;
		for (int i= 0; i< rawPlaintext.length; i++)
		{
			//strip away all non letters
			if (rawPlaintext[i] < 'a' || rawPlaintext[i] > 'z')
				continue;
			else
				plaintext[j++]= rawPlaintext[i];
		}
		
		char[] ciphertext= new char[plaintext.length];
		
		//perform the matrix multiplication. store in the ciphertext array
		//l= letter		r= row		c= column
		for (int l= 0; l< plaintext.length; l+= keyRowsCols)
		{
			for (int r=0; r< keyRowsCols; r++)
			{
				for (int c=0; c< keyRowsCols; c++)
				{
					ciphertext[l+r]+= (char) ((key[r][c]* (int) plaintext[l+c]));
					System.out.println("key: "+key[r][c]+
							"\nr: "+r+"    c: "+c+
							"\nplaintext: " + plaintext[l+c]+
							"\nciphertext@"+(l+r)+"="+(int)ciphertext[l+r]);
				}
			}
			//mod by 26 and add 'a' to keep it in ascii alphabet
			ciphertext[l]= (char) (ciphertext[l] % 26 + 'a');
		}
		
		//print out the finished product, 80 chars per line.
		for (int i=0; i<ciphertext.length; i++)
		{
			for(j=0; j<LINE_LENGTH; j++)
			{
				if (i<ciphertext.length)
					System.out.print(ciphertext[i++]);
			}
			System.out.println();
		}
	}
}