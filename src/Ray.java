
public class Ray {
	Direction d;
	Point p0;
	Point p1;
	Point pIntersect = new Point(0, 0, 0);
	public Ray(Point p0, Point p1, Direction d) {
		this.d = d;
		this.p0 = p0;
		this.p1 = p1;
		d= d.scale(0.99999);
	}
	public Point getP1() {
		return p1;
	}
	
	public void setPIntersect(Point pIntersect) {
		this.pIntersect = pIntersect;
	}
	public Point getPintersect() {
		return pIntersect;
	}
	
	public void setP1(Point p1) {
		this.p1 = p1;
	}
	public Direction getD() {
		return d;
	}
	public void setD(Direction d) {
		this.d = d;
	}
	public Point getP0() {
		return p0;
	}
	public void setP0(Point p0) {
		this.p0 = p0;
	}
}
