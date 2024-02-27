package levels;

/**
 * Does not support WorldManager and FileManager classes.
 * Does level saving in 2d array, one dimension array is the standard.
 *
 * @deprecated use {@link #LevelData} instead.  
 */
@Deprecated
public class Level {
	private int[][] lvlData;
	
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
		
	}
	
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
	
	public int[][] getLevelData(){
		return lvlData;
	}
}
