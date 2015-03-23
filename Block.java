import java.awt.Graphics;

public class Block {
	int xpos, ypos;
	int upperY, lowerY;
	int health;
	int width;
	int height;
	
	public Block(int x, int y) { //Constructor
		this.xpos = x;
		this.ypos = y;
		this.upperY = ypos;
		this.lowerY = ypos+20;
		this.width = 40;
		this.height = 20;
	}
	
	public void update() {
		
		if(health < 0) {
		xpos = 850;
		ypos = 650;
		}
	}
	
	public void display(Graphics d) { //d = layer it will be drawn on
		d.drawRect(xpos, ypos, width, height); 
	}

}
