package editor.editorUI_elements;

import static utils.Constants.UI.VolumeButtons.SLIDER_DEFAULT_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_DEFAULT_HEIGHT;
import static utils.Constants.UI.VolumeButtons.VOLUME_DEFAULT_WIDTH;
import static utils.Constants.UI.VolumeButtons.VOLUME_WIDTH;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import editor.EditorUI;
import utils.LoadSave;
import static utils.Constants.EditorUI.SLIDE_BUTTON_WIDTH;;
public class Slider extends SliderPropert {
	
	private BufferedImage[] imgs;
	private BufferedImage slider;
	private int index = 0;
	private boolean mouseOver;
	private boolean mousePressed;
	private int buttonX, minX, maxX;
	private EditorUI editorUI;
	
	public Slider(int x, int y, int width, int height, EditorUI editorUI) {
		super(x, y, VOLUME_WIDTH, height);
		this.editorUI = editorUI;
		bounds.x = x + VOLUME_WIDTH / 2;
		buttonX = x + VOLUME_WIDTH / 2;
		// giving info for actual slider
		this.x = x;
		this.width = width;
		minX = x + VOLUME_WIDTH / 2;
		buttonX = minX + VOLUME_WIDTH / 3;
		maxX = x + width - VOLUME_WIDTH / 2;
		loadImgs();
		// TODO Auto-generated constructor stub
	}
	
	private void loadImgs() {
		// TODO Auto-generated method stub
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.VOLUME_BUTTONS);
		imgs = new BufferedImage[3];
		for (int i = 0; i < imgs.length; i++)
			imgs[i] = temp.getSubimage(i*VOLUME_DEFAULT_WIDTH, 0, VOLUME_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
	
		slider = temp.getSubimage(3*VOLUME_DEFAULT_WIDTH, 0, SLIDER_DEFAULT_WIDTH, VOLUME_DEFAULT_HEIGHT);
	}

	public void update() {
		index = 0;
		if(mouseOver)
			index = 1;
		if (mousePressed)
			index = 2;
	}
	
	public void draw(Graphics g) {
		g.drawImage(slider, x, y, width, height, null);
		g.drawImage(imgs[index], buttonX - SLIDE_BUTTON_WIDTH/2, y, SLIDE_BUTTON_WIDTH, height, null);
	}
	
	public void changeX(int x) {
		if (x < minX)
			buttonX = minX;
		else if (x > maxX)
			buttonX = maxX;
		else
			buttonX = x;
		
		bounds.x = buttonX - SLIDE_BUTTON_WIDTH / 2;
	}
	
	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	
	public void mouseDragged(MouseEvent e) {
		
		if (mousePressed) {
			editorUI.setMouseInUse(true);
			changeX(e.getX());
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if (isIn(e)) {
			editorUI.setMouseInUse(true);
			mousePressed = true;
		}	
	}
	
	public void mouseReleased(MouseEvent e) {
		resetBools();
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseOver = false;
		if (isIn(e)) {
			editorUI.setMouseInUse(true);
			mouseOver = true;
		}
			
	}

	
	private boolean isIn(MouseEvent e) {
		return bounds.contains(e.getX(), e.getY());
	}
	
	public float getValue() {
		
		return Math.clamp((buttonX - minX) / (float)(maxX - minX), 0, 1);
	}
}
