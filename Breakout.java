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
		d.clearRect(0,0,800,600);
		
		d.drawRect(x,y,120,20); //Add to paddle object
		
		d.drawOval(xpos, ypos, 20,20); //Add to ball object
		//Ovals and rects are not drawn from center, but from corner for some reason.
		
		g.drawImage(offscreen,0,0,this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

}
