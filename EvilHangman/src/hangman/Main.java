package hangman;

import hangman.IEvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.Scanner;

public class Main {
	
	public static void userPrompt(int guesses, EvilHangmanGame game)
	{
		System.out.println("Used Letters: " + game.getUsedLetters());
		if(guesses == 1)
		{
			System.out.println("You have " + guesses + " guess left");
		}
		else
		{
			System.out.println("You have " + guesses + " guesses left");
		}
		System.out.println("Word: " + game.getCurrentWord());
		System.out.print("Enter Guess: ");
	}
	
	public static void main(String[] args) 
	{
		if (args.length != 3)
		{
			System.out.println("Usage: java [your main class name] dictionary wordLength guesses");
			return;
		}
		String file_name = args[0];
	    int word_length = Integer.parseInt(args[1]);
	    int guesses = Integer.parseInt(args[2]); //guesses is how many guesses left there are.
	    File dictionary_file = new File(file_name);
		if(dictionary_file.exists())
	    {
			if(!dictionary_file.isFile() || !dictionary_file.canRead())
			{
				System.out.println("Usage: java [your main class name] dictionary wordLength guesses");
				return;
			}

		}
		else
		{
			System.out.println("Usage: java [your main class name] dictionary wordLength guesses");
			return;
		}
	    EvilHangmanGame game = new EvilHangmanGame();
			game.startGame(dictionary_file, word_length);
	

		
		//char user_input = 'a'; 
		Scanner scan_in = new Scanner(System.in);

		boolean won_yet = false;
		
		while(guesses > 0 && won_yet == false)
		{
			userPrompt(guesses, game);
			String user_input_string = scan_in.next().toLowerCase();

			if(user_input_string.length() > 1)
			{
				System.out.println("Invalid Input: Please try again");
			}
			else
			{
				char user_input = user_input_string.charAt(0);
				try 
				{
					if(game.errorCheck(user_input))
					{
						game.makeGuess(user_input);
						if (game.getCurrentWord().contains(String.valueOf(user_input)))
						{
							int letter_count = game.getCurrentWord().length() - game.getCurrentWord().replaceAll(String.valueOf(user_input), "").length();
							System.out.println("Yes, there is " + letter_count + " " + user_input);
						}
						else
						{
							guesses -= 1;
							System.out.println("Sorry, there are no " + user_input + "'s");
						}
					}
					else 
					{
						System.out.println("Invalid input");
					}
				} 
				catch (GuessAlreadyMadeException e) 
				{
					System.out.println("You already used that letter");
				}
			}
			
			
			System.out.println();
			//check if won
			if(!game.getCurrentWord().contains("_"))
			{
				System.out.println("You win!");
				System.out.println("The word was " + game.getCurrentWord());
				return;
			}
			else if (guesses == 0)
			{
				System.out.println("You lose!");
				System.out.println("The word was " + game.getFirstWordFromWordSet());
				return;
			}
		}	
		
	}

}
