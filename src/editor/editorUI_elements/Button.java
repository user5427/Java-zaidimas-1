package editor.editorUI_elements;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import utils.LoadSave;


public class Button extends EditorButton implements MouseUser {
	private BufferedImage[] imgs;
	private int animations, index;
	private boolean mouseOver, mousePressed;
	private String fileName;
	private boolean triggered;
	private boolean mouseInUse;
	
	public Button(int x, int y, int width, int height, int animations, String fileName) {
		super(x, y, width, height);
		this.fileName = fileName;
		this.animations = animations;
		loadImgs();
	}
	
	private void loadImgs() {
		// TODO Auto-generated method stub
		BufferedImage temp = LoadSave.GetSpriteAtlas(fileName);
		imgs = new BufferedImage[animations];
		for (int i = 0; i < animations; i++) {
			imgs[i] = temp.getSubimage(i * (temp.getWidth() / animations), 0, (temp.getWidth() / animations), temp.getHeight());
		}
	}

	public void update() {
		index = 0;
		if (mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
		
		if (index >= animations)
			index = animations - 1;
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, width, height, null);
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e)) {
			setMouseInUse(true);
			mousePressed = true;
		}
			
	}
	
	public void mouseReleased(MouseEvent e) {
		if (isIn(e)) {
			setMouseInUse(true);
			if (mousePressed) 
				triggered = true;
		}
		resetBools();
	}
	

	public boolean isTriggered() {
		return triggered;
	}
	
	public void resetTriggered() {
		triggered = false;
	}

	public void mouseMoved(MouseEvent e) {
		mouseOver = false;
		if (isIn(e)) {
			setMouseInUse(true);
			mouseOver = true;
		}
			
	}
	
	private boolean isIn(MouseEvent e) {
		return bounds.contains(e.getX(), e.getY());
	}

	@Override
	public void setMouseInUse(boolean inUse) {

	}
}
