import java.awt.Color;

public class Figure {
	Point center;
	Color color;
	Material m;
	double t;
	Direction normal;
	
	public Figure(Point center, Color color, Material m, Direction normal) {
		this.center = center;
		this.color = color;
		this.m = m;
		this.normal=normal;
	}
	
	public Direction getNormal(Point p) {
		return normal; /*
		System.out.println(Geometry.subD(p,getCenter()).module());
		
		Geometry d=  Geometry.subD(p,getCenter());
		Direction dd = new Direction(d.getX(), d.getY(), d.getZ()).normalize();
		return dd; */
	}
	public Direction getNormal() {
		//System.out.println(Geometry.subD(p,getCenter()).doModule());
		return normal; 
	}
	public Material getM() {
		return m;
	}

	public void setM(Material m) {
		this.m = m;
	}

	public double getT() {
		return t;
	}

	public void setT(double t) {
		this.t = t;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public double intersect(Point n2, Direction d) {
		return -1;
	}

	public double getKD() {
		return m.kd;
	}

	public double getKS() {
		return m.ks;
	}
}
