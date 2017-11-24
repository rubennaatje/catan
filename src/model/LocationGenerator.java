package model;

import java.awt.Point;
/*responsibility:
	-builds a playboard in gridlocations
	-returns Point for a specific location
	-returns bordering points for a Hex*/

public class LocationGenerator {
	
	private Point[][] coordinates;
	
	//probably gonna make this a static utility class
	
	public LocationGenerator(double dimension, double width) {
		//leftside of array is X, right is Y
		coordinates = new Point[13][13];
		for (int x = 0; x <= 12; x++) {
			for (int y = 0; y <= 12; y++) {
				double tempX = (x * (dimension/12)) + ( (width - dimension)/2);
				double tempY = (((8 -y) * (dimension/10.5)) + (x * (dimension/21)));
				coordinates[x][y] = new Point((int)tempX, (int)tempY);
			}
		}
	}
	
	public Point getCoordinate(GridLocation p) throws Exception {
		if(p.x<0 || p.x>coordinates.length || p.y<0 || p.y>coordinates[0].length) throw new Exception("Parameter x" + p.toString() + " not valid");
		return coordinates[p.x][p.y];
	}
	public Point[][] getCoordinates() {
		return coordinates;
	}
	
	public Point[] getHexEdges(GridLocation p) throws Exception  {
		if(p.x<0 || p.x>coordinates.length || p.y<0 || p.y>coordinates[0].length) throw new Exception("Parameter x" + p.toString() + " not valid");
		Point[] outPoint = new Point[6];
		
		outPoint[0] = coordinates[p.x-1][p.y-1];
		outPoint[1] = coordinates[p.x][p.y-1];
		outPoint[2] = coordinates[p.x+1][p.y];
		outPoint[3] = coordinates[p.x+1][p.y+1];
		outPoint[4] = coordinates[p.x][p.y+1];
		outPoint[5] = coordinates[p.x-1][p.y];
		return outPoint;
		
	}
}
