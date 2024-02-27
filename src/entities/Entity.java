package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

// Abstract - never create an object of this class
public abstract class Entity {
	// Protected - only classes that extends this class can use this variable
	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float hitbox;
	private Rectangle2D.Float initialHitbox;
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	protected void drawHitbox(Graphics g) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
	}
	
	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y, width, height);
		initialHitbox = new Rectangle2D.Float(x, y, width, height);
	}
	
//	protected void updateHitbox() {
//		hitbox.x = (int)x;
//		hitbox.y = (int)y;
//	}
	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}
	
	public void resetHitbox() {
		hitbox = new Rectangle2D.Float(initialHitbox.x, initialHitbox.y, initialHitbox.width, initialHitbox.height);
	}
}
