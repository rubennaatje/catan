package model;

import java.awt.Point;

public class Location {
	
	private Point[][] coordinates;
	
	public Location(double d, double e) {
		//leftside of array is X, right is Y
		coordinates = new Point[13][13];
		for (int i = 0; i <= 12; i++) {
			for (int j = 0; j <= 12; j++) {
				double tempX = i * (e/12);
				double tempY = ((12 -j) * (d /12)) + (i * (d/24));
				coordinates[i][j] = new Point((int)tempX, (int)tempY);
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
}
