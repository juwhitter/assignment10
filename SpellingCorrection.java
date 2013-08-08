package assignment10;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;

public class SpellingCorrection
{
	static Hashtable<String, Integer>[] wordDictionary;
	static String highestWord = null;
	static int highestFreq = 0;

	public static void main(String[] args)
	{
		// Check that args is of valid length
		if (args.length != 1 && args.length!= 2)
		{
			System.out.println("Incorrect number of arguments!");
			return;
		}
		
		// Check that the first argument is an existing file
		File dictionary = new File(args[0]);
		if (!dictionary.isFile())
		{
			System.out.println("Invalid word statistics file argument!");
			return;
		}
		
		// Determine if the results should be printed to file
		boolean printToFile = false;
		// If there are two arguments and the second is -fr, then set printToFile to true
		if (args.length == 2 && args[1].equals("-fr"))
			printToFile = true;
		// If the second argument is not -fr, then print message and return
		if (args.length == 2 && !args[1].equals("-fr"))
		{
			System.out.println("Invalid file report option argument!");
			return;
		}
				
		// Create dictionary with frequencies
		populate(dictionary);
		System.out.println("Spelling correction program is active... please enter a word.");
		
		boolean continuous = true;
		// Continue the program until "exit" is typed in
		while (continuous)
		{
			// Ask user for input
			Scanner in = new Scanner(System.in);
			// Lowercase the input
			String word = in.next().toLowerCase();
			if (word.equals("exit"))
			{
				System.out.println("Spelling correction program is exiting... have a nice day.");
				return;
			}
			
			if (wordDictionary[word.length()].containsKey(word))
			{
				System.out.println(word + " is a known term.");
				continue;
			}
			
			// If it is to be printed to file, call the PrintWordsToFile class
			if (printToFile)
			{
				new PrintWordsToFile(word);			
			}		
			else
			{
				// Process the word
				process(word);
			}
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static void populate(File stats)
	{
		// If it is not a file, return
		if (!stats.isFile())
		{
			System.out.println("Invalid word statistics file argument!");
			return;
		}
		
		// Create dictionary with frequencies
		wordDictionary = (Hashtable<String, Integer>[]) new Hashtable[50000];
		for (int i = 0; i < wordDictionary.length; i++)
			wordDictionary[i] = new Hashtable<String, Integer>();
		
		Scanner fileIn = null;
		try 
		{
			fileIn = new Scanner(stats);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		while (fileIn.hasNext())
		{
			String temp = fileIn.next();
			wordDictionary[temp.length()].put(temp.toLowerCase(), fileIn.nextInt());			
		}
	}
	
	
	public static void process(String word)
	{
		word = word.toLowerCase();
		
		highestWord = null;
		highestFreq = 0;
		
		if (wordDictionary[word.length()].containsKey(word))
		{
			System.out.println(word + " is a known term.");
			return;
		}
		
		deletion(word);
		transpositions(word);
		substitution(word);
		insertion(word);
		
		if (highestFreq == 0)
			System.out.println(word + " is an unknown term!");
		else
			System.out.println(word + " is an unknown term... did you mean '" + highestWord +"'?");
	}

	
    private static void deletion(String word)
    {
        int length = word.length();
        // Loop through each letter in the word and delete a letter
        for (int i = 0; i  < length; i++)
        {
            // Delete the letter at spot i
        	StringBuilder wordBuild = new StringBuilder(word);
            wordBuild.deleteCharAt(i);
            
        	if(wordDictionary[wordBuild.length()].containsKey(wordBuild.toString()))
        		if (wordDictionary[wordBuild.length()].get(wordBuild.toString()) > highestFreq)
        		{
        			highestWord = wordBuild.toString();
        			highestFreq = wordDictionary[wordBuild.length()].get(wordBuild.toString());			
        		}
        }
    }
   
    
    private static void transpositions(String word)
    {
        int length = word.length();
        // Loop through and swap each letter with the one to the right
        for (int i = 0; i < length - 1; i++)
        {
            StringBuilder wordBuild = new StringBuilder(word);
            // Swap the characters
            char temp = wordBuild.charAt(i);
            wordBuild.setCharAt(i, wordBuild.charAt(i+1));
            wordBuild.setCharAt(i + 1, temp);
            
        	if(wordDictionary[wordBuild.length()].containsKey(wordBuild.toString()))
        		if (wordDictionary[wordBuild.length()].get(wordBuild.toString()) > highestFreq)
        		{
        			highestWord = wordBuild.toString();
        			highestFreq = wordDictionary[wordBuild.length()].get(wordBuild.toString());			
        		}
        }
    }
    
    
    public static void substitution(String word)
    {
    	char letter = 'a';	
    	for (int y = 0; y < word.length(); y++)
    	{
    		StringBuilder temp = new StringBuilder(word);
    		for (int x = 0; x <= 25; x++)
    		{
    			temp.setCharAt(y,letter);
    			letter++;
    			
    	    	if(wordDictionary[temp.length()].containsKey(temp.toString()))
    	    		if (wordDictionary[temp.length()].get(temp.toString()) > highestFreq)
    	    		{
    	    			highestWord = temp.toString();
    	    			highestFreq = wordDictionary[temp.length()].get(temp.toString());			
    	    		}
    		}
   	
    	// Set the letter back to 'a' so we can go through replacing them again
    	letter = 'a';
    	}
    }

    
    public static void insertion(String word)
    {
    	char letter = 'a';	
    	StringBuilder s = new StringBuilder(word);
    	for (int y = 0 ; y <= 25; y++)
    	{
    		/*
    		 * Reverses the word, then appends the letter to be added to the end of the word. Followed by a second reversal to 
    		 * revert the order back to normal. This allows us to add a letter to the beginning of the word without messing with indices.
    		 */
    		s.reverse();
    		s.append(letter);
    		s.reverse();

        	if(wordDictionary[s.length()].containsKey(s.toString()))
        		if (wordDictionary[s.length()].get(s.toString()) > highestFreq)
        		{
        			highestWord = s.toString();
        			highestFreq = wordDictionary[s.length()].get(s.toString());			
        		}
    		/*
    		 * Takes the inserted letter and walks it through each spot, the letterSwap helper method will check each iteration against the wordstats
    		 * as it goes along
    		 */
    		for (int x = 0; x < s.length()-1;x++)
    			letterSwap(x,x+1,s);

    		s = new StringBuilder(word);
    		letter++;

    	}

    }

    //Helper method for insertion, basic swapper with 2 indices and checks the resulting string against the wordstats list.
    public static void letterSwap(int index1, int index2, StringBuilder s)

    {	
    	char letter1 = s.charAt(index1);
    	char letter2 = s.charAt(index2);
    	s.setCharAt(index1,letter2);
    	s.setCharAt(index2,letter1);
    	if(wordDictionary[s.length()].containsKey(s.toString()))
    		if (wordDictionary[s.length()].get(s.toString()) > highestFreq)
    		{
    			highestWord = s.toString();
    			highestFreq = wordDictionary[s.length()].get(s.toString());			
    		}
    }
}
