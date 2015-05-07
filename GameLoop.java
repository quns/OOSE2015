import java.awt.event.KeyEvent;
import java.util.Random;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;

public class GameLoop extends Frame implements Runnable, KeyListener{
	
	public static int x,y,paddlewidth,paddleheight; //paddle x,y position and width,height
	public static int ballCount, blockCount, powerupsCount; //The number of active balls, blocks, powerups
	public Image offscreen; //What to save to the offscreen image
	public Graphics d; //What graphics to draw within screen
	public static boolean left,right,spacebar,checkCollision; //Booleans to check if a button is pressed, or if collisions should be calculated
	public long start; //Variable for calculating time passed
	
	static Random random = new Random(); //Random variable for RNG
	
	public static int numberOfBlocks = 500; //The total number of blocks that can be placed
	
	public static boolean paddleWiden = false; //If the paddle widdening powerup is enabled and in effect
	
	public static Ball[] balls = new Ball[10]; //Declaration + initialisation of the ball, 10 balls max
	public static Block[] blocks = new Block[numberOfBlocks]; //Declaration + initialisation of the blocks, 500 blocks max
	public static Powerup[] powerups = new Powerup[5]; //Declaration + initialisation of the powerups, 5 max
	
	public static int life = 3; //The players life
	
	public int counter = 0;
	
	static int k = 36; //The amount of blocks left on screen
	
	public static int level = 1; //Which level the player is at
	
	static boolean pause = true; //If the game is paused
	public static boolean gameover = false; //Gameover boolean
	
	public static void main(String[] args) { //Setup, declarations and initialisations of game logitstic
		Breakout f = new Breakout (); //Declare and initialise the game class, aka "Breakout"
		f.setSize(800,600); //Window size
		f.setVisible(true); //Enable window display
		f.setLayout(new FlowLayout()); //Create default layout
		
		x = 400; //Player starting x
		y = 550; //Player starting y
		paddleheight = 20; //Height of player
		paddlewidth = 120; //Width of player
		
		blockCount = 36; //Amount of blocks in first level
		
		balls[0] = new Ball(50,550); //Initial ball.
		
		for(int i = 0; i < 12; i++) blocks[i] = new Block(i*50,50); //Level Construction Example.
		for(int i = 12; i < 24; i++) blocks[i] = new Block(i*50-600,100); 
		for(int i = 24; i < 36; i++) blocks[i] = new Block(i*50-1200,150); 
		
		for(int i = 36; i < blocks.length; i++) blocks[i] = new Block(900,800); //Fill the entire block array
		
		for(int i = 1; i < balls.length; i++){ //Fill the entire ball array
			balls[i] = new Ball(850,650);
		}
		
		for(int i = 0; i < powerups.length; i++){ //Fill the entire powerup array
			powerups[i] = new Powerup(850,650);
		}
		
		f.init(); //Execute the "init" loop which is the update loop.
		
	}
	
	public void init() {
		while(true) { //The "update" or "main" loop that repeats.
			ballCount = balls[0].alive+balls[1].alive+balls[2].alive+balls[3].alive+balls[4].alive+balls[5].alive+balls[6].alive+balls[7].alive+balls[8].alive+balls[9].alive;
			powerupsCount = powerups[0].alive+powerups[1].alive+powerups[2].alive+powerups[3].alive+powerups[4].alive;
			//Calculates the total amount of balls and powerups on screen, depending on their alive integer. 
			
			if(x < 0) x = 0; //Limits player from leaving the left side of the screen
			if(x > 800-paddlewidth) x = 800-paddlewidth;  //Limits player from leaving the right side of the screen
			
			if(blockCount < 0) blockCount = k; //If the player clears a level, load the new levels number of blocks into count
			
			if(paddleWiden == true) paddlewidth = 250; //Set paddlewidth to 250 as long as powerup is enabled
			else paddlewidth = 120; //If powerup fades, return to default width
			
			if(ballCount == 0) { //No more balls are on screen
				if(pause) { //Pause the game
					life--; //Lose a life
					pause = false; //Pause disabled
				}
			}
			
			if(life == 0) { //If player has no more lives, game over
				gameover = true;
			}
			
			if(gameover == true) { //Game Over
				if(spacebar) { //Create the current level again from the start
					createLevel(level);
				}
			}
			
			if(blockCount == 0 && spacebar) { //No more blocks are left and space is pressed
				level++; //Go to next level
				if(level >= 4) level = 1; //If the player is on level 3, return to level 1
				createLevel(level); //Create the level
			}
			
			
			for(int i = 0; i < balls.length;i++) balls[i].update(); //Update all the balls
			
			for(int i = 0; i < blocks.length;i++) blocks[i].update(); //Update all the blocks
			
			for(int i = 0; i < powerups.length;i++) powerups[i].update(); //Update all the powerups
			
			if (left) x -= 5; //If left is pressed, decrement player x position with 5
			if (right) x += 5;//If right is pressed, increment player x position with 5
			
			if(ballCount == 0 && spacebar && !gameover) { //If player has no balls, is gameover and presses space
				balls[0].setPos(400, 300); //Spawn the initial ball at middle of screen
				balls[0].alive = 1; //Set it to alive
				balls[0].speedX = 3; //Give it a set speed
				balls[0].speedY = -3;
				pause = true; //Resume the game
			}
			
			repaint(); //Keep updating the display by calling paint
			
			try {
				Thread.sleep(8); //How long the processor sleeps each cycle. Effectively fps, lower num = higher fps.
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void createLevel(int lev) { //Level creation function
		blockCount = k; //Sets the number of blocks to k
		life = 3; //Resets player life to 3
		gameover = false; //Game over is false
		
		for(int i = 0; i < balls.length; i++) { //Reset all the balls to outside the screen
			balls[i].setPos(900, 800);
		}
		for(int i = 0; i < powerups.length; i++) { //Reset all the powerups to outside the screen
			powerups[i].setPos(900, 800);
		}
		
		if (lev == 1) { //Level construction level 1
			for(int i = 0; i < 12; i++) blocks[i] = new Block (i*50,50); //First row of blocks
			for(int i = 12; i < 24; i++) blocks[i] = new Block (i*50-600,100); //Second row of blocks
			for(int i = 24; i < 36; i++) blocks[i] = new Block(i*50-1200,150); //Third row of blocks
			for(int i = 0; i < blocks.length; i++) blocks[i].health = 1; //All blocks have 1 health
			k = 36; //Sets the number of blocks on screen to 36
		}
		
		else if (lev == 2) { //Level construction level 2
			blockCount = 75; //Blockcount to how many blocks are on screen
			blocks[0] = new Block(25,75); //Blocks were manually placed
			blocks[1] = new Block(75,75);
			blocks[2] = new Block(125,75);
			blocks[3] = new Block(175,75);
			blocks[4] = new Block(225,75);
			blocks[5] = new Block(275,75);
			
			blocks[6] = new Block(50,105);
			blocks[7] = new Block(100,105);
			blocks[8] = new Block(150,105);
			blocks[9] = new Block(200,105);
			blocks[10] = new Block(250,105);
			
			blocks[11] = new Block(75,135);
			blocks[12] = new Block(125,135);
			blocks[13] = new Block(175,135);
			blocks[14] = new Block(225,135);
			
			blocks[15] = new Block(100,165);
			blocks[16] = new Block(150,165);
			blocks[17] = new Block(200,165);
			
			blocks[18] = new Block(125,195);
			blocks[19] = new Block(175,195);
			
			blocks[20] = new Block(150,225);
			
			blocks[21] = new Block(450+25,75);
			blocks[22] = new Block(450+75,75);
			blocks[23] = new Block(450+125,75);
			blocks[24] = new Block(450+175,75);
			blocks[25] = new Block(450+225,75);
			blocks[26] = new Block(450+275,75);
			
			blocks[27] = new Block(450+50,105);
			blocks[28] = new Block(450+100,105);
			blocks[29] = new Block(450+150,105);
			blocks[30] = new Block(450+200,105);
			blocks[31] = new Block(450+250,105);
			
			blocks[32] = new Block(450+75,135);
			blocks[33] = new Block(450+125,135);
			blocks[34] = new Block(450+175,135);
			blocks[35] = new Block(450+225,135);
			
			blocks[36] = new Block(450+100,165);
			blocks[37] = new Block(450+150,165);
			blocks[38] = new Block(450+200,165);
		
			blocks[39] = new Block(450+125,195);
			blocks[40] = new Block(450+175,195);
			
			blocks[41] = new Block(450+150,225);
			
			blocks[42] = new Block(250,325);
			blocks[43] = new Block(300,325);
			blocks[44] = new Block(350,325);
			blocks[45] = new Block(400,325);
			blocks[46] = new Block(450,325);
			blocks[47] = new Block(500,325);
			
			blocks[48] = new Block(275,295);
			blocks[49] = new Block(325,295);
			blocks[50] = new Block(375,295);
			blocks[51] = new Block(425,295);
			blocks[52] = new Block(475,295);
			
			blocks[53] = new Block(300,265);
			blocks[54] = new Block(350,265);
			blocks[55] = new Block(400,265);
			blocks[56] = new Block(450,265);
		
			blocks[57] = new Block(325,235);
			blocks[58] = new Block(375,235);
			blocks[59] = new Block(425,235);
			
			blocks[60] = new Block(350,205);
			blocks[61] = new Block(400,205);
			
			blocks[62] = new Block(375,175);
			
			blocks[63] = new Block(25,325);
			blocks[64] = new Block(75,325);
			blocks[65] = new Block(25,295);
			blocks[66] = new Block(75,295);
			blocks[67] = new Block(125,295);
			blocks[68] = new Block(125,295);
			
			blocks[69] = new Block(600+25,325);
			blocks[70] = new Block(600+75,325);
			blocks[71] = new Block(600+25,325);
			blocks[72] = new Block(600+75,295);
			blocks[73] = new Block(600+125,295);
			blocks[74] = new Block(600+125,325);
			
			for(int i = 0; i <= 74; i++) {
				blocks[i].health = random.nextInt(3)+1; //Assign the blocks a random health number from 1 to 3
			}
			k = 75; //Set the number of blocks on screen to 75
		}
		
		else if(lev == 3) { //Level construction level 3
			blockCount = 39;
			blocks[0] = new Block(380,250);
			blocks[0].health = 3;
			blocks[1] = new Block(290,200);
			blocks[2] = new Block(335,200);
			blocks[3] = new Block(380,200);
			blocks[4] = new Block(425,200);
			blocks[5] = new Block(470,200);
			blocks[6] = new Block(290,300);
			blocks[7] = new Block(335,300);
			blocks[8] = new Block(380,300);
			blocks[9] = new Block(425,300);
			blocks[10] = new Block(470,300);
			blocks[11] = new Block(290,225);
			blocks[12] = new Block(290,250);
			blocks[13] = new Block(290,275);
			blocks[14] = new Block(470,225);
			blocks[15] = new Block(470,250);
			blocks[16] = new Block(470,275);
			blocks[17] = new Block(380,125);
			blocks[18] = new Block(380,375);
			blocks[19] = new Block(100,250);
			blocks[20] = new Block(650,250);
			
			blocks[21] = new Block(510,180);
			blocks[22] = new Block(555,155);
			blocks[23] = new Block(600,180);
			
			blocks[24] = new Block(250,180);
			blocks[25] = new Block(205,155);
			blocks[26] = new Block(160,180);
			
			blocks[27] = new Block(510,320);
			blocks[28] = new Block(550,340);
			blocks[29] = new Block(590,360);
			
			blocks[30] = new Block(250,320);
			blocks[31] = new Block(210,340);
			blocks[32] = new Block(170,370);
			
			blocks[33] = new Block(250-135,130+25);
			blocks[34] = new Block(205-135,105+25);
			blocks[35] = new Block(160-135,130+25);
			
			blocks[36] = new Block(510+135,130+25);
			blocks[37] = new Block(555+135,105+25);
			blocks[38] = new Block(600+135,130+25);
			
			for(int i = 1; i <= 39; i++) {
				blocks[i].health = random.nextInt(2)+1; //Assign the blocks a random health value from 1 to 2
			}
			k = 39;
		}
	}
	
	public void keyPressed(KeyEvent e) { //Register key-presses
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 37) left = true;
		if(e.getKeyCode() == 39) right = true;
		if(e.getKeyCode() == 32) spacebar = true;
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == 37) left = false;
		if(e.getKeyCode() == 39) right = false;
		if(e.getKeyCode() == 32) spacebar = false;
	}

	public void keyTyped(KeyEvent e) {}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
