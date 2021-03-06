import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
	//Creates objects
	Model model;
	Controller controller;
	View view;
	
	
	public Game()
	{
	
		//Objects
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		
		//Sets JFrame settings
		this.setTitle("Project 6 - Side Scroller Mario - Deep Copy");
		this.setSize(800, 960); 
		this.setFocusable(true);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		//tells us that contoller is in charge of mouse clicks
		view.addMouseListener(controller);
		
		//tells us that controller is in charge of handingling key clicks
		this.addKeyListener(controller);
		
	}
	
	//Responsibile for turtle movement
	public void run()
	{
		while(true)
		{
			controller.update();
			model.update();
			view.repaint(); // Indirectly calls View.paintComponent
			Toolkit.getDefaultToolkit().sync(); // Updates screen

			// Go to sleep for 50 miliseconds
			try
			{
				Thread.sleep(50);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}


	//Main program
	public static void main(String[] args)
	{ 
		Game g = new Game();
		g.model.load("map.json"); //loads JSON map
		g.run();
	}
}
