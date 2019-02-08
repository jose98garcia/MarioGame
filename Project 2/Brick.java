public class Brick

{
	//Positions and Dimensions of Brick
	int x; //x coordinate
	int y; //y coordinate
	int w; //width
	int h; //height
	

	Brick(int _x, int _y, int _w, int _h)
	{
		x = _x;
		y = _y;
		w = _w;
		h = _h;
		
	}
	
	//Unmarshall
	Brick(Json ob)
	{
		x = (int)ob.getLong("x");
		y = (int)ob.getLong("y");
		w = (int)ob.getLong("w");
		h = (int)ob.getLong("h");
	}	
	
	//Marshall
	Json marshall()
	{
		//JSon object that contains x,y,w,and h
		Json ob = Json.newObject();
         ob.add("x", x);
		 ob.add("y", y);
		 ob.add("w", w);
		 ob.add("h", h);
        
		return ob;
		
	}	



}

