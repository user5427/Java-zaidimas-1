package gamestates;

import editor.editorUI_elements.Button;
import editor.FileManagers.FileChooser;
import editor.FileManagers.FileManager;
import editor.editorUI_elements.MouseUser;
import levels.LevelData;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LevelChooser implements MouseUser, Statemethods{
    Game game;
    private BufferedImage backgroundImgPink;
    Button startGame;
    Button loadButton;
    Button menuButton;
    FileManager fileManager;
    FileChooser fileChooser;
    LevelData levelData;

    public LevelChooser(Game game){
        this.game = game;
        backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        int size = (int) (56 * Game.SCALE);
        int location = Game.GAME_WIDTH / 2 - size / 2;
        startGame = new Button(location, (int) (200 * Game.SCALE), size, size, 3, LoadSave.START_GAME_BUTTON);
        loadButton = new Button(location, (int) (270 * Game.SCALE), size, size, 3, LoadSave.LOAD_LEVEL_BUTTON);

        size = (int) (56 * Game.SCALE * 0.6f);
        location = Game.GAME_WIDTH / 2 - size / 2;
        menuButton = new Button(location, (int)(340 * Game.SCALE), size, size, 3, LoadSave.HOME_BUTTON);
        fileChooser = new FileChooser();
        levelData = new LevelData();
        fileManager = new FileManager(fileChooser, levelData);
    }

    public void update(){
        startGame.update();
        loadButton.update();
        menuButton.update();
        fileManager.update();
        if (fileManager.isLevelDataUpdated()){
            game.getPlaying().resetGame();
            game.getPlaying().getLevelManager().loadSpecificLevel(levelData);
            game.getPlaying().getPlayer().loadLvlData(levelData);
            Gamestate.state = Gamestate.PLAYING;
        }
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        startGame.draw(g);
        loadButton.draw(g);
        menuButton.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        startGame.mousePressed(e);
        loadButton.mousePressed(e);
        menuButton.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        startGame.mouseReleased(e);
        if (startGame.isTriggered()){
            startGame.resetTriggered();
            Gamestate.state = Gamestate.PLAYING;
        }
        loadButton.mouseReleased(e);
        if (loadButton.isTriggered()){
            loadButton.resetTriggered();
            fileChooser.setLoadOnly(true);
            fileChooser.openMenu();
        }
        menuButton.mouseReleased(e);
        if(menuButton.isTriggered()){
            menuButton.resetTriggered();
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        startGame.mouseMoved(e);
        loadButton.mouseMoved(e);
        menuButton.mouseMoved(e);
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
