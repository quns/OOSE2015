import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Breakout extends GameLoop implements WindowListener{ 
	
	public Breakout() { //Setup of window, size, processing thread, keylistening.
		setSize(800,600);
		
		Thread th = new Thread(this);
		th.start();
		
		this.addWindowListener(this);
		
		addKeyListener(this);
	}
	
	public void paint(Graphics g) { //Display graphics on screen
		offscreen = createImage(800,600); //Create offscreen to save graphics
		d = offscreen.getGraphics(); //Load graphics into offscreen
		d.clearRect(0,0,800,600); //Clear background
		
		d.setColor(Color.darkGray);
		d.fillRect(x,y,paddlewidth,paddleheight); //Create player paddle with gray color
		
		for(int i = 0; i < balls.length;i++) balls[i].display(d); //Initialisation of balls
		
		for(int i = 0; i < blocks.length;i++) blocks[i].display(d); //Initialisation of blocks
		
		for(int i = 0; i < powerups.length;i++) powerups[i].display(d); //Initialisation of powerups
		
		if(GameLoop.gameover) d.drawString("Game Over", 400, 300); //Display Game Over text when dead
		
		if(GameLoop.blockCount == 0) d.drawString("You Win", 400, 300); //Display Win text when level clear
		
		d.setColor(Color.black);
		d.drawString("Lives: " + life, 750, 65); //Create life display
		
		g.drawImage(offscreen,0,0,this); //Redraw elements
	}
	
	public void update(Graphics g) { //Update the graphics
		paint(g);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) { //Close window when pressing close
		// TODO Auto-generated method stub
		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
