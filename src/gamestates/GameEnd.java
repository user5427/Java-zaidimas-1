package gamestates;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameEnd {
    Game game;
    private BufferedImage winBanner;
    private int time, timeShow = 5;
    private BufferedImage backgroundImgPink;

    public GameEnd(Game playing){
        this.game = playing;
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    public void update(){
        ++time;
        if (time > timeShow * Game.UPS_SET){
            game.getPlaying().resetGame();
            Gamestate.state = Gamestate.MENU;
            time = 0;

        }
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, (int)(30*Game.SCALE)));
        g.drawString("Congratulations! Your Score: " + game.getPlaying().getPlayer().getScore(), (int)(Game.GAME_WIDTH / 4), (int)(Game.GAME_HEIGHT / 2));
    }
}


