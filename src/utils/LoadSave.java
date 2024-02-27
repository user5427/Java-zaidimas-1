package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;

import editor.LevelData;

public class LoadSave {
	public static final String PLAYER_ATLAS = "player_sprites.png";
	public static final String LEVEL_ATLAS = "new_tile_sheet.png";
	public static final String LEVEL_ONE_DATA = "level_one_complex.png";
	public static final String LEVEL_TWO_DATA = "levels/testi.png";
	public static final String LEVEL_THREE_DATA = "levels/level3.png";
	public static final String MENU_BUTTONS = "button_atlas.png";
	public static final String MENU_BACKGROUND = "menu_background.png";
	public static final String PAUSE_BACKGROUND = "pause_menu.png";
	public static final String SOUND_BUTTONS = "sound_button.png";
	public static final String URM_BUTTONS = "urm_buttons.png";
	public static final String VOLUME_BUTTONS = "volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "background_menu.png";
	public static final String PLAYING_BG_IMG = "playing_bg_img.png";
	public static final String BIG_CLOUDS = "big_clouds.png";
	public static final String SMALL_CLOUDS = "small_clouds.png";
	public static final String EDITOR_BUTTON = "editor_button.png";
	public static final String EDITOR_BANNER = "Editor_banner.png";
	public static final String IMAGE_BAR = "image_bar.png";
	public static final String IMAGE_FRAME = "image_frame.png";
	public static final String IMAGE_BAR_TOP = "image_bar_top.png";
	public static final String TILE_SELECTION = "tile_selection.png";
	public static final String MENU_BUTTON = "menu_button.png";
	public static final String FILES_BUTTON = "files_button.png";
	public static final String LEVEL_DISPLAY_BANNER = "level_display_banner.png";
	public static final String NUM_ARRAY = "num_array.png";
	public static final String START_GAME_BUTTON = "start_game_button.png";
	public static final String LOAD_LEVEL_BUTTON = "load_level_button.png";

	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;	
		InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
		try {
			img = ImageIO.read(is);				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try { 
				is.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		return img;
	}
	
	public static LevelData getLevelData(String lvlName){
		if (lvlName.contains("png")){
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
		} else {
			int[][] lvlData;// = new int[img.getHeight()][img.getWidth()];

			List<String> lines = null;
			try {
				lines = Files.readAllLines(Paths.get(lvlName), StandardCharsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			int ySize = lines.size();
			int xSize = 1;
			for (int i = 0; i < lines.get(0).length(); i++) {
				if (lines.get(0).charAt(i) == ',')
					xSize++;
			}
			lvlData = new int[ySize][xSize];
			
			for (int i = 0; i < ySize; i++) {
				String[] array = lines.get(i).split(",");
				for (int j = 0; j < xSize; j++) {
					lvlData[i][j] = Integer.parseInt(array[j]);
				}
			}
			LevelData levelData = new LevelData(lvlData, xSize, ySize);

            return levelData; 
		            
		}
		
	}
	
	
}
