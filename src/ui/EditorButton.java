package ui;


import static utils.Constants.UI.EditorButtons.*;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import utils.LoadSave;

public class EditorButton {
	private int xPos, yPos, index;
	private int xOffsetCenter = EB_WIDTH / 2;
	private Gamestate state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	
	public EditorButton(int xPos, int yPos, Gamestate state){
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		loadImgs();
		initBounds();
	}

	private void initBounds() {
		// TODO Auto-generated method stub
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, EB_WIDTH, EB_HEIGHT);
	}

	private void loadImgs() {
		imgs = new BufferedImage[2];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.EDITOR_BUTTON);
		for(int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i * EB_WIDTH_DEFAULT, 0, EB_WIDTH_DEFAULT, EB_HEIGHT_DEFAULT);
		
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, EB_WIDTH, EB_HEIGHT, null);
	}
	
	public void update() {
		index = 0;
		if (mouseOver || mousePressed)
			index = 1;

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
	
	public void applyGamestate() {
		Gamestate.state = state;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	public Rectangle getBound() {
		return bounds;
	}
}
