import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Ball {
	int xpos,ypos;
	int speedX = 3;
	int speedY = 3;
	int diameter;
	
	int alive = 1;
	boolean checkCollision = true;
	
	long start;
	
	public Ball(int x, int y) { //Constructor REQUIRED even if it is empty
		this.xpos = x;
		this.ypos = y;
		this.diameter = 20;
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
		
		for(int i = 0; i < GameLoop.blocks.length;i++) { //Block collision top
			if(xpos < GameLoop.blocks[i].xpos+35 && xpos > GameLoop.blocks[i].xpos-5 && ypos > GameLoop.blocks[i].ypos-20 && ypos < GameLoop.blocks[i].ypos+18 ) {//Left collision
				speedY *= -1;
				GameLoop.blocks[i].health--;
			}
		}
		
		for(int i = 0; i < GameLoop.blocks.length;i++) { //Block collision Right
			if(xpos < GameLoop.blocks[i].xpos+45 && xpos > GameLoop.blocks[i].xpos+40 && ypos > GameLoop.blocks[i].ypos-20 && ypos < GameLoop.blocks[i].ypos+18 ) {//Left collision
				speedX *= -1;
				GameLoop.blocks[i].health--;
			}
		}
		
		for(int i = 0; i < GameLoop.blocks.length;i++) { //Block collision Left
			if(xpos < GameLoop.blocks[i].xpos-5 && xpos > GameLoop.blocks[i].xpos && ypos > GameLoop.blocks[i].ypos-20 && ypos < GameLoop.blocks[i].ypos+18 ) {//Left collision
				speedX *= -1;
				GameLoop.blocks[i].health--;
			}
		}
		
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
		d.drawOval(xpos, ypos, diameter,diameter); 
	}
	
}
