import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Ball {
	int xpos,ypos;
	float speedX = 3f;
	float speedY = 3f;
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
	
	private void UpdateCollision() { //NEW CODE. ALL THE NUMBERS IN IF HAVE BEEN REPLACED
		
		for(int i = 0; i < GameLoop.blocks.length;i++) { //Block collision top
			if(xpos < GameLoop.blocks[i].xpos+35 && xpos > GameLoop.blocks[i].xpos-5 && ypos > GameLoop.blocks[i].ypos-20 && ypos < GameLoop.blocks[i].ypos+18 ) {//Left collision
				speedY *= -1;
				GameLoop.blocks[i].health--;
				//TEST
				if(GameLoop.powerupsCount < 5) { //Add it so they randomly spawn instead of every time
				GameLoop.powerups[GameLoop.powerupsCount].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
				GameLoop.powerups[GameLoop.powerupsCount].alive = 1;
				GameLoop.powerups[GameLoop.powerupsCount].newPower();
				}
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
			GameLoop.ballCount--; //Could perhaps be removed
			alive = 0;
		}
		
		if(checkCollision == true) { //NEW CODE PADDLEWIDTH + HEIGHT
			if(xpos < GameLoop.x+GameLoop.paddlewidth && xpos > GameLoop.x && ypos > GameLoop.y-GameLoop.paddleheight && ypos < GameLoop.y+GameLoop.paddleheight-2 ) { //Paddle detection top
				speedY *= -1;
				//NEW CODE
				speedX += ((xpos-GameLoop.x)-(0.5f * GameLoop.paddlewidth))/50f; //Vary
				if (speedX > 5) speedX = 5; //Clamp speed
				if (speedX < -5) speedX = -5;
				// END NEW CODE
				checkCollision = false;
				start = System.currentTimeMillis();
				
			} //Right now the balls path is predetermined. Add some variation based on the balls hit on the paddle.
			
			if((xpos < GameLoop.x+GameLoop.paddlewidth+10 && xpos > GameLoop.x+GameLoop.paddlewidth && ypos > GameLoop.y-GameLoop.paddleheight && ypos < GameLoop.y+GameLoop.paddleheight-2 && speedX <0)) { //Paddle detection right
				speedY *= -1;
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis();
			}
			
			if((xpos > GameLoop.x-15 && xpos < GameLoop.x && ypos > GameLoop.y-GameLoop.paddleheight && ypos < GameLoop.y+GameLoop.paddleheight-2 && speedX >0)) { //Paddle detection left
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
