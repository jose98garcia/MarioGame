import java.util.ArrayList;
import java.util.Iterator;

public class Mario
{
	//Variables and Velocity
	int x;
	int y;
	int w = 60;
	int h = 95;
	double vertVel;
	Model model;
	
	//previous positions
	int prevX;
	int prevY;
	
	int frameSinceOnGround;
	
	void rememberPos()
	{
		prevX = x;
		prevY = y;
		
	}
	
	Mario(Model m)
	{
		model = m;
		frameSinceOnGround = 0;
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
		
		//am i colliding? with iterator
		Iterator<Brick> it = model.bricks.iterator();
		while(it.hasNext())
		{
			Brick b = it.next();
			
			if(doesCollide(b.x, b.y, b.w, b.h))
			{
				getOut(b.x, b.y, b.w, b.h);
				//System.out.println("collision");
			}	
		}
				
		frameSinceOnGround++;
		
	}
	
	void jump()
	{
		if(frameSinceOnGround < 3)
		{
			vertVel += -15;
		}		

	}	
	
}
