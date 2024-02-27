package utils;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import editor.LevelData;
import main.Game;

public class HelpMethods {
	public static boolean canMoveHere(float x, float y, float width, float height, int[][] lvlData) {
		if (!isSolid(x, y, lvlData)) 
			if (!isSolid(x+width, y+height, lvlData)) 
				if (!isSolid(x+width, y, lvlData)) 
					if (!isSolid(x, y+height, lvlData)) 
						return true;
			
		return false;
	}
	
	private static boolean isSolid(float x, float y, int[][] lvlData) {
		int maxWidth = lvlData[0].length * Game.TILES_SIZE;
		int maxHeight = lvlData.length * Game.TILES_SIZE;
		if (x < 0 || x >= maxWidth)
			return true;
		if (y < 0 || y >= maxHeight)
			return true;
		
		float xIndex = x / Game.TILES_SIZE;
		float yIndex = y / Game.TILES_SIZE;
		
		int value = lvlData[(int)(yIndex)][(int)(xIndex)];
		
		if (value == 11 || value == 48 || value == 50)
			return false;
		else
			return true;
		
	}
	
	public static List<Integer> getAllEffects(float x, float y, float width, float height, LevelData lvlData) {
		
	    List<Integer> resultList = new ArrayList<>();

	    resultList.add(getEffect(x-1, y-1, lvlData));
		resultList.add(getEffect(x+width+1, y+height+1, lvlData));
		resultList.add(getEffect(x+width+1, y-1, lvlData));
		resultList.add(getEffect(x-1, y+height+1, lvlData));
			
		return resultList;
	}
	
	private static int getEffect(float x, float y, LevelData lvlData) {
		
		if (x < 0 || x >= lvlData.getxSize() * Game.TILES_SIZE) {
			return -1;
		}
			
		if (y < 0 || y >= lvlData.getySize() * Game.TILES_SIZE)
			return -1;
		
		int xIndex = (int)(x / Game.TILES_SIZE);
		int yIndex = (int)(y / Game.TILES_SIZE);
		int value = lvlData.getValueAtPoint(xIndex, yIndex);
		if (value == 48) {
			lvlData.setNewValueAtPoint(xIndex, yIndex, 11);
			return 1;
		}
		else if (value == 49)
			return 2;
		else if (value == 50)
			return 3;
		
		return -1;
	}

	public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
		int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
		if (xSpeed > 0) {
			// right
			int tileXPos = currentTile * Game.TILES_SIZE;
			int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
			return tileXPos + xOffset - 1;
			
		} else {
			// left
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static float getEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
		int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
		if(airSpeed > 0) {
			// Falling - touching floor
			int tileYPos = currentTile * Game.TILES_SIZE;
			int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
			return tileYPos + yOffset - 1;
		} else {
			// Jumping
			return currentTile * Game.TILES_SIZE;
		}
	}
	
	public static boolean isEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
		// Check the pixel below the bottomleft and bottomright
		if (!isSolid(hitbox.x, hitbox.y+hitbox.height+1, lvlData))
			if (!isSolid(hitbox.x+hitbox.width, hitbox.y+hitbox.height+1, lvlData))
				return false;
		return true;
	}
}
