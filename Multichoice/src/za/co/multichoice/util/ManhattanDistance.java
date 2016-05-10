package za.co.multichoice.util;

public class ManhattanDistance {
	private static ManhattanDistance instance = null;
	
	private ManhattanDistance() {
	}
	
	public static ManhattanDistance getInstance() {
		if (instance == null) {
			instance = new ManhattanDistance();
		}
		
		return instance;
	}
	
	/**
	 * calculates the L1 distance between 2 points using Manhattan distance 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return 
	 */
	public int calculateDistance(int x1, int y1, int x2, int y2) {
		int result = -99999999;
		
		result = (Math.abs(x1 - x2) + Math.abs(y1 - y2));
		
		return result;
	}
	

}
