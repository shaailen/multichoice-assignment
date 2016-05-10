package za.co.multichoice.util;

import java.util.Comparator;

import za.co.multichoice.environment.Tile;

public class ScoreComparator implements Comparator<Tile>{
	
	@Override
	public int compare(Tile tile1, Tile tile2) {
		
		int tileScore1 = tile1.getScore();
		int tileScore2 = tile2.getScore();
		
		
		return getAscending(tileScore1, tileScore2);
	}
	
	private int getAscending(int score1, int score2) {
		return (score1 - score2);
	}
}
