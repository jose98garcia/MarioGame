import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{
	//objects
	View view;
	Model model;
	
	//member variables for keyboard controls
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean keyS;
	boolean keyL;
	boolean keySpace;
	
	int mouseDownX;
	int mouseDownY;
	
	Controller(Model m)
	{
		model = m;
	}
	
	
	void setView(View v)
	{
		view = v;
	}


	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	
	//Mouse Methods for movement
	public void mousePressed(MouseEvent e)
	{	
		
		//Gets location of coordinates when pressed on mouse
		mouseDownX = e.getX();
		mouseDownY = e.getY();	
		
		//draws coin blocks when clicked
		model.addBlock(mouseDownX + model.scrollPos, mouseDownY, 89, 83);
		
	}

	
	public void mouseReleased(MouseEvent e)
	{  

		//sets coordinates to variables 
		int x1 = mouseDownX;
		int x2 = e.getX();
		int y1 = mouseDownY;
		int y2 = e.getY();
		
		int left = Math.min(x1,x2);
		int right = Math.max(x1,x2);
		int top = Math.min(y1, y2);
		int bottom = Math.max(y1, y2);
		
		//creates brick when mouse is released
		//model.addBrick(left + model.scrollPos, top, right - left, bottom - top);
	
	}
	public void mouseEntered(MouseEvent e) {    }
	public void mouseExited(MouseEvent e) {    }
	public void mouseClicked(MouseEvent e) {    }
	
	//allows for entering of keys
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_S: model.save("map.json"); 
				if(keyS = true)
				{System.out.println("Map has been successfully saved");} break; //saves model with letter S
			case KeyEvent.VK_L: model.load("map.json");
				if(keyL = true)
				{System.out.println("Map has been successfully loaded");} break; //loads model with letter L */
			case KeyEvent.VK_SPACE: // allows mario to jump
				if(keySpace = true)
				{
					model.mario.jump();
	
				} break;
				
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
		}
	}
 
	public void keyTyped(KeyEvent e)
	{
	}


	void update()
	{
		model.mario.rememberPos();
		
		//Allows you to move mario
		
		if(keyRight)
		{
			model.mario.x += 10;
			//model.scrollPos++;
		}
		
		if(keyLeft)
		{
			model.mario.x -= 10;
			//model.scrollPos--;
			
		}
		
		
	}
}
