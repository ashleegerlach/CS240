package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Grep extends FileProcessor implements IGrep {

	//Data Members
	String substring_selection_pattern;
	Map<File, List<String>> map;
	
	//Constructors 
	
	@Override
	public Map<File, List<String>> grep(File directory,
			String fileSelectionPattern, String substringSelectionPattern,
			boolean recursive) {

		substring_selection_pattern = substringSelectionPattern;
		this.map = new TreeMap<File, List<String>>();
		processFile(directory, fileSelectionPattern, recursive); //how to pull this from the abstract class?? how to include the abstract class?
		
		return this.map;
	}
	
	public void lineCountOrGrep(File file)
	{
		List<String> line_list = new ArrayList<String>();
		int matches = 0;
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if(line.matches(".*" + substring_selection_pattern + ".*"))
			{
				line_list.add(line);
				matches += 1;
			}
		}
		if(matches > 0)
		{
			this.map.put(file, line_list);
		}
	}
}
