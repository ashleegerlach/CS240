package spell;

import java.io.IOException;

import spell.ISpellCorrector.NoSimilarWordFoundException;

public class Trie implements ITrie {

	//Data Members
	Node root_node;
	int nodecount;
	int wordcount;
	
	//Constructor
	public Trie ()
	{
		this.root_node = new Node();//the root node
		nodecount = 1; //the number of unique nodes
		wordcount = 0; //the number of unique words in the trie
	}
	
	@Override //Override is a security thing, it just helps you keep things clear for inherited function
	/**
	 * Adds the specified word to the trie (if necessary) and increments the word's frequency count
	 * 
	 * @param word The word being added to the trie
	 */
	public void add(String word) {	
		word = word.toLowerCase();
		root_node.recursiveAdd(word, this);
	}	


	@Override
	/**
	 * Searches the trie for the specified word
	 * 
	 * @param word The word being searched for
	 * 
	 * @return A reference to the trie node that represents the word,
	 * 			or null if the word is not in the trie
	 */
	public INode find(String word) {
		try {
			return root_node.recursiveFind(word);
		}
		catch (NullPointerException e)
		{
			return null;
		}
				
	}

	@Override
	/**
	 * Returns the number of unique words in the trie
	 * 
	 * @return The number of unique words in the trie
	 */
	public int getWordCount() {
		return wordcount;
	}

	@Override
	/**
	 * Returns the number of nodes in the trie
	 * 
	 * @return The number of nodes in the trie
	 */
	public int getNodeCount() {
		return nodecount;
	}
	
	@Override
	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	public String toString()
	{
		StringBuilder main_output = new StringBuilder();
		StringBuilder temp_string = new StringBuilder();
		
		
		return recursiveToString(main_output, temp_string, root_node);	
	}
	
	public String recursiveToString(StringBuilder main_output, StringBuilder temp_string, Node cur_node)
	{
		int array_size = 26;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		
		for(int i = 0; i < array_size; i++)
		{
			if(cur_node.childrenNodes[i] != null)
			{
				temp_string.append(alphabet.charAt(i));
				
				if(cur_node.childrenNodes[i].getValue() >= 1)
				{
					main_output.append(temp_string + "\n");
				}
				recursiveToString(main_output, temp_string, cur_node.childrenNodes[i]);
				temp_string.deleteCharAt(temp_string.length() - 1);
			}
		}
		return main_output.toString();
	}
	
	@Override
	public int hashCode() { //all you have to do is change some of the stuff inside of here. 
		final int prime = 84;
		int result = 13;
		result = result + (nodecount * nodecount) + wordcount;
		result = result * prime;

		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
		{
			//System.out.println("this == obj");
			return true;
		}
		if (obj == null)
		{
			//System.out.println("object is null");
			return false;
		}
		if (getClass() != obj.getClass())
		{
			//System.out.println("classes are not equal");
			return false;
		}
		
		Trie other = (Trie) obj;
		if (wordcount != other.wordcount)
		{
			//System.out.println("wordcount not equal to other wordcount");
			return false;
		}
		if (nodecount != other.nodecount)
		{
			//System.out.println("nodecount not equal to other nodecount");
			return false;
		}
		/*
		if (root_node == null) //should I have the opposite?
		{
			if (other.root_node != null)
			{
				System.out.println("one node is null, other node is not null");
				return false;
			}
		} */
		else
		{
			//System.out.println("About to start recursion");
			if (!recursiveEquals(root_node, other.root_node))
			{
				//System.out.println("returning false afte recursion");
				return false;
			}
		}
		//System.out.println("Returning True at end of equals function");
		return true;
	}
	
	public boolean recursiveEquals(Node main_cur_node, Node other_cur_node) 
	{
		int array_size = 26;
		for(int i = 0; i < array_size; i++)
		{
			if(main_cur_node.childrenNodes[i] != null &&
					other_cur_node.childrenNodes[i] != null)
			{
				if(main_cur_node.childrenNodes[i].getValue() != 
						other_cur_node.childrenNodes[i].getValue())
				{
					//compare frequency
					return false;
				}
				if (recursiveEquals(main_cur_node.childrenNodes[i], other_cur_node.childrenNodes[i]) == false)
				{
					return false;
				}
				else
				{
					continue;
				}
			}
			else if (main_cur_node.childrenNodes[i] == null &&
					other_cur_node.childrenNodes[i] == null)
			{
				//if both are null, do nothing
				continue;
			}
			else  //if one is null and the other is not null, return false
			{
				return false;
			}
		}
		return true;
	}
}
