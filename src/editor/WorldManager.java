package editor;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import main.Game;

public class WorldManager {
	private LevelData levelData;
	private int xSize;
	private int ySize;
	
	private int lvlData[];
	EditorUI editorUI;
	
	private boolean drawTiles;
	Point cursorPos;
	float offsetX, offsetY;
	
	
	public WorldManager(EditorUI editorUI) {
		this.editorUI = editorUI;
		xSize = Game.TILES_IN_WIDTH;
		ySize = Game.TILES_IN_HEIGHT;
		lvlData = new int[xSize * ySize];
		for (int i = 0; i < ySize; i++)
			for (int j = 0; j < xSize; j++) {
				int index = i * xSize + j;
				lvlData[index] = 11;
			}
		levelData = new LevelData(lvlData, xSize, ySize);
		cursorPos = new Point();
	}
	
	public void update() {
		
		calculateOffset();
		UpdateSize();
		DrawTile();
		editorUI.setWorld(levelData);
	}
	
	private void calculateOffset() {
		offsetX = cursorPos.x - (editorUI.getFocusPoint().x * editorUI.getScale());
		offsetY = cursorPos.y - (editorUI.getFocusPoint().y * editorUI.getScale());
	}

	private void UpdateSize() {
		int newX = (int)(editorUI.getXSize() * 6 * Game.TILES_IN_WIDTH);
		int newY = (int)(editorUI.getYSize() * 6 * Game.TILES_IN_HEIGHT);
		
		if (newX != xSize || newY != ySize) {
			int ret[] = CheckIfDeletedAreaEmpty(newX, newY);
			newX = ret[0];
			newY = ret[1];
			int newArray[] = new int[newX * newY];
			minimizeArea(newArray, newX, newY);
			maximizeArea(newArray, newX, newY);
			levelData.setxSize(newX);
			levelData.setySize(newY);
			levelData.setLvlData(newArray);
		}
		
		
	}

	private void maximizeArea(int newArray[], int newX, int newY) {
		for (int i = 0; i < newY; i++)
			for (int j = 0; j < newX; j++) {
				if (i >= levelData.getySize() || j >= levelData.getxSize()) {
					int newIndex = i * newX + j;
					newArray[newIndex] = 11;
				}
			}
		
	}

	private void minimizeArea(int newArray[], int newX, int newY) {
		for (int i = 0; i < levelData.getySize(); i++)
			for (int j = 0; j < levelData.getxSize(); j++) {
				if (i < newY && j < newX) {
					int oldIndex = i * levelData.getxSize() + j;
					int newIndex = i * newX + j;
					newArray[newIndex] = levelData.getValueAt(oldIndex);
				}
			}
	}

	private int[] CheckIfDeletedAreaEmpty(int newX, int newY) {
		int maxX = newX, maxY = newY;
		for (int i = 0; i < levelData.getySize(); i++)
			for (int j = 0; j < levelData.getxSize(); j++) {
				if (i >= newY || j >= newX) {
					int index = i * levelData.getxSize() + j;
					if (levelData.getValueAt(index) != 11) {	
						if (maxX < j+1)
							maxX = j+1;
						if (maxY < i+1)
							maxY = i+1;
						
					}
				}
			}
		int returnVal[] = new int[2];
		returnVal[0] = maxX;
		returnVal[1] = maxY;
		return returnVal;
	}

	private void updateCursorPos(MouseEvent e) {
		cursorPos.x = e.getX();
		cursorPos.y = e.getY();
	}

	private void DrawTile() {
		if (!editorUI.getMouseInUse() && drawTiles) {
			int xTile = (int)(offsetX / (Game.TILES_SIZE * editorUI.getScale()));
			int yTile = (int)(offsetY / (Game.TILES_SIZE * editorUI.getScale()));
			int index = yTile * levelData.getxSize() + xTile;
			if (xTile < levelData.getxSize() && yTile < levelData.getySize() && xTile >= 0 && yTile >= 0)
				levelData.setNewValueAt(index, editorUI.getSelectedTile());
				//lvlData[index] = editorUI.getSelectedTile();
		}
	}
	
	public void mousePressed(MouseEvent e) {
		drawTiles = true;
		updateCursorPos(e);
	}
	
	public void mouseDragged(MouseEvent e) {
		updateCursorPos(e);	
	}

	public void mouseReleased(MouseEvent e) {
		drawTiles = false;
	}
	
	public void mouseMoved(MouseEvent e) {
		drawTiles = false;
	}
	
	public LevelData getLevelData() {
		return levelData;
	}
	
	public void setLevelData(LevelData levelData) {
		this.levelData = levelData;
		lvlData = levelData.getLvlData();
		xSize = levelData.getxSize();
		ySize = levelData.getySize();
	}

	
	
}
