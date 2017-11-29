

public class Camera{
	Direction l = new Direction(-3, 0, 0);
	Direction f = new Direction(0, 0, 0);
	Direction u = new Direction(0, -3, 0);
	Point center;
	public Camera(Point center, Point Orientation) {
		this.center=center;
		f= Geometry.subD(Orientation, center);
		//u.crossProduct(f, l);
		//u.doModule(u);
	}

	public Direction getl() {
		return l;
	}
	public void setl(Direction l) {
		this.l = l;
	}
	public Direction getf() {
		return f;
	}
	public void setf(Direction f) {
		this.f = f;
	}
	public Direction getu() {
		return u;
	}
	public void setu(Direction u) {
		this.u = u;
	}
	public Point getCenter() {
		return center;
	}
}
