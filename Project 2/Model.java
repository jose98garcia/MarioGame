import java.util.ArrayList;

class Model
{

	//array list of bricks
	ArrayList<Brick> bricks;
	
	//Scrolling
	int scrollPos;
	

	Model()
	{
		//adding bricks to arraylist
		bricks = new ArrayList<Brick>();

	}

	public void update()
	{
	
	}

	//adds Bricks
	void addBrick(int x, int y, int w, int h)
	{
		Brick b = new Brick(x, y, w, h);
		bricks.add(b);
	}

	//Unmarshall
	void unmarshall(Json ob)
	{
		bricks.clear(); //clears any bricks already in it
		Json json_bricks = ob.get("bricks");
		
		for(int i = 0; i < json_bricks.size(); i++)
		{
			Json j = json_bricks.get(i); //gets thing that represents brick
			Brick b = new Brick(j); //makes a new brick
			bricks.add(b); //adds brick
		}	
	}	
	
	//Marshall
	Json marshall()
	{
		//JSon object that contains x,y,w,and h
		Json ob = Json.newObject();
		Json json_bricks = Json.newList();
		ob.add("bricks", json_bricks);
		for(int i = 0; i < bricks.size(); i++)
		{
			Brick b = bricks.get(i);
			Json j = b.marshall(); //turns to Json
			json_bricks.add(j); //adds to Json
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