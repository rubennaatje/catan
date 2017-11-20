package model;

import java.awt.Point;

public class Location {
	
	private Point[][] coordinates;
	
	//probably gonna make this a static utility class
	
	public Location(double dimension) {
		//leftside of array is X, right is Y
		coordinates = new Point[13][13];
		for (int x = 0; x <= 12; x++) {
			for (int y = 0; y <= 12; y++) {
				double tempX = x * (dimension/12);
				double tempY = (((8 -y) * (dimension/10.5)) + (x * (dimension/21)));
				coordinates[x][y] = new Point((int)tempX, (int)tempY);
			}
		}
	}
	
	public Point getCoordinate(Integer x, Integer y) throws Exception {
		if(x<0 || x>coordinates.length || y<0 || y>coordinates[0].length) throw new Exception("Parameter x" + x.toString() + ":y" + y.toString() + " not valid");
		return coordinates[x][y];
	}
	public Point[][] getCoordinates() {
		return coordinates;
	}
	
	public Point[] getHexEdges(Integer x, Integer y) throws Exception  {
		if(x<1 || x>coordinates.length-1 || y<1 || y>coordinates[0].length-1) throw new Exception("Parameter x" + x.toString() + ":y" + y.toString() + " not valid");
		Point[] outPoint = new Point[6];
		
		outPoint[0] = coordinates[x-1][y-1];
		outPoint[1] = coordinates[x][y-1];
		outPoint[2] = coordinates[x+1][y];
		outPoint[3] = coordinates[x+1][y+1];
		outPoint[4] = coordinates[x][y+1];
		outPoint[5] = coordinates[x-1][y];
		return outPoint;
		
	}
}
