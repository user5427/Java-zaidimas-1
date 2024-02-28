package levels;

public class LevelData {
	private int lvlData[];
	private int xSize, ySize;
	public LevelData(int lvlData[], int xSize, int ySize) {
		this.lvlData = lvlData;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	public LevelData(int lvlData[][], int xSize, int ySize) {
		setLvlData(lvlData, xSize, ySize);
		this.xSize = xSize;
		this.ySize = ySize;
	}

	public LevelData() {

	}

	public int[] getLvlData() {
		return lvlData;
	}
	public int[][] getLvlData2D() {
		int lvlData[][] = new int[ySize][xSize];
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				lvlData[i][j] = this.lvlData[i * xSize + j];
			}
		}
		return lvlData;
	}
	
	public void setLvlData(int[] lvlData) {
		this.lvlData = lvlData;
	}
	public void setLvlData(int[][] lvlData, int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.lvlData = new int[xSize * ySize];
		for (int i = 0; i < ySize; i++) {
			for (int j = 0; j < xSize; j++) {
				this.lvlData[i * xSize + j] = lvlData[i][j];
			}
		}
	}
	public void setLvlData(LevelData levelData) {
		this.lvlData = levelData.lvlData;
		this.xSize = levelData.xSize;
		this.ySize = levelData.ySize;
	}
	
	public void setNewValueAt(int index, int value) {
		if (index < lvlData.length)
			lvlData[index] = value;
	}
	
	public void setNewValueAtPoint(int x, int y, int value) {
		int index = y * xSize + x;
		if (index < lvlData.length)
			lvlData[index] = value;
	}
	
	public int getValueAt(int index) {
		if (index < lvlData.length)
			return lvlData[index];
		return -1;
	}
	
	public int getValueAtPoint(int x, int y) {
		int index = y * xSize + x;
		if (index < lvlData.length)
			return lvlData[index];
		return -1;
	}
	
	
	public int getxSize() {
		return xSize;
	}
	public void setxSize(int xSize) {
		this.xSize = xSize;
	}
	public int getySize() {
		return ySize;
	}
	public void setySize(int ySize) {
		this.ySize = ySize;
	}
	
	
}
