import java.awt.Color;
import java.awt.Graphics;

public class Block {
	int xpos, ypos;
	int health;
	int width;
	int height;
	
	public Block(int x, int y) { //Constructor
		this.health = 1;
		this.xpos = x;
		this.ypos = y;
		this.width = 40;
		this.height = 20;
	}
	
	public void update() {
		if(health < 0) {
			this.xpos = 850;
			this.ypos = 650;
		}
	}
	
	public void setPos(int x, int y) {
		this.xpos = x;
		this.ypos = y;
	}
	
	public void display(Graphics d) { //d = layer it will be drawn on
		//d.drawRect(xpos, ypos, width, height);
		if(health == 1) {
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
		
		else {
			d.setColor(Color.black);
			d.fillRect(xpos, ypos, width, height);
			this.xpos = 850;
			this.ypos = 650;
		}
	}

}
