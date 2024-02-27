package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.EditorButton;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements Statemethods{
	private MenuButton[] buttons = new MenuButton[3];
	private EditorButton editorButton;
	private BufferedImage backgroundImg;
	private BufferedImage backgroundImgPink;
	private int menuX, menuY, menuWidth, menuHeight;
	
	public Menu(Game game) {
		super(game);
		loadButtons();
		loadEditorButton();
		loadBackground();
		backgroundImgPink = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(backgroundImg.getWidth() * Game.SCALE);
		menuHeight = (int)(backgroundImg.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (45 * Game.SCALE);
		
	}

	private void loadButtons() {
		// TODO Auto-generated method stub
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.LEVEL_MENU);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTIONS);
		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
	}
	private void loadEditorButton() {
		editorButton = new EditorButton(Game.GAME_WIDTH / 2, (int) (387 * Game.SCALE), Gamestate.EDITING);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		for(MenuButton mb : buttons)
			mb.update();
		editorButton.update();
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImgPink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);
		for(MenuButton mb : buttons)
			mb.draw(g);
		editorButton.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// check if we are pressing in the button
		for(MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
				break;
			}
		}
		
		// for the editor button
		if (isIn(e, editorButton)) 
			editorButton.setMousePressed(true);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// if we are realesing mouse are we inside button
		for(MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed()) {
					mb.applyGamestate();
				}
				break;
			}
		}
		// for the editor button
		if (isIn(e, editorButton))
			if (editorButton.isMousePressed())
				editorButton.applyGamestate();
		
		
		resetButtons();
		editorButton.resetBools();
		
	}

	private void resetButtons() {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb : buttons) {
			mb.resetBools();
		}
		editorButton.resetBools();
		
		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}
		
		if (isIn(e, editorButton))
			editorButton.setMouseOver(true);
				
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.PLAYING;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
