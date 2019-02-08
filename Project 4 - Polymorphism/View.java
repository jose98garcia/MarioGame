import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Iterator;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color; // allows us to use color

class View extends JPanel
{
	Model model;
	Controller controller;
	
	//background image
	static BufferedImage background_image = null;
	
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		
		//LAZY Loading for background image
		if (background_image == null )
			background_image = Sprite.loadImage("wallpaper2.jpg");

	}
	
	//Paints
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		g.drawImage(background_image, -200 - model.scrollPos/5, 0, null); //draws background
		
		//uses iterator to draw sprites
		Iterator<Sprite> it = model.sprites.iterator();
		while(it.hasNext())
		{
			Sprite s = it.next();
			s.draw(g);
		}
		
		g.setColor(Color.BLACK); //draws black outline to separate ground
		g.drawLine(0, 855, 2000, 855);	

	}
}
