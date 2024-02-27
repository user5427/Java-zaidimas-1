package gamestates;

import java.awt.event.MouseEvent;

import main.Game;
import ui.EditorButton;
import ui.MenuButton;

public class State {
	protected Game game;
	public State(Game game) {
		this.game = game;
	}
	
	public boolean isIn(MouseEvent e, MenuButton mb) {
		return mb.getBound().contains(e.getX(), e.getY());
	}
	
	public boolean isIn(MouseEvent e, EditorButton mb) {
		return mb.getBound().contains(e.getX(), e.getY());
	}
	
	public Game getGame() {
		return game;
	}
}
