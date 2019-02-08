import java.awt.Color;
import java.awt.Graphics;

public class Brick extends Sprite
{
	Brick(Model m, int xx, int yy, int ww, int hh)
	{
		super(m);
		
		x = xx;
		y = yy;
		w = ww;
		h = hh;
		type = "Brick";
		
		vertVel = 0;
			
	}
	
	//Brick Copy Constructor
	Brick(Brick that, Model m)
	{
		super(that, m);	
	}
	
	Brick duplicateMe(Model m)
	{
		return new Brick(this, m);
	}
	
	boolean isBrick()
	{
		return true;
	}	

	void draw(Graphics g)
	{
		//draws bricks
		g.setColor(new Color(41, 31, 10)); //sets squares outline to black
		g.drawRect(x - model.scrollPos , y, w, h);
		g.fillRect(x - model.scrollPos, y, w, h);
		
	}
	
	void update()
	{
		
		
	}
	
	
	//Unmarshall
	Brick(Json ob, Model m)
	{
		super(m);
		
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
	}	


}

