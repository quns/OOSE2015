import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class GameLoop extends Applet implements Runnable, KeyListener{
	
	public static int x,y;
	public static int ballCount;
	public Image offscreen;
	public Graphics d;
	public boolean left,right,spacebar,checkCollision;
	public long start;
	
	public Ball[] balls = new Ball[10];
	
	public void run() { //Initialisation
		x = 400; 
		y = 550;
		
		balls[0] = new Ball(); //Initial ball.
		balls[0].setPos(50, 50);
		
		for(int i = 1; i < balls.length; i++){ //Other balls
			balls[i] = new Ball();
			balls[i].setPos(850,650);
		}
		
		while(true) { //The "update" or "main" loop that repeats.
			ballCount = balls[0].alive+balls[1].alive+balls[2].alive+balls[3].alive+balls[4].alive+balls[5].alive+balls[6].alive+balls[7].alive+balls[8].alive+balls[9].alive;
			
			for(int i = 0; i < balls.length;i++) balls[i].update();
			
			if (left) x -= 5;
			if (right) x += 5;
			
			if(ballCount == 0 && spacebar) {
				balls[0].setPos(400, 300);
				balls[0].alive = 1;
			}
			
			repaint(); //Keep updating the display by calling paint
			
			try {
				Thread.sleep(10); //How long the processor sleeps each cycle. Effectively fps, lower num = higher fps.
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
