package hangman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class EvilHangmanGame implements IEvilHangmanGame {
//Data Members
	private int guesses_remaining; //needs a getter to access this
	public Set<String> word_set;
	public Set<Character> guess_set;
	public int word_length;
	public String current_word_pattern;
	
	public boolean can_run = true;
	
//Constructors
	public EvilHangmanGame(){
		//fill this in once data members are determined
		guesses_remaining = 0;
		word_set = new TreeSet<String>();
		guess_set = new TreeSet<Character>();
		word_length = 0;
		current_word_pattern = "";
	}
	
//Methods
	public boolean errorCheck(char guess) throws GuessAlreadyMadeException  
	{
		
		if(!Character.isLetter(guess))
		{
			return false;
		}
		if(guess_set.contains(guess))
		{
			throw new GuessAlreadyMadeException();
		}
		return true;
	}
	
	public String getCurrentWord()
	{
		return current_word_pattern;
	}
	
	public String getFirstWordFromWordSet()
	{
		String loser_word = (String) word_set.toArray()[0];
		return loser_word;
	}
	
	public String getUsedLetters()
	{
		String guess_string = "";
		for(char letter : guess_set)
		{
			guess_string += letter + " ";
		}
		return guess_string;
	}
	
	@Override
	public void startGame(File dictionary, int wordLength) {
		word_length = wordLength;
		for(int i = 0; i < word_length; i++)
		{
			current_word_pattern += "_";
		}
		Scanner scanner = null;

		try {
			scanner = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String word = "";
			 
		while (scanner.hasNext())
		{
			word = scanner.next().toLowerCase();
		   	if(word.length() == wordLength)
		   	{
		    	word_set.add(word);
	    	}
	    }
	   scanner.close();
	}
	
	public String createPattern(String word, char guess)
	{
		String new_pattern = "";
		for(int i = 0; i < word.length(); i++)
		{
			if(word.charAt(i) == guess)
			{
				new_pattern += guess;
			}
			else if (Character.isAlphabetic(current_word_pattern.charAt(i)) 
					&& current_word_pattern.charAt(i) == word.charAt(i))
			{
				new_pattern += current_word_pattern.charAt(i);
			}
			else
			{
				new_pattern += "_";
			}
		}
		return new_pattern;
	}
	
	public Map<String, Set<String>> makePartitionMap(char guess)
	{
		Map<String, Set<String>> partition_map = new TreeMap<String, Set<String>>();
		for(String word : word_set)
		{
			String new_pattern = createPattern(word, guess);
			if (!partition_map.containsKey(new_pattern))
			{
				Set<String> map_value_set = new TreeSet<String>();
				partition_map.put(new_pattern, map_value_set);	
			}
			partition_map.get(new_pattern).add(word);
		}		
		return partition_map;
	}
	
	public String findLargestSet(Map<String, Set<String>> partition_map, char guess)
	{		
		String largest_set_key = "";
		int largest_size = 0;
		
		for(Entry<String, Set<String>> key_set : partition_map.entrySet())
		{
//			System.out.print(key_set.getKey() + " : ");
//			for (String l : key_set.getValue())
//				System.out.print(l + " ");
//			System.out.println();
			if(key_set.getValue().size() > largest_size)
			{
				largest_size = key_set.getValue().size();
				largest_set_key = key_set.getKey();
			}
			else if (key_set.getValue().size() == largest_size)
			{
				//choose the word that has the letter farthest to the right.
			
				int key_letter_count = key_set.getKey().length() - key_set.getKey().replaceAll(String.valueOf(guess), "").length();
				
				int largest_set_letter_count = largest_set_key.length() - largest_set_key.replaceAll(String.valueOf(guess), "").length();
				
				if(key_letter_count == 0) //choose the pattern that has zero of the letter
				{
					largest_size = key_set.getValue().size();
					largest_set_key = key_set.getKey();
				}
				else if(key_letter_count != 0 && key_letter_count < largest_set_letter_count) //choose the pattern that has the least number of the letter
				{
					largest_size = key_set.getValue().size();
					largest_set_key = key_set.getKey();
				}
				else if(key_letter_count == largest_set_letter_count) //if the letter counts are equal, choose the one that has the letter farthest to the right
				{
					String largest_key_string = largest_set_key;
					String key_string = key_set.getKey();
					
					int largest_letter_position = 0; 
					int key_letter_position = 0; 

					while(largest_key_string.length() == key_string.length())
					{
						while(largest_key_string.charAt(0) != guess)
						{
							largest_letter_position += 1;
							largest_key_string = largest_key_string.substring(1, largest_key_string.length());
						}
						while(key_string.charAt(0) != guess)
						{
							key_letter_position += 1;
							key_string = key_string.substring(1, key_string.length());
						}
						largest_key_string = largest_key_string.substring(1, largest_key_string.length());
						key_string = key_string.substring(1, key_string.length());
					}
					if(key_letter_position > largest_letter_position)
					{
						largest_size = key_set.getValue().size();
						largest_set_key = key_set.getKey();
					}
				}				
			}
		}
		return largest_set_key;
	}
	
	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException 
	{
		guess = Character.toLowerCase(guess);
		guess_set.add(guess);
		
		Map<String, Set<String>> partition_map = makePartitionMap(guess);
		String max_pattern = findLargestSet(partition_map, guess);
		
		current_word_pattern = max_pattern;
		word_set = partition_map.get(max_pattern);
		
		return word_set;
	}
	
	

	
}
