package editor.editorUI_elements;

import static utils.Constants.EditorUI.IMAGE_BAR_HEIGHT;
import static utils.Constants.EditorUI.IMAGE_BAR_WIDTH;
import static utils.Constants.EditorUI.SEPERATION_BETWEEN_FRAMES;
import static utils.Constants.EditorUI.SLIDER_SLOW_DOWN_SPEED;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import editor.EditorUI;
import main.Game;
import utils.LoadSave;

public class ImageSlider {
	private BufferedImage imageBar, imageBarTop;
	private BufferedImage tileImgs[], imgFrame;
	private int tileCount;
	
	private int xOffset;
	private int minXOffset, maxXOffset;
	private int beforeChange;
	private float inertiaSpeed;
	boolean sliderNotPressed;
	
	ImageFrame imageFrames[];
	
	private int selectedIndex;
	private EditorUI editorUI;
	
	public ImageSlider(BufferedImage tileImgs[], int tileCount, EditorUI editorUI){
		this.tileImgs = tileImgs;
		this.tileCount = tileCount;
		this.editorUI = editorUI;
		imgFrame = LoadSave.GetSpriteAtlas(LoadSave.IMAGE_FRAME);
		imageBar = LoadSave.GetSpriteAtlas(LoadSave.IMAGE_BAR);
		imageBarTop = LoadSave.GetSpriteAtlas(LoadSave.IMAGE_BAR_TOP);
		initImageFrames();
		getMinMaxSliderOffset();
	}
	
	
	
	private void getMinMaxSliderOffset() {
		int oneTileSize = (imgFrame.getWidth() + SEPERATION_BETWEEN_FRAMES);
		int tile_length = (tileCount-1) * oneTileSize;
		tile_length *= Game.SCALE;
		minXOffset = -tile_length;
		maxXOffset = 0;
	}

	private void initImageFrames() {
		imageFrames = new ImageFrame[tileCount];
		for (int i = 0; i < tileCount; i++)
			imageFrames[i] = new ImageFrame(i, imgFrame, tileImgs[i], (int)(Game.GAME_WIDTH / 2 + i *(imgFrame.getWidth() + SEPERATION_BETWEEN_FRAMES) * Game.SCALE) , 340);
		
	}
	
	public void update() {
		if (sliderNotPressed)
			simulateSliderInertia();
		for(int i = 0; i < tileCount; i++)
			imageFrames[i].update();
	}
	
	private void simulateSliderInertia() {
		xOffset += (int)Math.clamp(inertiaSpeed, -20, 20);
		if (xOffset > maxXOffset) {
			xOffset = maxXOffset;
			inertiaSpeed = 0;
		}
		else if (xOffset < minXOffset) {
			xOffset = minXOffset;
			inertiaSpeed = 0;
		}
			
				
		
		
		if (Math.abs(inertiaSpeed) <= SLIDER_SLOW_DOWN_SPEED)
			inertiaSpeed = 0;
		else {
			if (inertiaSpeed > 0)
				inertiaSpeed -= SLIDER_SLOW_DOWN_SPEED;
			else
				inertiaSpeed += SLIDER_SLOW_DOWN_SPEED;
		}
		
	}

	public void draw(Graphics g) {
		g.drawImage(imageBar, (int)(Game.GAME_WIDTH / 2 - IMAGE_BAR_WIDTH / 2), (int)(300 * Game.SCALE), IMAGE_BAR_WIDTH, IMAGE_BAR_HEIGHT, null);
		for(int i = 0; i < tileCount; i++)
			imageFrames[i].draw(g, xOffset);
		g.drawImage(imageBarTop, (int)(Game.GAME_WIDTH / 2 - IMAGE_BAR_WIDTH / 2), (int)(300 * Game.SCALE), IMAGE_BAR_WIDTH, IMAGE_BAR_HEIGHT, null);
	}
	
	public void mousePressed(MouseEvent e) {
		for (ImageFrame im : imageFrames) {
			if (isIn(e, im)) {
				editorUI.setMouseInUse(true);
				im.setMousePressed(true);
				break;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		sliderNotPressed = true;
		for (ImageFrame im : imageFrames) 
			im.resetBools();
		
		for (ImageFrame im : imageFrames) {
			if (isIn(e, im)) {
				editorUI.setMouseInUse(true);
				im.setMouseOver(true);
				break;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		for (ImageFrame im : imageFrames) {
			if (isIn(e, im)) {
				if (im.isMousePressed() && sliderNotPressed) {
					editorUI.setMouseInUse(true);
					setTile(im.getIndex());
				}
				sliderNotPressed = true;
				break;
			}
		}
		
		for (ImageFrame im : imageFrames)
			im.setMousePressed(false);;
	}
	
	private void setTile(int index) {
		selectedIndex = index;
		editorUI.setSelectedTile(selectedIndex);
	}
	
	public void mouseDragged(MouseEvent e) {
		for (ImageFrame im : imageFrames) {
			if (im.isMousePressed()) {
				editorUI.setMouseInUse(true);
				changeX(e.getX());
				break;
			}
		}
	}
	
	private void changeX(int change) {
		if (sliderNotPressed) {
			sliderNotPressed = false;
			beforeChange = change;
		}
		inertiaSpeed = change - beforeChange;
		int difference = change - beforeChange;
		beforeChange = change;
		xOffset += difference;

		if (xOffset > maxXOffset)
			xOffset = maxXOffset;
		else if (xOffset < minXOffset)
			xOffset = minXOffset;
	}

	public boolean isIn(MouseEvent e, ImageFrame imageFrame) {
		if (imageFrame.getBounds().contains(e.getX() - xOffset, e.getY()))
			if (imageFrame.isVisible(xOffset))
				return true;
		return false;
	}
}
