import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Coin extends Sprite
{
	BufferedImage coin_image = null;
	
	Coin(int xx, int yy, double horiz, double vert, Model m)
	{
		super(m);
		
		x = xx;
		y = yy;
		w = 75;
		h = 75;
		
		horizVel = horiz;
		vertVel = vert;
		
		
		
		//Lazy Loading
		if (coin_image == null)
		{
			//coin_image = new BuffferedImage;
			coin_image = loadImage("bitcoin.png");
		}
							
	}
	
	//Coin Copy Constructor
	Coin(Coin that, Model m)
	{
		super(that, m);
		
		/*
		//Lazy Loading
		if (coin_image == null)
		{
			//coin_image = new BuffferedImage;
			coin_image = loadImage("bitcoin.png");
		}	
		*/	
		
	}
	
	Coin duplicateMe(Model m)
	{
		return new Coin (this, m);
	}
	
	Coin(Json ob, Model m)
	{
		super(m);
		
		//type = (String)ob.getString("type");
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		//vertVel = (double)ob.getDouble("vertVel");
		//horizVel = (double)ob.getDouble("horizVel");
		
		//Lazy Loading
		if (coin_image == null)
		{
			//coin_image = new BuffferedImage;
			coin_image = loadImage("bitcoin.png");
		}
	}
	
	boolean isCoin()
	{
		return true;
	}
	
	
	void draw(Graphics g)
	{
		g.drawImage(coin_image, x - model.scrollPos , y , null);
	}
	
	void update()
	{
		//Velocity
		
		vertVel += 3.14;
		y += vertVel;
		
		horizVel += 1;
		x += horizVel;
			
		
	}

}
