package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		
	
		//Trie trie_one = new Trie();
		//Trie trie_two = new Trie();
		
		//trie_one.add("yea");
		/*
		trie_one.add("dog");
		trie_one.add("rat");
		trie_one.add("rat");
		
		trie_two.add("cab");
		trie_two.add("dog");
		trie_two.add("rat");
		if(trie_one.equals(trie_two))
		{
			System.out.println("return true");
		} */

		/**
		 * Create an instance of your corrector here
		 */
		ISpellCorrector corrector = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
