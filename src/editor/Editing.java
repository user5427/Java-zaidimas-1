package editor;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import editor.FileManagers.FileManager;
import gamestates.Statemethods;
import main.Game;
/**
 * The Editing class is responsible for managing the tile map editing functionality in the game editor.
 * It serves as an implementation of the Statemethods interface, handling update and draw operations
 * related to the tile map editing state.
 *
 * The class integrates with various components such as the Game instance, EditorUI, WorldManager, and FileManager
 * to provide a comprehensive editing environment. It includes methods to update, draw, handle mouse events,
 * and respond to key events during the tile map editing process.
 */
public class Editing implements Statemethods{
	/** The Game instance associated with the editor. */
	Game game;
	/** The user interface for the editor. */
	EditorUI editorUI;
	/** Manages the tile map and its elements in the editor. */
	WorldManager worldManager;
	/** Manages file-related operations such as loading and saving tile map data. */
	FileManager fileManager;

	/**
	 * Constructs an Editing instance associated with the specified Game.
	 *
	 * @param game The Game instance to which the editing functionality is attached.
	 */
	public Editing(Game game) {
		this.game = game;
		editorUI = new EditorUI();
		worldManager = new WorldManager(editorUI);
		fileManager = new FileManager(editorUI.getFileChooser(), worldManager.getLevelData());
	}

	/**
	 * Updates the editing state by invoking update methods for the EditorUI, WorldManager, and FileManager.
	 */
	@Override
	public void update() {
		worldManager.update();
		editorUI.update();
		fileManager.update();
		
	}

	/**
	 * Draws the editor UI, providing a visual representation of the tile map editing environment.
	 *
	 * @param g The Graphics object used for drawing.
	 */
	@Override
	public void draw(Graphics g) {
		editorUI.draw(g);	
	}

	/**
	 * Handles the mouse drag event during tile map editing, updating both the EditorUI and WorldManager.
	 *
	 * @param e The MouseEvent representing the mouse drag event.
	 */
	public void mouseDragged(MouseEvent e) {
		// Indicates that the mouse is not currently in use, allowing appropriate handling of mouse drag.
		editorUI.setMouseInUse(false);
		// Delegates the mouse drag event to the EditorUI and WorldManager for further processing.
		editorUI.mouseDragged(e);
		worldManager.mouseDragged(e);
	}

	/**
	 * Handles the mouse click event.
	 *
	 * @param e The MouseEvent representing the mouse click event.
	 */
	@Override
	public void mouseClicked(MouseEvent e) { // This one
		// TODO Auto-generated method stub
		
	}

	/**
	 * Handles the mouse press event during tile map editing, updating both the EditorUI and WorldManager.
	 *
	 * @param e The MouseEvent representing the mouse press event.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// Indicates that the mouse is not currently in use, allowing appropriate handling of mouse drag.
		editorUI.setMouseInUse(false);
		// Delegates the mouse drag event to the EditorUI and WorldManager for further processing.
		editorUI.mousePressed(e);
		worldManager.mousePressed(e);
	}

	/**
	 * Handles the mouse release event during tile map editing, updating both the EditorUI and WorldManager.
	 *
	 * @param e The MouseEvent representing the mouse release event.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// Indicates that the mouse is not currently in use, allowing appropriate handling of mouse drag.
		editorUI.setMouseInUse(false);
		// Delegates the mouse drag event to the EditorUI and WorldManager for further processing.
		editorUI.mouseReleased(e);
		worldManager.mouseReleased(e);
	}

	/**
	 * Handles the mouse move event during tile map editing, updating both the EditorUI and WorldManager.
	 *
	 * @param e The MouseEvent representing the mouse move event.
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		editorUI.mouseMoved(e);
		worldManager.mouseMoved(e);
	}

	/**
	 * Handles the key press event during tile map editing, updating the EditorUI.
	 *
	 * @param e The KeyEvent representing the key press event.
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		editorUI.keyPressed(e);
	}

	/**
	 * Handles the key release event during tile map editing, updating the EditorUI.
	 *
	 * @param e The KeyEvent representing the key release event.
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		editorUI.keyReleased(e);
		
	}
	
}
