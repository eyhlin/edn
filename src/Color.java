

import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Color
{
  public static void main(String args[]) throws IOException{
  File file= new File("color.png");
  BufferedImage image = ImageIO.read(file);
  // Getting pixel color by position x and y 
  
  
  for(int i=0;i<image.getWidth();i++){
	  for(int j=0;j<image.getHeight();j++){
		  
		  int clr=  image.getRGB(i,j); 
		  int  red   = (clr & 0x00ff0000) >> 16;
		  int  green = (clr & 0x0000ff00) >> 8;
		  int  blue  =  clr & 0x000000ff;
		  if(red>=60&&red<=68&&green>=60&&green<=68&&blue>=105&&blue<=115) System.out.println(i+" "+j); 
	  }
  }
  
  
  }
}