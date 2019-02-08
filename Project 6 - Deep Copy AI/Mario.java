import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Mario extends Sprite
{	
	//previous positions
	int prevX;
	int prevY;
	
	int frameSinceOnGround;
	int jumpNum;
	
	void rememberPos()
	{
		prevX = x;
		prevY = y;
		
	}
	
	boolean isMario()
	{
		return true;
	}
	
	//images
	static BufferedImage[] mario_images = null;
	static BufferedImage[] mario_imagesl = null;	
	
	Mario(Model m)
	{
		super(m);
		frameSinceOnGround = 0;
		
		blockHits = 0;
		
		vertVel = 0;
		w = 60;
		h = 95;
		
		jumpNum = 0;
		
		type = "Mario";
		
		//LAZY Loading
		if (mario_images == null && mario_imagesl == null)
			{
				//assigns mario images into array & calls load method
				mario_images = new BufferedImage[5]; //right scrolling images
				mario_imagesl = new BufferedImage[5]; // left scrolling images

				for (int i = 0; i < 5; i++)
				{
					mario_images[i] = loadImage("mario" + (i+1) + ".png");
					mario_imagesl[i] = loadImage("mariol" + (i+1) + ".png");
				}
			}
		
	}
	
	//Mario Copy Constructor
	Mario(Mario that, Model m)
	{
		super(that, m);
		
		this.jumpNum = that.jumpNum;
		this.frameSinceOnGround = that.frameSinceOnGround;
		
		/*
		//LAZY Loading
		if (mario_images == null && mario_imagesl == null)
		{
			//assigns mario images into array & calls load method
			mario_images = new BufferedImage[5]; //right scrolling images
			mario_imagesl = new BufferedImage[5]; // left scrolling images

			for (int i = 0; i < 5; i++)
			{
				mario_images[i] = loadImage("mario" + (i+1) + ".png");
				mario_imagesl[i] = loadImage("mariol" + (i+1) + ".png");
			}
		}
		*/
		
	}
	
	Mario duplicateMe(Model m)
	{
		return new Mario(this, m);
	}
	
	Mario(Json ob, Model m)
	{
		super(m);
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
		vertVel = (double)ob.getDouble("vertVel");
		
	}

	void draw(Graphics g)
	{
		int marioFrame = (Math.abs(x) / 20) % 5; // 20 slows down how fast he runs //5 number of frames
		
		if(prevX <= x)
			g.drawImage(mario_images[marioFrame], x - model.scrollPos, y, null); //draws right scrolling mario
		else
			g.drawImage(mario_imagesl[marioFrame], x - model.scrollPos, y, null); //draws left scrolling mario
		
	}
	
	boolean doesCollide(int _x, int _y, int _w, int _h)
	{
		//detects for collision
		if(x + w <= _x)
			return false;
		else if(x >= _x + _w)
			return false;
		else if (y + h <= _y)
			return false;
		else if (y >= _y + _h)
			return false;
		
		return true;
	}
	
	//gets mario out of collisions
	void getOut(int _x, int _y, int _w, int _h)
	{
		if(x + w >= _x && prevX + w <= _x) //if coming in from left
		{
			 x = _x - w;
			 //System.out.println("Working -- Left Side");
		}
		
		else if(x < _x + _w && prevX >= _x + _w) //coming in from right
		{
			x = _x + _w;
			//System.out.println ("Working -- Right Side");
		}
		
		
		else if(y + h > _y && prevY + h <= _y) //coming in from above
		{
			y = _y - h;
			vertVel = 0;
			frameSinceOnGround = 0;
			//System.out.println ("Working -- Top Side");
		}
		
		else if(y < _y + _h && prevY >= _y + _h) //coming in from below
		{ 
			y = _y + _h;
			vertVel = 0;
			//System.out.println("Working -- Bottom Side");
		}
		
	}
		
	//Mario Physics
	void update()
	{	
		//scroll position goes with mario
		model.scrollPos = x - 200;
		
		//gravity
		vertVel += 3.14;
		y += vertVel;

		
		//Makes the ground stop mario from falling
		if( y > 760)
		{
			y = 760; //snap back to ground
			vertVel = 0.0;
			frameSinceOnGround = 0;
		}
		
		frameSinceOnGround++;
		
		
	}
	
	void jump()
	{
		if(frameSinceOnGround < 3)
		{
			vertVel += -25;
			jumpNum++;
		}	
		
		//System.out.println(jumpNum);
		

	}	
	
	
	
}
