import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color; // allows us to use color

class View extends JPanel
{
	Model model;
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
				
	}
		
	//Paints
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255)); // background color to cyan
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(0, 0, 0)); //sets squares outline to black
		
		for(int i = 0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			g.drawRect(b.x - model.scrollPos, b.y, b.w, b.h); //draws squares
										  
		}
		
	}
}
