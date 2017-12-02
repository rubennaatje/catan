package model;

import java.awt.Point;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class GridLocation extends Point {

	public GridLocation(int x, int y) throws Exception {
		super();
		if (x > 12 || x < 0)
			throw new Exception(x + ": X value out of bounds");
		if (y > 12 || y < 0)
			throw new Exception(y + ": Y value out of bounds");
		setLocation(x, y);
	}

	@Override
	public void setLocation(int x, int y) {
		try {
			if (x > 12 || x < 0)
				throw new Exception(x + ": X value out of bounds");
			if (y > 12 || y < 0)
				throw new Exception(y + ": Y value out of bounds");
			super.setLocation(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLocation(Point p) {
		try {
			if (p.x > 12 || p.x < 0)
				throw new Exception(x + ": X value out of bounds");
			if (p.y > 12 || p.y < 0)
				throw new Exception(y + ": Y value out of bounds");
			super.setLocation(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLocation(double x, double y) {
		try {
			if (x > 12 || x < 0)
				throw new Exception(x + ": X value out of bounds");
			if (y > 12 || y < 0)
				throw new Exception(y + ": Y value out of bounds");
			super.setLocation(x, y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLocation(Point2D p) {
		try {
			if (p.getX() > 12 || p.getX() < 0)
				throw new Exception(x + ": X value out of bounds");
			if (p.getY() > 12 || p.getY() < 0)
				throw new Exception(y + ": Y value out of bounds");
			super.setLocation(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
