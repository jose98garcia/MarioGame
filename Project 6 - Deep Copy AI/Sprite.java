import java.awt.Graphics;
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
	
	//Constructor
	Sprite(Model m)
	{
		model = m;
	}
	
	//Copy Constructor
	Sprite(Sprite that, Model m)
	{
		this.x = that.x;
		this.y = that.y;
		this.w = that.w;
		this.h = that.h;
		this.vertVel = that.vertVel;
		this.horizVel = that.horizVel;
		this.blockHits = that.blockHits;
		
		this.type = that.type;
		
		model = m;
	}
	
	abstract Sprite duplicateMe(Model m);
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
	
	boolean isMario()
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