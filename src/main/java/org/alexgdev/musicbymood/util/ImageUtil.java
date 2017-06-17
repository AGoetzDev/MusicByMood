package org.alexgdev.musicbymood.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtil {
	
	public static BufferedImage makeGray(BufferedImage img)
	{
	    for (int x = 0; x < img.getWidth(); ++x)
	    for (int y = 0; y < img.getHeight(); ++y)
	    {
	        int rgb = img.getRGB(x, y);
	        int r = (rgb >> 16) & 0xFF;
	        int g = (rgb >> 8) & 0xFF;
	        int b = (rgb & 0xFF);

	        int grayLevel = (int)(0.2126 * r + 0.7152 * g + 0.0722 * b) / 3;
	        int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel; 
	        img.setRGB(x, y, gray);
	    }
	    BufferedImage result = new BufferedImage(
	            img.getWidth(),
	            img.getHeight(),
	            BufferedImage.TYPE_BYTE_GRAY);
	    Graphics g = result.getGraphics();
	    g.drawImage(img, 0, 0, null);
	    g.dispose();
	    return result;
	}
	
	public static void equalizeHistogram(BufferedImage bi){
		 int width =bi.getWidth();
        int height =bi.getHeight();
        int anzpixel= width*height;
        int[] histogram = new int[256];
        int[] iarray = new int[1];
        int i =0;

        //read pixel intensities into histogram
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore=bi.getRaster().getPixel(x, y,iarray)[0];
                histogram[valueBefore]++;
            }
        }

        int sum =0;
     // build a Lookup table LUT containing scale factor
        float[] lut = new float[anzpixel];
        for ( i=0; i < 255; ++i )
        {
            sum += histogram[i];
            lut[i] = sum * 255 / anzpixel;
        }

        // transform image using sum histogram as a Lookup table
        for (int x = 1; x < width; x++) {
            for (int y = 1; y < height; y++) {
                int valueBefore=bi.getRaster().getPixel(x, y,iarray)[0];
                int valueAfter= (int) lut[valueBefore];
                iarray[0]=valueAfter;
                 bi.getRaster().setPixel(x, y, iarray); 
            }
        }

	}
	

}
