//Brandon Forster
//CIS 3360 Fall 2012
//1 October 2012
//Assignment 1: Hill Cipher

import java.util.*;
import java.io.*;

public class hillCipher {

	private static final int MAX_INPUT_SIZE= 10000;
	private static final int LINE_LENGTH= 80;
	private static final boolean DEBUG = false;

	public static void main(String[] args) {
		Scanner stdin= new Scanner(System.in);

		//Read in filenames. Handle errors later. The DEBUG code is just for me to quickly test.
		String keyFilename;
		if (!DEBUG){
			System.out.println("Please ensure your files are all in this program's directory.\n" +
					"Please enter the filename of your encryption key.");

			keyFilename= stdin.next();
		}
		else
			keyFilename= "key";

		File keyFile= new File(keyFilename);

		//plaintext
		String plaintextFilename;
		if (!DEBUG)
		{
			System.out.println("Please enter the filename of your plaintext.");

			plaintextFilename= stdin.next();
		}
		else
			plaintextFilename= "in";

		File plaintextFile = new File(plaintextFilename);

		//output
		String outputFilename;
		if(!DEBUG)
		{
			System.out.println("Please enter the desired filename of your ciphertext.");

			outputFilename= stdin.next();
		}
		else
			outputFilename= "out";

		//the "n" value of the key matrix. determines how many rows and columns there are.
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
			//add letters to the final plaintext array
			else
				plaintext[j++]= rawPlaintext[i];
		}

		char[] ciphertext= new char[plaintext.length+1];

		//perform the matrix multiplication. store in the ciphertext array
		//l= letter		r= row		c= column	j= length of actual plaintext
		for (int l= 0; l< j; l+= keyRowsCols)
		{
			for (int r=0; r< keyRowsCols; r++)
			{
				for (int c=0; c< keyRowsCols; c++)
				{
					//this checks for run over in case we run out of plaintext to encode
					if ((l+c) < j)
					{
						ciphertext[l+r]+= (char) (key[r][c]* (plaintext[l+c]-'a'));
					}
					
					//this is the code that is used in case of run out
					else
					{
						//'x'-'a' looks weird, but it's there to be consistent with the way we encode normally
						ciphertext[l+r]+= (char) (key[r][c]* ('x'-'a'));
					}
				}
				//mod by 26 and add 'a' to keep it in ascii alphabet
				ciphertext[(l+r)]= (char) (ciphertext[(l+r)] % 26 + 'a');
				}
		}
		try
		{
			FileWriter fstream = new FileWriter(outputFilename);
			BufferedWriter out = new BufferedWriter(fstream);

			//print out the finished product, 80 chars per line.
			for (int i=0; i<ciphertext.length;)
			{
				for(j=0; j<LINE_LENGTH; j++)
				{
					if (i<ciphertext.length)
						out.write(ciphertext[i++]);
				}
				out.write("\n");
			}

			out.close();
		}
		//in case something happened
		catch (IOException e)
		{
			System.out.println("Error writing output. Ending program...");
			System.exit(1);
		}
	}
}