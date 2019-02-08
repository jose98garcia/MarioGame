import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

abstract class Sprite
{
	Model model;
	
	//Variables
	int x;
	int y;
	int w;
	int h;
	double vertVel;
	double horizVel;
	int blockHits;
	String type;
	
	Sprite(Model m)
	{
		model = m;
	}
	
	abstract void update();
	abstract void draw(Graphics g);
	
	Json marshall()
	{
		Json ob = Json.newObject();
		ob.add("type", type);
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        
        return ob;
	}
	
	boolean isBrick()
	{
		return false;
	}	
	
	boolean isCoinBlock()
	{
		return false;
	}
	
	boolean isCoin()
	{
		return false;
	}
	
	 
	//Load Image Method
	static BufferedImage loadImage(String filename)
	{
		//temporary image holder
		BufferedImage img = null;
			
		try
		{
			img = ImageIO.read(new File(filename));
		} 
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		return img;
	}
		
	/*
	static boolean collide(Sprite a, Sprite b)
	{
		if(a.x + a.w  < b.x) 
		{
			return false;
		}
		
		else if(a.x - a.w > b.w + b.x)
		{
			return false;
		}
		
		else if(a.y + a.h < b.h)
		{
			return false;
		}
		
		else if(a.y > b.y + b.h)
		{
			return false;
		}
		
		return true;	
	}	
	*/
	
	
}	