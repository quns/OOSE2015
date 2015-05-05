import java.awt.event.KeyEvent;
import java.util.Random;
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
	
	Random random = new Random();
	
	public static int numberOfBlocks = 500;
	
	public static boolean paddleWiden = false;
	
	public static Ball[] balls = new Ball[10];
	public static Block[] blocks = new Block[numberOfBlocks];
	public static Powerup[] powerups = new Powerup[5];
	
	public static int life = 3;
	
	public int counter = 0;
	
	int k = 36;
	
	public static int level = 1;
	
	boolean pause = true;
	public static boolean gameover = false;
	
	public void run() { //Initialisation
		x = 400; 
		y = 550; //NEW CODE PADDLEWIDTH
		paddleheight = 20;
		paddlewidth = 120;
		
		blockCount = 36;
		
		balls[0] = new Ball(50,550); //Initial ball.
		
		for(int i = 0; i < 12; i++) blocks[i] = new Block(i*50,0); //Level Construction Example.
		for(int i = 12; i < 24; i++) blocks[i] = new Block(i*50-600,50); //Make it into a function called "createLevel"
		for(int i = 24; i < 36; i++) blocks[i] = new Block(i*50-1200,100); //and "if button pressed" create level so it builds once.
		
		for(int i = 36; i < blocks.length; i++) blocks[i] = new Block(900,800);
		
		for(int i = 1; i < balls.length; i++){ //Other balls
			balls[i] = new Ball(850,650);
		}
		
		for(int i = 0; i < powerups.length; i++){ //Other balls
			powerups[i] = new Powerup(850,650);
		}
		
		while(true) { //The "update" or "main" loop that repeats.
			ballCount = balls[0].alive+balls[1].alive+balls[2].alive+balls[3].alive+balls[4].alive+balls[5].alive+balls[6].alive+balls[7].alive+balls[8].alive+balls[9].alive;
			powerupsCount = powerups[0].alive+powerups[1].alive+powerups[2].alive+powerups[3].alive+powerups[4].alive;
			
			if(blockCount < 0) blockCount = k;
			
			if(paddleWiden == true) paddlewidth = 250;
			else paddlewidth = 120;
			
			if(ballCount == 0) {
				if(pause) {
					life--;
					pause = false;
				}
			}
			
			if(life == 0) {
				gameover = true;
			}
			
			if(gameover == true) {
				if(spacebar) {
					createLevel(level);
				}
			}
			
			if(blockCount == 0 && spacebar) {
				level++;
				if(level >= 4) level = 1;
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
		blockCount = k;
		life = 3;
		gameover = false;
		
		for(int i = 0; i < balls.length; i++) {
			balls[i].setPos(900, 800);
		}
		for(int i = 0; i < powerups.length; i++) {
			powerups[i].setPos(900, 800);
		}
		
		if (lev == 1) {
			for(int i = 0; i < 12; i++) blocks[i] = new Block (i*50,0); //Level Construction Example.
			for(int i = 12; i < 24; i++) blocks[i] = new Block (i*50-600,50); //Make it into a function called "createLevel"
			for(int i = 24; i < 36; i++) blocks[i] = new Block(i*50-1200,100); //and "if button pressed" create level so it builds once.
			for(int i = 0; i < blocks.length; i++) blocks[i].health = 1;
			k = 36;
		}
		
		else if (lev == 2) {
			blockCount = 75;
			blocks[0] = new Block(25,25);
			blocks[1] = new Block(75,25);
			blocks[2] = new Block(125,25);
			blocks[3] = new Block(175,25);
			blocks[4] = new Block(225,25);
			blocks[5] = new Block(275,25);
			
			blocks[6] = new Block(50,55);
			blocks[7] = new Block(100,55);
			blocks[8] = new Block(150,55);
			blocks[9] = new Block(200,55);
			blocks[10] = new Block(250,55);
			
			blocks[11] = new Block(75,85);
			blocks[12] = new Block(125,85);
			blocks[13] = new Block(175,85);
			blocks[14] = new Block(225,85);
			
			blocks[15] = new Block(100,115);
			blocks[16] = new Block(150,115);
			blocks[17] = new Block(200,115);
			
			blocks[18] = new Block(125,145);
			blocks[19] = new Block(175,145);
			
			blocks[20] = new Block(150,175);
			
			blocks[21] = new Block(450+25,25);
			blocks[22] = new Block(450+75,25);
			blocks[23] = new Block(450+125,25);
			blocks[24] = new Block(450+175,25);
			blocks[25] = new Block(450+225,25);
			blocks[26] = new Block(450+275,25);
			
			blocks[27] = new Block(450+50,55);
			blocks[28] = new Block(450+100,55);
			blocks[29] = new Block(450+150,55);
			blocks[30] = new Block(450+200,55);
			blocks[31] = new Block(450+250,55);
			
			blocks[32] = new Block(450+75,85);
			blocks[33] = new Block(450+125,85);
			blocks[34] = new Block(450+175,85);
			blocks[35] = new Block(450+225,85);
			
			blocks[36] = new Block(450+100,115);
			blocks[37] = new Block(450+150,115);
			blocks[38] = new Block(450+200,115);
			
			blocks[39] = new Block(450+125,145);
			blocks[40] = new Block(450+175,145);
			
			blocks[41] = new Block(450+150,175);
			
			blocks[42] = new Block(250,275);
			blocks[43] = new Block(300,275);
			blocks[44] = new Block(350,275);
			blocks[45] = new Block(400,275);
			blocks[46] = new Block(450,275);
			blocks[47] = new Block(500,275);
			
			blocks[48] = new Block(275,245);
			blocks[49] = new Block(325,245);
			blocks[50] = new Block(375,245);
			blocks[51] = new Block(425,245);
			blocks[52] = new Block(475,245);
			
			blocks[53] = new Block(300,215);
			blocks[54] = new Block(350,215);
			blocks[55] = new Block(400,215);
			blocks[56] = new Block(450,215);
			
			blocks[57] = new Block(325,185);
			blocks[58] = new Block(375,185);
			blocks[59] = new Block(425,185);
			
			blocks[60] = new Block(350,155);
			blocks[61] = new Block(400,155);
			
			blocks[62] = new Block(375,125);
			
			blocks[63] = new Block(25,275);
			blocks[64] = new Block(75,275);
			blocks[65] = new Block(25,245);
			blocks[66] = new Block(75,245);
			blocks[67] = new Block(125,245);
			blocks[68] = new Block(125,275);
			
			blocks[69] = new Block(600+25,275);
			blocks[70] = new Block(600+75,275);
			blocks[71] = new Block(600+25,245);
			blocks[72] = new Block(600+75,245);
			blocks[73] = new Block(600+125,245);
			blocks[74] = new Block(600+125,275);
			
			for(int i = 0; i <= 74; i++) {
				blocks[i].health = random.nextInt(3)+1;;
			}
			k = 75;
		}
		
		else if(lev == 3) {
			blocks[0] = new Block(380,200);
			blocks[0].health = 3;
			blocks[1] = new Block(290,150);
			blocks[2] = new Block(335,150);
			blocks[3] = new Block(380,150);
			blocks[4] = new Block(425,150);
			blocks[5] = new Block(470,150);
			blocks[6] = new Block(290,250);
			blocks[7] = new Block(335,250);
			blocks[8] = new Block(380,250);
			blocks[9] = new Block(425,250);
			blocks[10] = new Block(470,250);
			blocks[11] = new Block(290,175);
			blocks[12] = new Block(290,200);
			blocks[13] = new Block(290,225);
			blocks[14] = new Block(470,175);
			blocks[15] = new Block(470,200);
			blocks[16] = new Block(470,225);
			blocks[17] = new Block(380,75);
			blocks[18] = new Block(380,325);
			blocks[19] = new Block(100,200);
			blocks[20] = new Block(650,200);
			
			blocks[21] = new Block(510,130);
			blocks[22] = new Block(555,105);
			blocks[23] = new Block(600,130);
			
			blocks[24] = new Block(250,130);
			blocks[25] = new Block(205,105);
			blocks[26] = new Block(160,130);
			
			blocks[27] = new Block(510,270);
			blocks[28] = new Block(550,290);
			blocks[29] = new Block(590,310);
			
			blocks[30] = new Block(250,270);
			blocks[31] = new Block(210,290);
			blocks[32] = new Block(170,310);
			
			blocks[33] = new Block(250-135,130-25);
			blocks[34] = new Block(205-135,105-25);
			blocks[35] = new Block(160-135,130-25);
			
			blocks[36] = new Block(510+135,130-25);
			blocks[37] = new Block(555+135,105-25);
			blocks[38] = new Block(600+135,130-25);
			
			for(int i = 1; i <= 39; i++) {
				blocks[i].health = random.nextInt(2)+1;;
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

}
