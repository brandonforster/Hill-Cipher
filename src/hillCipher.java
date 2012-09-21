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
		Scanner stdin= new Scanner("System.in");
		
		//Read in filenames. Handle errors later.
		System.out.println("Please ensure your files are all in this program's directory./n" +
				"Please enter the filename of your encryption key.");
		
		File keyFile= new File(stdin.next());
		
		System.out.println("Please enter the filename of your plaintext.");
		
		File plaintextFile = new File(stdin.next());
		
		System.out.println("Please enter the desired filename of your ciphertext.");
		
		File outputFile = new File(stdin.next());
		
		//Begin reading files. Start with the key. Handle errors here.
		

	}

}
