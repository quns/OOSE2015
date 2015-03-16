import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class GameLoop extends Applet implements Runnable, KeyListener{
	
	public int x,y,xpos,ypos,ballSpeedX,ballSpeedY;
	public Image offscreen;
	public Graphics d;
	public boolean up,down,left,right,checkCollision;
	public long start;
	
	public void run() {
		x = 400; //Initialization
		y = 550;
		checkCollision = true;
		
		xpos = 100;
		ypos = 300;
		ballSpeedX = 5;
		ballSpeedY = 5;
		
		while(true) { //The "update" or "main" loop that repeats.
			xpos += ballSpeedX;
			ypos += ballSpeedY;
			
			if (xpos < 1 || xpos > 780) { //Reverse direction if ball hits sides
				ballSpeedX *= -1;
			}
			
			if (ypos < 0) { //Reverse direction if ball hits top
				ballSpeedY *= -1;
			}
			
			if(ypos > 600) { //Reset ball if it hits bottom
				xpos = 400;
				ypos = 300;
			}
			
			if(checkCollision == true) {
				if(xpos < x+120 && xpos > x && ypos > y-20 && ypos < y+18 ) { //Paddle detection top
					ballSpeedY *= -1;
					checkCollision = false;
					start = System.currentTimeMillis();
				} //Right now the balls path is predetermined. Add some variation based on the balls hit on the paddle.
				
				if((xpos < x+130 && xpos > x+120 && ypos > y-20 && ypos < y+18 && ballSpeedX <0)) { //Paddle detection right
					ballSpeedY *= -1;
					ballSpeedX *= -1;
					checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
					start = System.currentTimeMillis();
				}
				
				if((xpos > x-15 && xpos < x && ypos > y-20 && ypos < y+18 && ballSpeedX >0)) { //Paddle detection left
					ballSpeedY *= -1;
					ballSpeedX *= -1;
					checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
					start = System.currentTimeMillis();
				}
			}
			
			if(checkCollision == false) { //Reactivate checkCollision in 1 second. Delay
				long elapsedTime = System.currentTimeMillis() - start;
				if(elapsedTime > 1000) checkCollision = true;
			}
			
			if (left) x -= 10;
			if (right) x += 10;
			if (up) y--;
			if (down) y++;
			
			repaint(); //Keep updating the display by calling paint
			
			try {
				Thread.sleep(20); //How fast the processor updates
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) { //Register key-presses
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 37) left = true;
		if(e.getKeyCode() == 39) right = true;
		if(e.getKeyCode() == 38) up = true;
		if(e.getKeyCode() == 40) down = true;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 37) left = false;
		if(e.getKeyCode() == 39) right = false;
		if(e.getKeyCode() == 38) up = false;
		if(e.getKeyCode() == 40) down = false;
	}

	public void keyTyped(KeyEvent e) {}

}
