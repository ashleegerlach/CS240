package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class LineCounter extends FileProcessor implements ILineCounter {

//Data Members
	private Map<File, Integer> map;
	
//Constructors //no constructor needed
	
//Functions
	
	@Override
	public Map<File, Integer> countLines(File directory,
			String fileSelectionPattern, boolean recursive) {
		
		map = new TreeMap<File, Integer>();
		processFile(directory, fileSelectionPattern, recursive); //how to pull this from the abstract class?? how to include the abstract class?
		
		return map;
	}

	@Override
	public void lineCountOrGrep(File file) {

		int number_of_lines = 0;
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNextLine())
		{
			scanner.nextLine();
			number_of_lines += 1;
		}
		this.map.put(file, number_of_lines);
	}
}
