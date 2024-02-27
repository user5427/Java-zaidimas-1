package editor;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import gamestates.Statemethods;
import main.Game;

public class Editing implements Statemethods{
	Game game;
	EditorUI editorUI;
	WorldManager worldManager;
	FileManager fileManager;
	
	public Editing(Game game) {
		this.game = game;
		editorUI = new EditorUI();
		worldManager = new WorldManager(editorUI);
		fileManager = new FileManager(editorUI.getFileChooser(), worldManager.getLevelData());
	}
	@Override
	public void update() {
		worldManager.update();
		editorUI.update();
		fileManager.update();
		
	}

	@Override
	public void draw(Graphics g) {
		editorUI.draw(g);	
	}
	
	public void mouseDragged(MouseEvent e) {
		editorUI.setMouseInUse(false);
		editorUI.mouseDragged(e);
		worldManager.mouseDragged(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) { // This one
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		editorUI.setMouseInUse(false);
		editorUI.mousePressed(e);
		worldManager.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		editorUI.setMouseInUse(false);
		editorUI.mouseReleased(e);
		worldManager.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		editorUI.mouseMoved(e);
		worldManager.mouseMoved(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		editorUI.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		editorUI.keyReleased(e);
		
	}
	
}
