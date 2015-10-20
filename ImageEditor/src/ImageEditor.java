import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageEditor {
	
	
	public static void main(String[] args) throws IOException 
	{
		Image image = new Image();
        String infile = args[0];
        String outfile = args[1];
        String image_command = args[2];
        ImageEditor image_editor = new ImageEditor();
        
        try {
			image.readFile(infile);
			
			switch(image_command) {
			case "invert": 
				image_editor.invertImage(image);
				break;
			case "grayscale":
				image_editor.grayscaleImage(image);
				break;
			case "emboss":
				image_editor.embossImage(image);
				break;
			case "motionblur":	
				int blur_radius = Integer.valueOf(args[3]);
				if(blur_radius > 1)
				{
					image_editor.motionblurImage(image, blur_radius);
				}
				break;
			default:
				throw new Exception();
			}
			
			image.writeToFile(outfile);
			
		} catch (Exception e) {
			System.out.println("USAGE: java ImageEditor  in-file out-file (grayscale|invert|emboss|motionblur motion-blur-length)");
		}
	}
	
	public void invertImage(Image image)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		Pixel[][] pixel_array = image.getPixel_array();
		Pixel temp_pixel = new Pixel();
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				temp_pixel = pixel_array[i][j];
				temp_pixel.invertPixel();
			}
		}
	}
	
	public void grayscaleImage(Image image)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		Pixel[][] pixel_array = image.getPixel_array();
		Pixel temp_pixel = new Pixel();
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				temp_pixel = pixel_array[i][j];
				temp_pixel.grayscalePixel();
			}
		}		
	}
	
	public void embossImage(Image image)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		Pixel[][] pixel_array = image.getPixel_array();
		Pixel temp_pixel = new Pixel();
		Pixel ul_pixel = new Pixel();
		int r_value = 0;
		int g_value = 0;
		int b_value = 0;
		int ul_r_value = 0;
		int ul_g_value = 0;
		int ul_b_value = 0;
		int new_r = 0;
		int new_g = 0;
		int new_b = 0;
		int max_value = 0;

		for(int i = height-1; i >= 0; i--)
		{
			for(int j = width-1; j >= 0; j--)
			{
				if(i == 0 || j == 0)
				{
					pixel_array[i][j].setRed(128);
					pixel_array[i][j].setGreen(128);
					pixel_array[i][j].setBlue(128);
				}
				else
				{
					temp_pixel = pixel_array[i][j];
					ul_pixel = pixel_array[i-1][j-1];
					
					r_value = temp_pixel.getRed();
					ul_r_value = ul_pixel.getRed();
					new_r = r_value - ul_r_value;
					
					
					g_value = temp_pixel.getGreen();
					ul_g_value = ul_pixel.getGreen();
					new_g = g_value - ul_g_value;
					
					ul_b_value = ul_pixel.getBlue();
					b_value = temp_pixel.getBlue();
					new_b = b_value - ul_b_value;
					
					max_value = findMaxDifference(new_r, new_g, new_b) + 128;
					if(max_value > 255)
					{
						max_value = 255;
					}
					if(max_value < 0)
					{
						max_value = 0;
					}

					pixel_array[i][j].setRed(max_value);
					pixel_array[i][j].setGreen(max_value);
					pixel_array[i][j].setBlue(max_value);
				}
			}
		}		
	}
	
	
	public int findMaxDifference(int new_r, int new_g, int new_b)
	{
		int max_rg = (Math.abs(new_r) >= Math.abs(new_g)) ? new_r : new_g;
		int max_value = (Math.abs(max_rg) >= Math.abs(new_b)) ? max_rg : new_b;
		return max_value;
	}
	
	public void motionblurImage(Image image, int blur_radius)
	{
		int height = image.getHeight();
		int width = image.getWidth();
		Pixel[][] pixel_array = image.getPixel_array();
		int remaining_pixels = 0;

		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{

				int new_red = 0;
				int new_green = 0;
				int new_blue = 0;
				remaining_pixels  = width - j;
				if(remaining_pixels >= blur_radius)
				{					
					for(int k = 0; k < blur_radius; k++)
					{
						new_red += pixel_array[i][j+k].getRed();
						new_green += pixel_array[i][j+k].getGreen();
						new_blue += pixel_array[i][j+k].getBlue();
					}
					new_red = new_red / blur_radius;
					new_green = new_green / blur_radius;
					new_blue = new_blue / blur_radius;
				}
				else
				{					
					for(int k = 0; k < remaining_pixels; k++)
					{
						new_red += pixel_array[i][j+k].getRed();
						new_green += pixel_array[i][j+k].getGreen();
						new_blue += pixel_array[i][j+k].getBlue();
					}
					new_red = new_red / remaining_pixels;
					new_green = new_green / remaining_pixels;
					new_blue = new_blue / remaining_pixels;
				}
				pixel_array[i][j].setRed(new_red);
				pixel_array[i][j].setGreen(new_green);
				pixel_array[i][j].setBlue(new_blue);
				
			}
		}
	}
}