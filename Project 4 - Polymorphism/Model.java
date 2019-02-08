import java.util.ArrayList;
import java.util.Iterator;

class Model
{
	//array list of sprites
	ArrayList<Sprite> sprites;
	
	//Scrolling
	int scrollPos = 0;
	
	//Instance of mario
	Mario mario;
	CoinBlock block;
	
	Model()
	{
		mario = new Mario(this);
		block = new CoinBlock(100, 670, this); //creates a coinblock --  for testing
		
		//adding sprites to arraylist
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		sprites.add(block);

	}

	public void update()
	{		
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			s.update();
			
			//Collision Detection
			if(mario.doesCollide(s.x, s.y, s.w, s.h))
			{
				//if mario collides with a brick
				if(s.isBrick())
					mario.getOut(s.x, s.y, s.w, s.h);
				
				//if mario collides with a coin block
				else if(s.isCoinBlock())
				{
					CoinBlock cb = (CoinBlock)s; //casting
					mario.getOut(cb.x, cb.y, cb.w, cb.h);
					
					//if mario hits coin block from bottom
					if((mario.y == cb.y + cb.h ))
					{
						if(s.blockHits < 4)
						{
							cb.popOutCoin(); //pops out one coin
							s.blockHits++; //increments times mario hits brick
						}
						
						else if(s.blockHits == 4)
						{
							//Pops out 5 coins at once
							for(int j = 0; j < 5; j++)
							{
								cb.popOutCoin();
							}
							
							s.blockHits++; // increments mario hitting brick
							
						}
						
						else if (s.blockHits > 4)
						{
							//does nothing... block is now empty
						}

					}				
				}
			}
		}
	}
	
	//method used in controller to draw bricks
	void addBrick(int x, int y, int w, int h)
	{
		Sprite spr = new Brick(this, x, y, w, h);
		sprites.add(spr);
	}
	
	//method used in controller to draw blocks
	void addBlock(int x, int y, int w, int h)
	{
		Sprite spr = new CoinBlock(x, y, this);
		sprites.add(spr);
	}
	
	//Unmarshall
	void unmarshall(Json ob)
	{
		Sprite mario2 = mario; //creates mario
		sprites.clear(); //clears any sprites already in it
		sprites.add(mario2); //adds mario again
		Json json_sprites = ob.get("sprites");
		
		for(int i = 0; i < json_sprites.size(); i++)
		{
			
			Json j = json_sprites.get(i); //gets thing that represents sprites
			String str = j.getString("type");
			
			if(str.equals("Mario"))
			{
				//does nothing		
			}
			
			else if(str.equals("Brick"))
			{
				Brick b = new Brick(j, this);
				sprites.add(b);
			}
			
			else if(str.equals("CoinBlock"))
			{
				CoinBlock cb = new CoinBlock(j, this);
				sprites.add(cb);
			}
		}	
	}	
	
	//Marshall
	Json marshall()
	{
		//JSon object that contains x,y,w,and h
		Json ob = Json.newObject();
		Json json_sprites = Json.newList();
		ob.add("sprites", json_sprites);
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i);
			Json j = s.marshall(); //turns to Json
			json_sprites.add(j); //adds to Json
		}
       
		return ob;	
	}	
	
	
	//save method
	void save(String filename)
	{
		Json ob = marshall(); 
		ob.save(filename); //saves
	
	}	
	
	//load method 
	void load(String filename)
	{
		Json ob = Json.load(filename);
		unmarshall(ob);

	}	
}