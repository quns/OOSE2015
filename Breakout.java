import java.applet.Applet;
import java.awt.Graphics;

public class Breakout extends GameLoop{ //Options and setup. Launch this for the rest to work.

	public void init() {
		setSize(800,600);
		
		Thread th = new Thread(this);
		th.start();
		
		offscreen = createImage(800,600);
		d = offscreen.getGraphics();
		
		addKeyListener(this);
	}
	
	public void paint(Graphics g) { //Display graphics on screen
		d.clearRect(0,0,800,600); //Clear background
		
		d.drawRect(x,y,120,20); //Add to paddle object
		
		for(int i = 0; i < balls.length;i++) balls[i].display(d);
		
		//Ovals and rects are not drawn from center, but from corner for some reason.
		
		g.drawImage(offscreen,0,0,this); //Redraw elements
	}
	
	public void update(Graphics g) {
		paint(g);
	}

}
