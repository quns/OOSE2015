import java.awt.Color;
import java.awt.Graphics;

public class Block {
	int xpos, ypos; //Block x,y position
	int health; //Block health
	int width; //Block width
	int height; //block height
	
	public Block(int x, int y) { //Constructor
		this.health = 1; //Default health 1
		this.xpos = x;
		this.ypos = y;
		this.width = 40; //Default width 40
		this.height = 20; //Default height 20
	}
	
	public void update() {
		if(health < 0) { //If block is dead, move outside of active playing field
			this.xpos = 850;
			this.ypos = 650;
		}
	}
	
	public void setPos(int x, int y) { //Manually set x,y position
		this.xpos = x;
		this.ypos = y;
	}
	
	public void display(Graphics d) { //Display the block
		if(health == 1) { //Changes the color of the block depending on how much life it has.
			d.setColor(Color.lightGray);
			d.fillRect(xpos, ypos, width, height);	
		}
		
		else if(health == 2) {
			d.setColor(Color.gray);
			d.fillRect(xpos, ypos, width, height);	
		}
		
		else if(health == 3) {
			d.setColor(Color.darkGray);
			d.fillRect(xpos, ypos, width, height);	
		}
		
		else { //If the block is dead, make it black and force it outside of the playing field. Failsafe.
			d.setColor(Color.black);
			d.fillRect(xpos, ypos, width, height);
			this.xpos = 850;
			this.ypos = 650;
		}
	}

}
