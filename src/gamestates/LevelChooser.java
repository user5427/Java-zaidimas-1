package gamestates;

import editor.Button;
import editor.FileChooser;
import editor.LevelData;
import editor.MouseUser;
import main.Game;
import utils.LoadSave;

import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LevelChooser implements MouseUser, Statemethods{
    Game game;
    private BufferedImage backgroundImgPink;
    Button startGame;
    Button loadButton;

    FileChooser fileChooser;
    LevelData levelData;

    public LevelChooser(Game game){
        this.game = game;
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        int size = (int) (56 * Game.SCALE);
        int location = Game.GAME_WIDTH / 2 - size / 2;
        startGame = new Button(location, (int) (200 * Game.SCALE), size, size, 3, LoadSave.START_GAME_BUTTON);
        loadButton = new Button(location, (int) (270 * Game.SCALE), size, size, 3, LoadSave.LOAD_LEVEL_BUTTON);
    }

    public void update(){
        startGame.update();
        loadButton.update();
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        startGame.draw(g);
        loadButton.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startGame.mousePressed(e);
        loadButton.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startGame.mouseReleased(e);
        loadButton.mouseReleased(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        startGame.mouseMoved(e);
        loadButton.mouseMoved(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void setMouseInUse(boolean inUse) {

    }
}
