package za.co.multichoice.resource;

public class ResourceFile {
	
	//File properties
	public static final String inputFileName =  "./large_map.txt";
	public static final String outputfile = "./large_map.txt";

	
	//Terrain properties - Would be more efficient to store in a db or some properties file
	public static final int FLATLANDS = 1;
	public static final int FOREST = 2;
	public static final int MOUNTAIN = 3;
	public static final String WATER = "N/A";
}