
public class Direction extends Geometry {
	public Direction(double x, double y, double z) {
		super(x, y, z);
	}

	/*
	 * x->i
	 * j->y
	 * k->z
	 */
	public void crossProduct(Direction D, Direction E) {
		super.setX(D.getY() * E.getZ() - D.getZ() * E.getY());
		super.setY(D.getZ() * E.getX() - D.getX() * E.getZ());
		super.setZ(D.getX() * E.getY() - D.getY() * E.getX());
	}

	public double module() {
		return Math.sqrt((getX()*getX())+(getY()*getY())+(getZ()*getZ()));
	}
	
	public Direction normalize() {
		return new Direction(getX()/module(), getY()/module(), getZ()/module());
	}
	
	public Direction scale(double t) {
		Direction r = new Direction(0, 0, 0);
		r.setX(t*this.getX());
		r.setY(t*this.getY());
		r.setZ(t*this.getZ());
		return r;
	}

}