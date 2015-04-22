import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class GameLoop extends Applet implements Runnable, KeyListener{
	
	public static int x,y,paddlewidth,paddleheight; //NEW CODE PADDLEHEIGHT
	public static int ballCount, blockCount, powerupsCount;
	public Image offscreen;
	public Graphics d;
	public boolean left,right,spacebar,checkCollision;
	public long start;
	
	public static Ball[] balls = new Ball[10];
	public static Block[] blocks = new Block[36];
	public static Powerup[] powerups = new Powerup[5];
	
	public static int life = 3;
	
	public int counter = 0;
	
	public int numberOfBlocks = 36;
	
	public static boolean pierce;
	
	public static int level = 1;
	
	boolean pause = true;
	public static boolean gameover = false;
	
	public void run() { //Initialisation
		x = 400; 
		y = 550; //NEW CODE PADDLEWIDTH
		paddleheight = 20;
		paddlewidth = 120;
		
		blockCount = numberOfBlocks;
		
		balls[0] = new Ball(50,250); //Initial ball.
		
		for(int i = 0; i < 12; i++) blocks[i] = new Block(i*50,0); //Level Construction Example.
		for(int i = 12; i < 24; i++) blocks[i] = new Block(i*50-600,50); //Make it into a function called "createLevel"
		for(int i = 24; i < numberOfBlocks; i++) blocks[i] = new Block(i*50-1200,100); //and "if button pressed" create level so it builds once.
		
		for(int i = 1; i < balls.length; i++){ //Other balls
			balls[i] = new Ball(850,650);
		}
		
		for(int i = 0; i < powerups.length; i++){ //Other balls
			powerups[i] = new Powerup(850,650);
		}
		
		while(true) { //The "update" or "main" loop that repeats.
			ballCount = balls[0].alive+balls[1].alive+balls[2].alive+balls[3].alive+balls[4].alive+balls[5].alive+balls[6].alive+balls[7].alive+balls[8].alive+balls[9].alive;
			powerupsCount = powerups[0].alive+powerups[1].alive+powerups[2].alive+powerups[3].alive+powerups[4].alive;
			
			if(ballCount == 0) {
				if(pause) {
					life--;
					pause = false;
				}
			}
			
			if(life == 0) {
				gameover = true;
				if(spacebar) {
					createLevel(level);
				}
			}
			
			if(blockCount == 0 && spacebar) {
				level++;
				createLevel(level);
			}
			
			
			for(int i = 0; i < balls.length;i++) balls[i].update();
			
			for(int i = 0; i < blocks.length;i++) blocks[i].update();
			
			for(int i = 0; i < powerups.length;i++) powerups[i].update();
			
			if (left) x -= 5;
			if (right) x += 5;
			
			if(ballCount == 0 && spacebar && !gameover) {
				balls[0].setPos(400, 300);
				balls[0].alive = 1;
				balls[0].speedX = 3;
				balls[0].speedY = -3;
				pause = true;
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
	
	public void createLevel(int lev) {
		blockCount = numberOfBlocks;
		life = 3;
		gameover = false;
		
		for(int i = 0; i < balls.length; i++) {
			balls[i].setPos(900, 800);
		}
		for(int i = 0; i < powerups.length; i++) {
			powerups[i].setPos(900, 800);
		}
		
		if (lev == 1) {
			for(int i = 0; i < 12; i++) blocks[i].setPos(i*50,0); //Level Construction Example.
			for(int i = 12; i < 24; i++) blocks[i].setPos(i*50-600,50); //Make it into a function called "createLevel"
			for(int i = 24; i < numberOfBlocks; i++) blocks[i].setPos(i*50-1200,100); //and "if button pressed" create level so it builds once.
			for(int i = 0; i < blocks.length; i++) blocks[i].health = 1;
		}
		
		else if (lev == 2) {
			for(int i = 0; i < blocks.length; i++) blocks[i].setPos(50,50);
			for(int i = 0; i < blocks.length; i++) blocks[i].health = 1;
		}
		
		else if(lev == 3) {
			for(int i = 0; i < blocks.length; i++) blocks[i].setPos(50,50);
			for(int i = 0; i < blocks.length; i++) blocks[i].health = 1;
		}
		if(lev == 4) lev = 1;
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

}
