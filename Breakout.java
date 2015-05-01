import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Color;

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
		
		d.setColor(Color.darkGray);
		d.fillRect(x,y,paddlewidth,paddleheight);
		//d.drawRect(x,y,paddlewidth,paddleheight); //The player box //NEW CODE paddlewidth, paddleheight
		
		for(int i = 0; i < balls.length;i++) balls[i].display(d);
		
		for(int i = 0; i < blocks.length;i++) blocks[i].display(d);
		
		for(int i = 0; i < powerups.length;i++) powerups[i].display(d);
		
		if(GameLoop.gameover) d.drawString("Game Over", 400, 300);
		
		if(GameLoop.blockCount == 0) d.drawString("You Win", 400, 300);
		
		d.setColor(Color.black);
		d.drawString("Lives: " + life, 750, 15);
		
		//Ovals and rects are not drawn from center, but from corner for some reason.
		
		g.drawImage(offscreen,0,0,this); //Redraw elements
	}
	
	public void update(Graphics g) {
		paint(g);
	}

} 
