package assignment10;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Hashtable;

public class PrintWordsToFile
{
	//static Hashtable<String, Integer>[] wordDictionary;
	static int wordAlternatives = 0;
	static PrintWriter outputFile;
	
	
	public PrintWordsToFile(String word)
	{
		//is.wordDictionary = wordDictionary;
		try 
		{
			outputFile = new PrintWriter(new FileWriter(word + ".txt"));
			
			outputFile.println("User string: " + word + "\n");
			// Print out deletion possibilities
			deletion(word);
			
			// Print out transposition alternatives
			transpositions(word);
			
			// Print out substitution alternatives
			substitution(word);
			
			// Print out the insertion alternatives
			insertion(word);
			
			outputFile.print("TOTAL: generated " + wordAlternatives + " alternative spellings!");
			
			outputFile.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	private static void deletion(String word)
    {
        int length = word.length();
        int counter = 0;
        // Loop through each letter in the word and delete a letter
        for (int i = 0; i  < length; i++)
        {
            // Delete the letter at spot i
        	StringBuilder wordBuild = new StringBuilder(word);
            wordBuild.deleteCharAt(i);
            outputFile.println("Deletion string: " + wordBuild.toString());
            counter++;
            wordAlternatives++;
        }
        
        outputFile.println("Created " + counter + " deletion alternatives \n");
    }
   
    
    private static void transpositions(String word)
    {
        int length = word.length();
        int counter = 0;
        // Loop through and swap each letter with the one to the right
        for (int i = 0; i < length - 1; i++)
        {
            StringBuilder wordBuild = new StringBuilder(word);
            // Swap the characters
            char temp = wordBuild.charAt(i);
            wordBuild.setCharAt(i, wordBuild.charAt(i+1));
            wordBuild.setCharAt(i + 1, temp);
            outputFile.println("Transposition string: " + wordBuild.toString());
            counter++;
            wordAlternatives++;
        }
        
        outputFile.println("Created " + counter + " transposition alternatives \n");
    }
    
    
    public static void substitution(String word)
    {
    	char letter = 'a';	
    	int counter = 0;
    	for (int y = 0; y < word.length(); y++)
    	{
    		StringBuilder temp = new StringBuilder(word);
    		for (int x = 0; x <= 25; x++)
    		{
    			temp.setCharAt(y,letter);
    			letter++;
    			if (temp.toString().equals(word))
    				continue;
    			
    			outputFile.println("Substitution string: " + temp.toString());
                counter++;
                wordAlternatives++;
    		}
   	
    	// Set the letter back to 'a' so we can go through replacing them again
    	letter = 'a';
    	}
    	
    	outputFile.println("Created " + counter + " substitution alternatives \n");
    }

    
    public static void insertion(String word)
    {
    	char letter = 'a';	
    	int counter = 0;
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

    		outputFile.println("Insertion string: " + s.toString());
            counter++;
            wordAlternatives++;
            
    		/*
    		 * Takes the inserted letter and walks it through each spot, the letterSwap helper method will check each iteration against the wordstats
    		 * as it goes along
    		 */
    		for (int x = 0; x < s.length()-1;x++)
    		{
    			letterSwap(x,x+1,s);
    			counter++;
    			wordAlternatives++;
    		}

    		s = new StringBuilder(word);
    		letter++;
    	}
    	
    	outputFile.println("Created " + counter + " insertion alternatives \n");
    }

    //Helper method for insertion, basic swapper with 2 indices and checks the resulting string against the wordstats list.
    public static void letterSwap(int index1, int index2, StringBuilder s)

    {	
    	char letter1 = s.charAt(index1);
    	char letter2 = s.charAt(index2);
    	s.setCharAt(index1,letter2);
    	s.setCharAt(index2,letter1);

		outputFile.println("Insertion string: " + s.toString());
    }
}

