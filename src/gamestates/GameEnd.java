package gamestates;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The {@code GameEnd} class represents the end screen of the game, displayed when the player
 * completes the game. It includes methods to update and draw the end screen.
 */
public class GameEnd {
    /**
     * The {@code Game} instance associated with the game end screen.
     */
    Game game;

    /**
     * The banner image displayed on the win screen.
     */
    private BufferedImage winBanner;

    /**
     * The elapsed time since the end screen is displayed.
     */
    private int time;

    /**
     * The duration (in seconds) the end screen is shown before transitioning to the menu.
     */
    private int timeShow = 5;

    /**
     * The background image for the end screen.
     */
    private BufferedImage backgroundImgPink;

    /**
     * Constructs a new {@code GameEnd} instance with the specified {@code Game} instance.
     *
     * @param playing The {@code Game} instance associated with the end screen.
     */
    public GameEnd(Game playing){
        this.game = playing;
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
    }

    /**
     * Updates the end screen. It increments the elapsed time and resets the game and
     * transitions to the menu when the specified display time is exceeded.
     */
    public void update(){
        ++time;
        if (time > timeShow * Game.UPS_SET){
            game.getPlaying().resetGame();
            Gamestate.state = Gamestate.MENU;
            time = 0;

        }
    }

    /**
     * Draws the end screen using the specified {@code Graphics} object.
     *
     * @param g The {@code Graphics} object used for drawing.
     */
    public void draw(Graphics g){
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, (int)(30*Game.SCALE)));
        g.drawString("Congratulations! Your Score: " + game.getPlaying().getPlayer().getScore(), (int)(Game.GAME_WIDTH / 4), (int)(Game.GAME_HEIGHT / 2));
    }
}


