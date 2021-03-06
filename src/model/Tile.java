package model;

public class Tile {

	private GridLocation location;
	private TileType tileType;
	private Integer value;
		
	public Tile(GridLocation location,TileType resource, Integer value) {
		setTileType(resource);
		this.setLocation(location);
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
	public GridLocation getLocation() {
		return location;
	}

	public void setLocation(GridLocation location) {
		this.location = location;
	}

	public TileType getTileType() {
		return tileType;
	}

	public void setTileType(TileType tileType) {
		this.tileType = tileType;
	}	
}
