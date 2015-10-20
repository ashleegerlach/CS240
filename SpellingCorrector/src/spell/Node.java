package spell;

import spell.ITrie.INode;

public class Node implements INode {

	//Data Members
	public Node[] childrenNodes; //for array of 26 nodes
	private int frequency;
	
	//Constructors
	public Node()
	{
		childrenNodes = new Node[26]; //is this the correct way to initialize?
		frequency = 0;
	}
	
	@Override
	/**
	 * Returns the frequency count for the word represented by the node
	 * 
	 * @return The frequency count for the word represented by the node
	 */
	public int getValue() {
		return this.frequency;
	}
	
	public void recursiveAdd(String word, Trie curr_trie)
	{
		int word_length = word.length();
		char a = 'a';
		char current_char = word.charAt(0);	
		int array_pos = current_char - a;
		
		if(childrenNodes[array_pos] == null)
		{
			curr_trie.nodecount += 1;
			Node new_node = new Node();
			childrenNodes[array_pos] = new_node;
		}
		if(word_length > 1)
		{
			word = word.substring(1, word_length); //deletes the first letter of the word
			childrenNodes[array_pos].recursiveAdd(word, curr_trie); //delete a letter from word first
		}
		if (word_length == 1)
		{
			childrenNodes[array_pos].incrementFrequency();
			if(childrenNodes[array_pos].getValue() == 1)
			{
				curr_trie.wordcount += 1;
			}
		}
	}
	
	public Node recursiveFind(String word) throws NullPointerException
	{
		int word_length = word.length();
		if(word_length > 0)
		{
			char a = 'a';
			char current_char = word.charAt(0);	
			int array_pos = current_char - a;

			if(word_length > 1)
			{
				word = word.substring(1, word_length); //deletes the first letter of the word
				return childrenNodes[array_pos].recursiveFind(word); //delete a letter from word first
			}
			if (word_length == 1)
			{
				if(childrenNodes[array_pos].getValue() >= 1)
				{
					return childrenNodes[array_pos]; //returns a node
				}
				else
				{
					return null;
				}
			}
		}
		return null;
	}

	private void incrementFrequency() {
		this.frequency += 1;
	}
	
}
