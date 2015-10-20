import java.io.*;
import java.util.*;


public class Image {

	private Pixel[][] pixel_array;
	private int height;
	private int width;
	
	//Constructor
	public Image(){}
	
	//Getters

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public Pixel[][] getPixel_array() {
		return pixel_array;
	}
	
	//Setters
	public void setPixel_array(Pixel[][] pixel_array) {
		this.pixel_array = pixel_array;
	}

	//Other Functions
	public void readFile(String infile) throws IOException
	{
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(infile)));
        scanner.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s+)");
        
        scanner.next(); //grabs P3 token
        width = Integer.valueOf(scanner.next()); //does width have to be an Integer type or can it be an int?
        height = Integer.valueOf(scanner.next()); //same question as above for this one.
        create2DArray();
        //why doesn't the debugger show the values of width and height?
        scanner.next(); //grabs the 255 value;
        
        int r_value = -1; 
        int g_value = -1;
        int b_value = -1;
        
        for(int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
            	r_value = Integer.valueOf(scanner.next());
            	g_value = Integer.valueOf(scanner.next());
            	b_value = Integer.valueOf(scanner.next());
            	
            	pixel_array[i][j] = new Pixel(r_value, g_value, b_value);	
        	}
        }

        scanner.close(); // is this where this should go?
	}

	public void writeToFile(String outfile) throws IOException
	{
        
        PrintWriter printer = new PrintWriter(new BufferedWriter(new FileWriter(outfile)));
        
        printer.println("P3");
        printer.print(width); //ppm files have the width and then a space and then the height
        printer.print(" ");
        printer.println(height);
        printer.println("255");
        
        //print the pixel values
        for(int i = 0; i < height; i++)
        {
        	for (int j = 0; j < width; j++)
        	{
        		printer.println(pixel_array[i][j].toString());
        	}
        }
         
        printer.close();
	}
	public void create2DArray()
	{
		pixel_array = new Pixel[height][width];
	}
}
