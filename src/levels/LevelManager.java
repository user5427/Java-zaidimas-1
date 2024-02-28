package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    private BufferedImage[] levelSprite;
    private LevelData currentLevel;
    private int currentLevelIndex;
    private final String[] levels = {utils.LoadSave.LEVEL_ONE_DATA, utils.LoadSave.LEVEL_TWO_DATA, utils.LoadSave.LEVEL_THREE_DATA};
    private boolean specificLevelMode;
    private boolean finishedSpecificLevel;
    public LevelManager() {
        importOutsideSprites();
        currentLevel = LoadSave.getLevelData(utils.LoadSave.LEVEL_ONE_DATA);
    }

    public void changeLevel() {
        currentLevelIndex++;
        if (currentLevelIndex < levels.length && !specificLevelMode)
            currentLevel = LoadSave.getLevelData(levels[currentLevelIndex]);

        if (specificLevelMode)
            finishedSpecificLevel = true;
    }

    public boolean gameFinished() {
        if (currentLevelIndex == levels.length || finishedSpecificLevel)
            return true;
        return false;
    }

    private void importOutsideSprites() {

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[12 * 5];
        for (int j = 0; j < 5; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }

    }

    public void draw(Graphics g, int lvlOffsetX, int lvlOffsetY) {
        for (int j = 0; j < currentLevel.getySize(); j++)
            for (int i = 0; i < currentLevel.getxSize(); i++) {
                int index = currentLevel.getValueAtPoint(i, j);
                g.drawImage(levelSprite[index], i * Game.TILES_SIZE - lvlOffsetX, j * Game.TILES_SIZE - lvlOffsetY, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }

    }

    public void update() {
    }

    public LevelData getCurrentLevel() {
        return currentLevel;
    }

    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public void resetLevelManager() {
        currentLevelIndex = 0;
        currentLevel = LoadSave.getLevelData(utils.LoadSave.LEVEL_ONE_DATA);
        specificLevelMode = false;
        finishedSpecificLevel = false;
    }

    public void loadSpecificLevel(LevelData levelData) {
        currentLevelIndex = 0;
        specificLevelMode = true;
        currentLevel = levelData;
    }
}
