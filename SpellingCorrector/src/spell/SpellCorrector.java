package spell;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


public class SpellCorrector implements ISpellCorrector {
	
	//Data Members
	private Trie trie;
	private Set<String> set_one;
	private Set<String> set_two;

	//Constructor
	public SpellCorrector()
	{
		trie = new Trie();
		set_one = new TreeSet<String>();
		set_two = new TreeSet<String>();
	}
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException {
		
		Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(dictionaryFileName)));
		String word = "";
		 
	    while (scanner.hasNext())
	    {
	    	word = scanner.next().toLowerCase();
	    	trie.add(word);
	    }
	    scanner.close();
	}

	@Override
	public String suggestSimilarWord(String inputWord)
			throws NoSimilarWordFoundException {
		
		inputWord = inputWord.toLowerCase();
		
		String most_freq_word = "";
		
		if(trie.find(inputWord) != null) // can I do this?
		{
			return inputWord;
		}
		else
		{
			//find closest word possible. 
			//if no closest word is found, call not found exception.
			set_one = modificationCalls(inputWord);
			most_freq_word = mostFrequentWord(set_one);
			if(most_freq_word.equals(""))
			{
				for(String i : set_one)
				{
					set_two.addAll(modificationCalls(i));
				}
				most_freq_word = mostFrequentWord(set_two);
			}
		}
		if(most_freq_word.equals(""))
		{
			throw new NoSimilarWordFoundException();
		}
		else
		{
			return most_freq_word;
		}
	}
	
	public String mostFrequentWord(Set<String> storage_set)
	{
		String most_freq_word = "";
		int most_freq_count = -1;
		
		for(String i : storage_set) //is this how to do a for_each loop?
		{
			if(trie.find(i) == null)
			{
				continue;
			}
			if(trie.find(i).getValue() > most_freq_count)
			{
				most_freq_word = i;
				most_freq_count = trie.find(i).getValue();
			}
			else if (trie.find(i).getValue() == most_freq_count)
			{
				//the one that is alphabetically first wins. 
				int alph_order = i.compareTo(most_freq_word);
				if(alph_order < 1) // if negative, i precedes the argument string
				{
					most_freq_word = i;
				}
				//else the current most_freq_word is alphabetically first so nothing changes
			}
		}	
		return most_freq_word;
	}
	
	public Set<String> modificationCalls(String inputWord)
	{
		Set<String> storage_set = new TreeSet<String>();
		
		storage_set.addAll(deletionEdits(inputWord));
		storage_set.addAll(transpositionEdits(inputWord));
		storage_set.addAll(alterationEdit(inputWord));
		storage_set.addAll(insertionEdit(inputWord));
		
		return storage_set;
	}
	
	
	public Set<String> transpositionEdits(String inputWord) // works correctly
	{
		int word_length = inputWord.length();
		Set<String> storage_set = new TreeSet<String>();
		char storage_char = '*';
		String new_word = "";
				
		for(int i = 0; i < word_length - 1; i++)
		{
			char char_array[] = inputWord.toCharArray();

			storage_char = char_array[i+1];
			char_array[i+1] = char_array[i];
			char_array[i] = storage_char;
			new_word = new String(char_array);
            storage_set.add(new_word);
		}
		return storage_set;
	}
	
	public Set<String> alterationEdit(String inputWord) //works correctly
	{
		int word_length = inputWord.length();
		int alphabet_length = 26;
		
		Set<String> storage_set = new TreeSet<String>();
		
		String new_word = "";
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		
				
		for(int i = 0; i < word_length; i++)
		{
			char char_array[] = inputWord.toCharArray();
			for(int j = 0; j < alphabet_length; j++)
			{
				if(char_array[i] != alphabet.charAt(j))
				{
					char_array[i] = alphabet.charAt(j);
					new_word = new String(char_array);
					storage_set.add(new_word);
				}
			}
		}	
		return storage_set;
	}
	
	public Set<String> insertionEdit(String inputWord) 
	{
		int word_length = inputWord.length();
		int alphabet_length = 26;
		
		Set<String> storage_set = new TreeSet<String>();
		
		String new_word = "";
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		

		for(int i = 0; i < word_length + 1; i++)
		{
			for(int j = 0; j < alphabet_length; j++)
			{
				StringBuilder s_builder = new StringBuilder(inputWord);

				s_builder.insert(i,alphabet.charAt(j));
				new_word = new String(s_builder);
				storage_set.add(new_word);
			}
		}
		return storage_set;
	}
	
	 public Set<String> deletionEdits(String inputWord) //works correctly
	{ 
		int word_length = inputWord.length();
		Set<String> storage_set = new TreeSet<String>();
		String new_word = "";

		for(int i = 0; i < word_length; i++)
		{
			StringBuilder s_builder = new StringBuilder(inputWord);
			new_word = s_builder.deleteCharAt(i).toString(); //is this going to work once I Override the toString()?		
			storage_set.add(new_word);
		}
		return storage_set;
	}
	
}
