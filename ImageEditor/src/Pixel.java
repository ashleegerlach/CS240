
public class Pixel {
	
	private int red;
	private int green;
	private int blue;
	
	//Constructor
	public Pixel()
	{
		red = 0;
		green = 0;
		blue = 0;
	}
	
	public Pixel(int r_value, int g_value, int b_value)
	{
		red = r_value;
		green = g_value;
		blue = b_value;
	}

	//ToString Function
	@Override
	public String toString() {
		return red + "\n" + green + "\n" + blue;
	}

	//Getters
	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}
	
	//Setters
	public void setRed(int red) {
		this.red = red;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	//Other Functions
	public void invertPixel()
	{
		int max_value = 255;
		red = max_value - red;
		green = max_value - green;
		blue = max_value - blue;
	}

	public void grayscalePixel()
	{
		int color_average = (red + green + blue) / 3;
		red = color_average;
		green = color_average;
		blue = color_average;
	}
	
}
