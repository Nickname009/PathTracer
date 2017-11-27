
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

	public double module(Direction D) {
		return Math.sqrt(Math.pow(D.getX(), 2)+Math.pow(D.getY(), 2)+Math.pow(D.getZ(), 2));
	}
	
	public void doModule(Direction D) {
		D.setX(D.getX() /Math.sqrt(Math.pow(D.getX(), 2)+Math.pow(D.getY(), 2)+Math.pow(D.getZ(), 2)));
		D.setY(D.getY() /Math.sqrt(Math.pow(D.getX(), 2)+Math.pow(D.getY(), 2)+Math.pow(D.getZ(), 2)));
		D.setZ(D.getZ() /Math.sqrt(Math.pow(D.getX(), 2)+Math.pow(D.getY(), 2)+Math.pow(D.getZ(), 2)));
	}

	public Direction doModule() {
		this.setX(this.getX() /Math.sqrt(Math.pow(this.getX(), 2)+Math.pow(this.getY(), 2)+Math.pow(this.getZ(), 2)));
		this.setY(this.getY() /Math.sqrt(Math.pow(this.getX(), 2)+Math.pow(this.getY(), 2)+Math.pow(this.getZ(), 2)));
		this.setZ(this.getZ() /Math.sqrt(Math.pow(this.getX(), 2)+Math.pow(this.getY(), 2)+Math.pow(this.getZ(), 2)));
	    return this;
	}
	
	public Direction scale(double t) {
		Direction r = new Direction(0, 0, 0);
		r.setX(t*this.getX());
		r.setY(t*this.getY());
		r.setZ(t*this.getZ());
		return r;
	}

}