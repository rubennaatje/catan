package controller;

import java.awt.Point;

import model.Location;

public class TestClassPlayboard {

	public static void main(String[] args) throws Exception {
		Location locs = new Location(500, 500);
		Point[][] arff = locs.getCoordinates();
		
		for (int i = 0; i < arff.length; i++) {
			for (int j = 0; j < arff[i].length; j++) {
				System.out.println("x=" + i+ "|" + "y=" + j + arff[i][j].toString());				
			}
		}
		
		System.out.println(locs.getCoordinate(1, 10));
		;
	}

}
