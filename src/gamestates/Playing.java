package gamestates;

import entities.Player;
import gamestates.PlayingClasses.LevelDisplay;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import static utils.Constants.Enviroment.*;

public class Playing extends State implements Statemethods{
	private Player player;
	private LevelDisplay levelDisplay;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;
	
	private int xLvlOffset, yLvlOffset;
	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int upBorder = (int) (0.2 * Game.GAME_HEIGHT);
	private int downBorder = (int) (0.8 * Game.GAME_HEIGHT);

	private BufferedImage backgroundImg, bigCloud, smallCloud;
	private int[] smallCloudsPos;
	private Random rnd = new Random();
	
	public Playing(Game game) {
		super(game);
		initClasses();
		initClouds();
	}

	private void initClouds() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
		smallCloudsPos = new int[8];
		for (int i = 0; i < smallCloudsPos.length; i++)
			smallCloudsPos[i] = rnd.nextInt((int)(100 * Game.SCALE)) + (int)(90 * Game.SCALE);
	}

	private void initClasses() {
		levelManager = new LevelManager();
		player = new Player(200, 200, (int)(60 * Game.SCALE), (int)(40 * Game.SCALE));
		player.loadLvlData(levelManager.getCurrentLevel());
		pauseOverlay = new PauseOverlay(this);
		levelDisplay = new LevelDisplay();
	}

	@Override
	public void update() {
		levelDisplay.update();
		if (!paused) {
			levelManager.update();
			player.update();
			checkCloseToBorder();
		} else
			pauseOverlay.update();

		if (!player.isAlive())
			player.resetPlayer();
		if (player.isChangeLevel()){
			xLvlOffset = 0;
			yLvlOffset = 0;
			levelManager.changeLevel();
			player.resetPlayer();
			player.loadLvlData(levelManager.getCurrentLevel());
			levelDisplay.showDisplay(levelManager.getCurrentLevelIndex());
		}

		if (levelManager.gameFinished())
			Gamestate.state = Gamestate.WIN;
	}

	private void checkCloseToBorder() {
		int maxLvlOffsetX = (levelManager.getCurrentLevel().getxSize() - Game.TILES_IN_WIDTH) * Game.TILES_SIZE;
		int playerX = (int) player.getHitbox().x;
		int diffX = playerX - xLvlOffset;
		
		if (diffX > rightBorder)
			xLvlOffset += diffX - rightBorder;
		else if (diffX < leftBorder)
			xLvlOffset += diffX - leftBorder;
		
		if (xLvlOffset > maxLvlOffsetX && maxLvlOffsetX > 0)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;

		int maxLvlOffsetY = (levelManager.getCurrentLevel().getySize() - Game.TILES_IN_HEIGHT) * Game.TILES_SIZE;
		int playerY = (int) player.getHitbox().y;
		int diffY = playerY - yLvlOffset;

		if (diffY > downBorder)
			yLvlOffset += diffY - downBorder;
		else if (diffY < upBorder)
			yLvlOffset += diffY - upBorder;

		if (yLvlOffset > maxLvlOffsetY  && maxLvlOffsetY > 0)
			yLvlOffset = maxLvlOffsetY;
		else if (yLvlOffset < 0)
			yLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		
		drawClouds(g);

		levelManager.draw(g, xLvlOffset, yLvlOffset);
		player.render(g, xLvlOffset, yLvlOffset);
		
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
		 g.setColor(Color.BLACK);
         g.setFont(new Font("Arial", Font.PLAIN, 20));

         // Draw text at coordinates (x, y)
         g.drawString("SCORE: " + player.getScore(), (int)(50 * Game.SCALE), (int)(40 * Game.SCALE));
		levelDisplay.draw(g);
	}
	
	private void drawClouds(Graphics g) {
		for(int i = 0; i < 3; i++)
			g.drawImage(bigCloud, 0 + i * BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3), (int)(204 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
		
		for (int i = 0; i < smallCloudsPos.length; i++)
			g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT ,null);
	}

	public void mouseDragged(MouseEvent e) {
		if (paused)
			pauseOverlay.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// Button1 is left
		if(e.getButton() == MouseEvent.BUTTON1)
			player.setAttacking(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (paused)
			pauseOverlay.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (paused)
			pauseOverlay.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		if (paused)
			pauseOverlay.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(true);
			break;
		case KeyEvent.VK_A:
			player.setLeft(true);
			break;
		case KeyEvent.VK_S:
			player.setDown(true);
			break;
		case KeyEvent.VK_D:
			player.setRight(true);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(true);
			break;
		case KeyEvent.VK_ESCAPE:
			paused = !paused;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
			player.setUp(false);
			break;
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_S:
			player.setDown(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJump(false);
			break;
		
		}
	}
	
	public void unpauseGame() {
		paused = false;
	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}
	public LevelManager getLevelManager() { return levelManager; }
	public void resetGame(){
		player.resetPlayer();
		player.resetScore();
		levelManager.resetLevelManager();
		player.loadLvlData(levelManager.getCurrentLevel());
		levelDisplay.showDisplay(levelManager.getCurrentLevelIndex());
	}
}
