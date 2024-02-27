package gamestates;

public enum Gamestate {
	PLAYING, MENU, OPTIONS, QUIT, EDITING, WIN, LEVEL_MENU;
	
	public static Gamestate state = MENU;
}
