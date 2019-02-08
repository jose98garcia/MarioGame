import java.awt.image.BufferedImage;
import java.util.Random;
import java.awt.Graphics;

public class CoinBlock extends Sprite
{	
	BufferedImage[] block_images = null;
	
	//for random numbers
	static Random rand = new Random();
	
	
	CoinBlock(int xx, int yy, Model m)
	{
		super(m);
		
		x = xx;
		y = yy;
		w = 89;
		h = 83;
		
		type = "CoinBlock";
		
		//number times mario hits block from the bottom
		blockHits = 0;
		
		//Lazy Loading
		if (block_images == null)
		{
			block_images = new BufferedImage[2];
			
			block_images[0] = loadImage("block0.png");
			block_images[1] = loadImage("block1.png");
		}	
		
	}
	
	
	boolean isCoinBlock()
	{
		return true;
	}
	
	void draw(Graphics g)
	{
		//draws regular block
		g.drawImage(block_images[0], x - model.scrollPos , y , null);
		
		//draws empty block after 5 hits
		if(blockHits >= 5)
			g.drawImage(block_images[1], x - model.scrollPos, y , null);
		
	}
	
	//pops out coins - used when mario hits block from underneath
	void popOutCoin()
	{
		//generates random numbers
		double hVel = rand.nextDouble() * 16 - 8;
		double vVel = -22;
		
		//adds coins
		Coin c = new Coin(x, y, hVel, vVel, model);
		model.sprites.add(c);
		
	}
	
	void update()
	{
		
	}
	
	//Unmarshall
	CoinBlock(Json ob, Model m)
	{
			super(m);
			
			//type = (String)ob.getString("type");
			x = (int)ob.getLong("x");
			y = (int)ob.getLong("y");
			w = (int)ob.getLong("w");
			h = (int)ob.getLong("h");
			
			//Lazy Loading
			if (block_images == null)
			{
				block_images = new BufferedImage[2];
				
				block_images[0] = loadImage("block0.png");
				block_images[1] = loadImage("block1.png");
			}	
		}	
}
