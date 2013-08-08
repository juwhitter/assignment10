package assignment10;

import java.io.File;

public class TimeSpelling
{

	public static void main(String[] args) 
	{
		int timesToLoop = 5;
		int initialSize = 100;
		int sizeIncrement = 1000;
		int numberOfPoints = 100;
		
		// Create a string that is sizeIncrement long
		String a = "";
		for (int i = 0; i < sizeIncrement; i++)
			a = a + "a";
		
		StringBuilder wordTest = new StringBuilder("a");
		
		File dictionary = new File("WordStats.txt");
		// TEST TIMING FOR ALTERNATIVES
		for (int i = 0; i < numberOfPoints; i++)
		{			
			long start, mid, end;
			
			start = System.nanoTime();
			for (int j = 0; j < timesToLoop; j++)
			{
				SpellingCorrection.populate(dictionary);
				new PrintWordsToFile(wordTest.toString());
			}
			mid = System.nanoTime();
			for (int j = 0; j < timesToLoop; j++); // Time for loop
			end = System.nanoTime();
			
			System.out.println(wordTest.length() + "\t" + (((mid - start) - (end - mid)) / timesToLoop));
			wordTest.append(a);
		}
		
		// TEST TIMING FOR FINDING MOST FREQUENT
		dictionary = new File("WordStats.txt");
		SpellingCorrection.populate(dictionary);
		for (int i = 0; i < numberOfPoints; i++)
		{			
			long start, mid, end;
			
			
			start = System.nanoTime();
			for (int j = 0; j < timesToLoop; j++)
			{
				SpellingCorrection.process(wordTest.toString());
			}
			mid = System.nanoTime();
			for (int j = 0; j < timesToLoop; j++); // Time for loop
			end = System.nanoTime();
			
			System.out.println(wordTest.length() + "\t" + (((mid - start) - (end - mid)) / timesToLoop));
			wordTest.append(a);
		}
	}

}
