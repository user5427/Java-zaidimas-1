package gamestates.PlayingClasses;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelDisplay {
    private BufferedImage banner;
    private BufferedImage numArray, number;
    private int time;
    private final float fadeTime = 1.2f, showTime = 2;
    private int levelIndex;
    private boolean drawDisplay;

    public LevelDisplay(){
        banner =  LoadSave.GetSpriteAtlas(LoadSave.LEVEL_DISPLAY_BANNER);
        numArray = LoadSave.GetSpriteAtlas(LoadSave.NUM_ARRAY);
        showDisplay(0);
    }

    public void showDisplay(int levelIndex){
        this.levelIndex = levelIndex;
        drawDisplay = true;
        time = 0;
    }

    public void update(){
        if (drawDisplay)
            time++;
        if (time > (fadeTime + showTime) * Game.UPS_SET){
            drawDisplay = false;
            time = 0;
        }
    }

    public void draw(Graphics g){
        if (drawDisplay) {
            AlphaComposite acomp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) Math.clamp(1 - (time - showTime * Game.UPS_SET) / Game.UPS_SET / fadeTime, 0.0, 1.0));
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setComposite(acomp);
            float bannerScale = Game.SCALE * 2;
            number = numArray.getSubimage(levelIndex * 10, 0, 10, 11);
            g2d.drawImage(banner, (int)(Game.GAME_WIDTH / 2 - 115 * bannerScale) , (int)(Game.GAME_HEIGHT / 2 - 22 * bannerScale), (int)(banner.getWidth() * bannerScale), (int)(banner.getHeight() * bannerScale), null);
            g2d.drawImage(number, (int)(Game.GAME_WIDTH / 2 + 45 * bannerScale) , (int)(Game.GAME_HEIGHT / 2 - 8 * bannerScale), (int)(10 * bannerScale * 1.8f), (int)(11 * bannerScale * 1.8f), null);
            g2d.dispose();
        }

    }
}
