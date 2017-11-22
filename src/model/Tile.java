package model;

import java.awt.Point;

public class Tile {

	private Point location;
	//TODO: remove after pulling from 'geordie' branch
	
	private TileType tileType;
	
	public Tile(Point location,TileType resource) {
		setTileType(resource);
		this.setLocation(location);

	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}
	
	
	
}
