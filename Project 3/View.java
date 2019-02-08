import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color; // allows us to use color

class View extends JPanel
{
	Model model;
	Controller controller;
	
	//image array for mario images
	static Image[] mario_images = null;
	static Image[] mario_imagesl = null;
	static Image background_image = null;
	
	
	View(Controller c, Model m)
	{
		model = m;
		c.setView(this);
		
		//LAZY Loading
		if (mario_images == null && mario_imagesl == null && background_image == null )
		{
			//assigns mario images into array & calls load method
			mario_images = new Image[5];
			mario_imagesl = new Image[5]; // left scrolling images

			for (int i = 0; i < 5; i++)
			{
				mario_images[i] = loadImage("mario" + (i+1) + ".png");
				mario_imagesl[i] = loadImage("mariol" + (i+1) + ".png");
			}
			
			background_image = loadImage("wallpaper2.jpg");
		}
		
		
	}

	//Load Image Method
	Image loadImage(String filename)
	{
		//temporary image holder
		Image img = null;
		
		try
		{
			img = ImageIO.read(new File(filename));
		} 
		catch(Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		
		//System.out.println("Working");
		
		return img;
	}
	
			
	//Paints
	public void paintComponent(Graphics g)
	{	
		super.paintComponent(g);
		g.drawImage(background_image, -200 - model.scrollPos/5, 0, null);
		
		//g.setColor(new Color(128, 255, 255)); // background color to cyan
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		//draws ground
		//g.setColor(new Color(15, 200, 64));
		//g.fillRect(0, 596, 2000, 596);
		
		g.setColor(Color.BLACK); //draws black outline to separate ground
		g.drawLine(0, 855, 2000, 855);
		
		//draws bricks
		g.setColor(new Color(0, 0, 0)); //sets squares outline to black
		for(int i = 0; i < model.bricks.size(); i++)
		{
			Brick b = model.bricks.get(i);
			g.drawRect(b.x - model.scrollPos, b.y, b.w, b.h); //draws squares
			
		}
		
		//draws mario
		int marioFrame = (Math.abs(model.mario.x) / 20) % 5; // 20 slows down how fast he runs //5 number of frames
		//g.drawImage(mario_images[marioFrame], model.mario.x - model.scrollPos, model.mario.y,null);
		
		if(model.mario.prevX <= model.mario.x)
			g.drawImage(mario_images[marioFrame], model.mario.x - model.scrollPos, model.mario.y,null);
		else
			g.drawImage(mario_imagesl[marioFrame], model.mario.x - model.scrollPos, model.mario.y,null);
			
	}
}
