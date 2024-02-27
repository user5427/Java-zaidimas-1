package editor;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;

import main.Game;
import static utils.Constants.EditorUI.*;
public class ImageFrame {
	private BufferedImage img, frame;
	private int x, y;
	Point framePos;
	private int frameWidth, frameWidthDefault;
	private int frameHeight, frameHeightDefault;
	Point imgPos;
	private int imgWidth, imgWidthDefault;
	private int imgHeight, imgHeightDefault;
	Point rectPos;
	private int rectWidth, rectWidthDefault;
	private int rectHeight, rectHeightDefault;
	private Rectangle bounds;
	private boolean mouseOver;
	private boolean mousePressed;
	
	private int space;
	private int barRight;
	private int barLeft;
	
	private int index;
	
	public ImageFrame(int index, BufferedImage frame, BufferedImage img, int x, int y) {
		this.index = index;
		this.frame = frame;
		this.img = img;
		this.x = x;
		this.y = y;
		findDimensions();
		findPositions();
		initBounds();
		findVisibilityLimits();
	}
	
	
	private void findVisibilityLimits() {
		space = (int)(20 * Game.SCALE);
		barRight = IMAGE_BAR_WIDTH / 2 + Game.GAME_WIDTH / 2 - space - frameWidth;
		barLeft = -IMAGE_BAR_WIDTH / 2 + Game.GAME_WIDTH / 2 + space;
		
	}


	private void findPositions() {
		framePos = new Point();
		framePos.x = x - frameWidth / 2;
		framePos.y = y;
		
		imgPos = new Point();
		imgPos.x = x - imgWidth / 2;
		imgPos.y = (int)((frameHeightDefault - imgHeightDefault) / 2 ) + y;
		
		rectPos = new Point();
		rectPos.x = x - rectWidth / 2;
		rectPos.y = (int)((frameHeightDefault - rectHeightDefault) / 2) + y;
		
	}


	private void initBounds() {
		bounds = new Rectangle();
		bounds.width = rectWidth;
		bounds.height = rectHeight;
		bounds.x = rectPos.x;
		bounds.y = (int)(rectPos.y * Game.SCALE);
	}

	private void findDimensions() {
		frameWidthDefault = frame.getWidth();
		frameHeightDefault = frame.getHeight();
		frameWidth = (int)(frameWidthDefault * Game.SCALE);
		frameHeight = (int)(frameHeightDefault * Game.SCALE);
		imgWidthDefault = img.getWidth();
		imgHeightDefault = img.getHeight();
		imgWidth = (int)(imgWidthDefault * Game.SCALE);
		imgHeight = (int)(imgHeightDefault * Game.SCALE);
		rectWidthDefault = imgWidthDefault + 10;
		rectHeightDefault = imgHeightDefault + 10;
		rectWidth = (int)(rectWidthDefault * Game.SCALE);
		rectHeight = (int)(rectHeightDefault * Game.SCALE);
	}


	public void update() {
		
	}
	
	public void draw(Graphics g, int xOffset) {
		int transperency = getTransperency(xOffset);
		if (mouseOver)
			g.setColor(new Color(0, 0, 0, 100));
		else
			g.setColor(new Color(0, 0, 0, 40));
		AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperency / 255.0f);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setComposite(acomp);
		g2d.drawImage(frame, framePos.x + xOffset, (int)(framePos.y * Game.SCALE), frameWidth, frameHeight, null);
		g2d.fillRect(rectPos.x + xOffset, (int)(rectPos.y * Game.SCALE), rectWidth, rectHeight);
		g2d.drawImage(img, imgPos.x + xOffset, (int)(imgPos.y * Game.SCALE), imgWidth, imgHeight, null);
		g2d.dispose();
	}
	
	private int getTransperency (int xOffset){
		int transperency = 0;
		if (framePos.x + xOffset > barRight) {
			transperency = Math.abs(barRight - (framePos.x + xOffset));
			transperency /= Game.SCALE;
		} else if (framePos.x + xOffset < barLeft) {
			transperency =  Math.abs(barLeft - (framePos.x + xOffset));
			transperency /= Game.SCALE;
		}
		transperency *= 10;
		transperency = Math.clamp(transperency, 0, 255);
		transperency = 255 - transperency;
		return transperency;
	}
	
	public boolean isVisible(int xOffset) {
		if (getTransperency(xOffset) != 0)
			return true;
		return false;
	}
	
	
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public int getIndex() {
		return index;
	}


	public boolean isMouseOver() {
		return mouseOver;
	}


	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}


	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	
	
	
}
