package listem;

import java.io.File;

public abstract class FileProcessor {
	
	//Data Members	
	//Constructor
	
	//Functions
	
	abstract public void lineCountOrGrep(File file);
	
	public void processFile(File directory, String file_selection_pattern, Boolean recurse_flag)
	{		
		for(File f : directory.listFiles())
		{
			if(f.isDirectory() && recurse_flag)
			{
				processFile(f, file_selection_pattern, recurse_flag);
			}
			else if(f.isFile() && f.getName().matches(file_selection_pattern))
			{
				lineCountOrGrep(f);
			}
		}
	}	
}
