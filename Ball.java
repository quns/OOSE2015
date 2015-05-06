import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Ball {
	int xpos,ypos;
	float speedX = 3f;
	float speedY = 3f;
	int diameter;
	Random random = new Random();
	
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
			if(xpos + diameter > GameLoop.blocks[i].xpos && xpos < GameLoop.blocks[i].xpos+GameLoop.blocks[i].width && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {
				speedY *= -1;
				GameLoop.blocks[i].health--;
				if(GameLoop.blocks[i].health == 0) GameLoop.blockCount--;
				
				if(GameLoop.powerupsCount < 5) { //Add it so they randomly spawn instead of every time
					if(GameLoop.powerups[0].alive == 0 && random.nextInt(5)+1 == 1) {
						GameLoop.powerups[0].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
						GameLoop.powerups[0].alive = 1;
						GameLoop.powerups[0].newPower();
					}
					else if(GameLoop.powerups[1].alive == 0 && random.nextInt(5)+1 == 1) {
						GameLoop.powerups[1].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
						GameLoop.powerups[1].alive = 1;
						GameLoop.powerups[1].newPower();
					}
					else if(GameLoop.powerups[2].alive == 0 && random.nextInt(5)+1 == 1) {
						GameLoop.powerups[2].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
						GameLoop.powerups[2].alive = 1;
						GameLoop.powerups[2].newPower();
					}
					else if(GameLoop.powerups[3].alive == 0 && random.nextInt(5)+1 == 1) {
						GameLoop.powerups[3].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
						GameLoop.powerups[3].alive = 1;
						GameLoop.powerups[3].newPower();
					}
					else if(GameLoop.powerups[4].alive == 0 && random.nextInt(5)+1 == 1) {
						GameLoop.powerups[4].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos);
						GameLoop.powerups[4].alive = 1;
						GameLoop.powerups[4].newPower();
					}
				}
			}
			
			else if(xpos > GameLoop.blocks[i].xpos+GameLoop.blocks[i].width && xpos < GameLoop.blocks[i].xpos+GameLoop.blocks[i].width+5 && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {//Left collision
				speedX *= -1;
				GameLoop.blocks[i].health--;
				if(GameLoop.blocks[i].health <= 0) GameLoop.blockCount--;
			}
			
			else if(xpos > GameLoop.blocks[i].xpos && xpos < GameLoop.blocks[i].xpos-5 && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {//Left collision
				speedX *= -1;
				GameLoop.blocks[i].health--;
				if(GameLoop.blocks[i].health <= 0) GameLoop.blockCount--;
			}
		}
		
		
		
		if (xpos < 0 || xpos > 780) { //Reverse direction if ball hits sides
			speedX *= -1;
			if(xpos < 0) xpos = 0;
			if(xpos > 800) xpos = 780;
		}
		
		if (ypos < 0) { //Reverse direction if ball hits top
			speedY *= -1;
			ypos = 0;
		}
		
		if(ypos > 600) { //Reset ball if it hits bottom
			GameLoop.ballCount--; //Could perhaps be removed
			alive = 0;
		}
		
		if(checkCollision == true) { //Update
			if(xpos + diameter > GameLoop.x && xpos < GameLoop.x+GameLoop.paddlewidth && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight) { //Paddle detection top
				speedY *= -1;

				speedX += ((xpos-GameLoop.x)-(0.5f * GameLoop.paddlewidth))/50f; //Vary
				
				if (speedX > 5) speedX = 5; //Clamp speed
				if (speedX < -5) speedX = -5;

				checkCollision = false;
				start = System.currentTimeMillis();
			} 
			//Update
			if(xpos > GameLoop.x+GameLoop.paddlewidth && xpos < GameLoop.x+GameLoop.paddlewidth+5 && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight && speedX < 0) { //Paddle detection right
				speedY *= -1;
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis();
			}
			//Update
			if(xpos > GameLoop.x && xpos < GameLoop.x-5 && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight && speedX > 0) { //Paddle detection left
				speedY *= -1;
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis();
			}
		}
		
		if(checkCollision == false) { //Reactivate checkCollision in 1 second. Prevents the ball from hitting multiple hitboxes. Delay
			long elapsedTime = System.currentTimeMillis() - start;
			if(elapsedTime > 1000) checkCollision = true;
		}
		
	}
	
	public void display(Graphics d) { //d = layer it will be drawn on
		//d.drawOval(xpos, ypos, diameter,diameter); 
		d.setColor(Color.gray);
		d.fillOval(xpos, ypos, diameter,diameter);
	}
	
}
