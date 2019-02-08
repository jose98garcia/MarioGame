import java.util.ArrayList;

class Model
{
	//Instance of mario and coinblock
	Mario mario;
	//CoinBlock block;
	
	//array list of sprites
	ArrayList<Sprite> sprites;
	
	//Scrolling
	int scrollPos;
	
	//mario position
	int marioPos;
	
	//mario jump
	int jumpCount;
	
	//coin number
	int coins;
	
	int d = 8;
	int k = 35;
	
	
	//Enum for action
	enum Action
	{
		run,
		jump,
		run_and_jump
	}
	
	Model()
	{
		mario = new Mario(this);
		//block = new CoinBlock(100, 670, this); //creates a coinblock --  for testing
		
		//adding sprites to arraylist
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
		//sprites.add(block);
		
		scrollPos = 0;
		marioPos = mario.x;
		jumpCount = mario.jumpNum;
		coins = 0;
		
		

	}
	
	
	//COPY CONSTRUCTOR
	Model(Model that)
	{
		
		this.scrollPos = that.scrollPos;
		this.marioPos = that.marioPos;
		this.jumpCount = that.jumpCount;
		this.coins = that.coins;
		
		
		
		sprites = new ArrayList<Sprite>();
		for(int i = 0; i < that.sprites.size(); i++)
		{
			Sprite copy = that.sprites.get(i);
			Sprite cloneMe = copy.duplicateMe(this);
			sprites.add(cloneMe);
			
			if(cloneMe.isMario())
			{
				mario = (Mario)cloneMe;
			}
			
		}
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
							coins++; //adds to coins
							s.blockHits++; //increments times mario hits brick
							//System.out.println("Coins: " + coins);
						}
						
						else if(s.blockHits == 4)
						{
							//Last coin
							cb.popOutCoin();
							coins++; //adds to coins
							s.blockHits++; // increments mario hitting brick
							//System.out.println("Coins: " + coins);
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
	

	
	double evaluateAction(Action action, int depth)
	{
		// Evaluate the state
		if(depth >= d)
			return marioPos + 50000 * coins - 2 * jumpCount;

		// Simulate the action
		Model copy = new Model(this); // uses the copy constructor
		copy.do_action(action); // like what Controller.update did before
		copy.update(); // advance simulated time

		// Recurse
		if(depth % k != 0)
		   return copy.evaluateAction(action, depth + 1);
		else
		{
		   double best = copy.evaluateAction(Action.run, depth + 1);
		   best = Math.max(best,
			   copy.evaluateAction(Action.jump, depth + 1));
		   best = Math.max(best,
			   copy.evaluateAction(Action.run_and_jump, depth + 1));
		   return best;
		}
	}
	
	
	//DO Action method
	void do_action(Action a)
	{
		mario.rememberPos();
		
		
		if(a == Action.run)
		{
			mario.x += 10;
			//System.out.println("Hello ");
		}
		
		else if(a == Action.jump)
		{
			mario.jump();
			
			//System.out.println("Jump JUmp ");
		}
		
		else //(a == Action.run_and_jump)
		{
			
			mario.jump();
			mario.x += 10;
			//System.out.println("Run run and jump jump");
			
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