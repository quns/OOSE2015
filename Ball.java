import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Ball {
	int xpos,ypos;
	int speedX = 3;
	int speedY = 3;
	
	int alive = 1;
	boolean checkCollision = true;
	
	long start;
	
	public Ball() { //Constructor REQUIRED even if it is empty
		
	}

	public void update() {
		if(alive == 1) {
		xpos += speedX;
		ypos += speedY;
		
		UpdateCollision();
		}
	}
	
	public void setPos(int a, int b) {
		xpos = a;
		ypos = b;
	}
	
	private void UpdateCollision() {
		if (xpos < 1 || xpos > 780) { //Reverse direction if ball hits sides
			speedX *= -1;
		}
		
		if (ypos < 0) { //Reverse direction if ball hits top
			speedY *= -1;
		}
		
		if(ypos > 600) { //Reset ball if it hits bottom
			GameLoop.ballCount--;
			alive = 0;
		}
		
		if(checkCollision == true) {
			if(xpos < GameLoop.x+120 && xpos > GameLoop.x && ypos > GameLoop.y-20 && ypos < GameLoop.y+18 ) { //Paddle detection top
				speedY *= -1;
				checkCollision = false;
				start = System.currentTimeMillis();
			} //Right now the balls path is predetermined. Add some variation based on the balls hit on the paddle.
			
			if((xpos < GameLoop.x+130 && xpos > GameLoop.x+120 && ypos > GameLoop.y-20 && ypos < GameLoop.y+18 && speedX <0)) { //Paddle detection right
				speedY *= -1;
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis();
			}
			
			if((xpos > GameLoop.x-15 && xpos < GameLoop.x && ypos > GameLoop.y-20 && ypos < GameLoop.y+18 && speedX >0)) { //Paddle detection left
				speedY *= -1;
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis();
			}
		}
		
		if(checkCollision == false) { //Reactivate checkCollision in 1 second. Delay
			long elapsedTime = System.currentTimeMillis() - start;
			if(elapsedTime > 1000) checkCollision = true;
		}
		
	}
	
	public void display(Graphics d) { //d = layer it will be drawn on
		d.drawOval(xpos, ypos, 20,20); 
	}
	
}
