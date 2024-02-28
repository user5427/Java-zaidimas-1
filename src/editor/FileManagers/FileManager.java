package editor.FileManagers;
import editor.FileManagers.FileChooser;
import levels.LevelData;

import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FileManager {
	private FileChooser fileChooser;
	private LevelData levelData;
	private boolean levelDataUpdated;
	public FileManager(FileChooser fileChooser, LevelData levelData) {
		this.fileChooser = fileChooser;
		this.levelData = levelData;
	}
	public void update() {
		levelDataUpdated = false;
		if (fileChooser.isHasFileName() && !fileChooser.getMenuState()) {
			processFile();
			fileChooser.resetChooser();
			levelDataUpdated = true;
		}
			
	}
	private void processFile() {
		if (fileChooser.isSaving())
			fileToImage(levelData, fileChooser.getFileName());
		else if (fileChooser.isLoading())
			levelData.setLvlData(getLevelData(fileChooser.getFileName()));
		
		
	}	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;		
		try {
			FileInputStream fis = new FileInputStream(fileName);
			img = ImageIO.read(fis);				
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return img;
	}
	
	public static LevelData getLevelData(String lvlName){
		BufferedImage img = GetSpriteAtlas(lvlName);
		int[][] lvlData = new int[img.getHeight()][img.getWidth()];
		
		for(int j = 0; j < img.getHeight(); j++)
			for(int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getRed();
				if (value >= img.getHeight() * img.getWidth())
					value = 0;
				lvlData[j][i] = value;
			}
		LevelData levelData = new LevelData(lvlData, img.getWidth(), img.getHeight());
		return levelData;
	}
	
	public static void fileToImage(LevelData levelData, String imageFile){
		BufferedImage image = new BufferedImage(levelData.getxSize(), levelData.getySize(), BufferedImage.TYPE_INT_RGB);
	    for (int y = 0; y < levelData.getySize(); y++) {
	        for (int x = 0; x < levelData.getxSize(); x++) {
	        	int index = y * levelData.getxSize() + x;
	            int red = levelData.getLvlData()[index];
	            int green = 0;
	            int blue = 0;
	            int rgb = (0xFF << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | (blue & 0xFF);
	            image.setRGB(x, y, rgb);
	        }
	    }
		try {
	    	ImageIO.write(image, "png", new File(imageFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public boolean isLevelDataUpdated() {
		return levelDataUpdated;
	}
}
