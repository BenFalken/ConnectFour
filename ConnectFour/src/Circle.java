import java.awt.Color;
import java.awt.Graphics;

public class Circle {
	private Color color;
	private int id;
	
	public Circle(Color color, int id) {
		this.color = color;
		this.id = id;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void draw(Graphics g, int row, int col) {
		g.setColor(this.color);
		g.fillOval(100*col, 100*row, 100, 100);
	}
}
