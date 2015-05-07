import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;

public class Ball {
	int xpos,ypos; //X and Y position of ball.
	float speedX = 3f; //The horizontal speed of the ball
	float speedY = 3f; //The vertical speed of the ball
	int diameter; //Ball diameter
	Random random = new Random(); //Random variable for RNG
	
	int alive = 1; //Whether or not the ball is alive
	boolean checkCollision = true; //If true, collides with paddle
	
	long start; //Long variable for time calculations
	
	public Ball(int x, int y) { //Constructor
		this.xpos = x; //Ball starting position X
		this.ypos = y; //Ball starting position Y
		this.diameter = 20; //Diameter
	}

	public void update() { //Update and calculate position and collisions.
		if(alive == 1) { //If the ball is alive, keep incrementing it with the current speed.
		xpos += speedX;
		ypos += speedY;
		
		UpdateCollision(); //Calculate collisions
		}
	}
	
	public void setPos(int a, int b) { //Sets the x,y position of the ball
		xpos = a;
		ypos = b;
	}
	
	private void UpdateCollision() { //Do the collision for everything.
		
		for(int i = 0; i < GameLoop.blocks.length;i++) { //Block collision top
			if(xpos + diameter > GameLoop.blocks[i].xpos && xpos < GameLoop.blocks[i].xpos+GameLoop.blocks[i].width && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {
				speedY *= -1; //If top of bottom of block is hit, reverse ball vertical direction
				GameLoop.blocks[i].health--; //Block loses health
				if(GameLoop.blocks[i].health == 0) GameLoop.blockCount--; //If block dies, reduce int block count 
				
				if(GameLoop.powerupsCount < 5) { //Powerup spawn
					if(GameLoop.powerups[0].alive == 0 && random.nextInt(5)+1 == 1) { //Random variable, 1 in 5 chance of spawning
						GameLoop.powerups[0].setPos(GameLoop.blocks[i].xpos,GameLoop.blocks[i].ypos); //Spawn powerup at block
						GameLoop.powerups[0].alive = 1; //Set powerup to alive
						GameLoop.powerups[0].newPower(); //Give the powerup a new power, so it is not static
					}//Repeated below, making sure that the same powerup is not asked to spawn elsewhere
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
			//Block collision right
			else if(xpos > GameLoop.blocks[i].xpos+GameLoop.blocks[i].width && xpos < GameLoop.blocks[i].xpos+GameLoop.blocks[i].width+5 && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {//Left collision
				speedX *= -1; //Reverse horizontal direction
				GameLoop.blocks[i].health--;
				if(GameLoop.blocks[i].health <= 0) GameLoop.blockCount--;
			}
			//Block collision left
			else if(xpos > GameLoop.blocks[i].xpos && xpos < GameLoop.blocks[i].xpos-5 && ypos + diameter > GameLoop.blocks[i].ypos && ypos < GameLoop.blocks[i].ypos + GameLoop.blocks[i].height) {//Left collision
				speedX *= -1; //Reverse horizontal direction
				GameLoop.blocks[i].health--;
				if(GameLoop.blocks[i].health <= 0) GameLoop.blockCount--;
			}
		}
		
		
		
		if (xpos < 0 || xpos > 780) { //Reverse direction if ball hits sides
			speedX *= -1;
			if(xpos < 0) xpos = 0; //Limit the ball from going out of bounds
			if(xpos > 800) xpos = 780; //Or from getting itself stuck on the edge
		}
		
		if (ypos < 0) { //Reverse direction if ball hits top
			speedY *= -1;
			ypos = 0;
		}
		
		if(ypos > 600) { //Reset ball if it hits bottom
			GameLoop.ballCount--; //Remove health if it goes out. Failsafe, this is included in alive.
			alive = 0;
		}
		
		if(checkCollision == true) { //Update
			//Paddle collision
			if(xpos + diameter > GameLoop.x && xpos < GameLoop.x+GameLoop.paddlewidth && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight) { //Paddle detection top
				speedY *= -1;

				speedX += ((xpos-GameLoop.x)-(0.5f * GameLoop.paddlewidth))/50f; //Variation to the ball trajectory based on paddle hit location
				
				if (speedX > 5) speedX = 5; //Clamp horizontal speed
				if (speedX < -5) speedX = -5;

				checkCollision = false; //Disable collisions with paddle for a short time, prevents lodging ball inside.
				start = System.currentTimeMillis(); //Start timer for reenabling collisions
			} 
			//Paddle collision right
			if(xpos > GameLoop.x+GameLoop.paddlewidth && xpos < GameLoop.x+GameLoop.paddlewidth+5 && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight && speedX < 0) { //Paddle detection right
				speedY *= -1; //Reverse x,y direction
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis(); //reset timer
			}
			//Paddle collisions left
			if(xpos > GameLoop.x && xpos < GameLoop.x-5 && ypos + diameter > GameLoop.y && ypos < GameLoop.y + GameLoop.paddleheight && speedX > 0) { //Paddle detection left
				speedY *= -1; //Reverse x,y direction
				speedX *= -1;
				checkCollision = false; //Disable collisions for a second afterwards to eliminate weird collisions
				start = System.currentTimeMillis(); //reset timer
			}
		}
		
		if(checkCollision == false) { //Reactivate checkCollision in 1 second. Prevents the ball from hitting multiple hitboxes. Delay
			long elapsedTime = System.currentTimeMillis() - start; //Calculates time from collision deactivation
			if(elapsedTime > 1000) checkCollision = true; //If 1000 ms has passed, reenable.
		}
		
	}
	
	public void display(Graphics d) { //Draw ball
		d.setColor(Color.gray); //d = layer it will be drawn on
		d.fillOval(xpos, ypos, diameter,diameter);
	}
	
}
