import java.awt.Color;

public class Figure {
	Point center;
	Color color;
	Material m;
	double t;
	Direction normal;
	boolean is_sphere=false;
	boolean is_light=false;
	String name;
	
	public Figure(Point center, Color color, Material m, Direction normal) {
		this.center = center;
		this.color = color;
		this.m = m;
		this.normal=normal;
		this.name= "h";
	}
	
	public void setSphere() {
		is_sphere=true;
		System.out.println("true?");
	}
	
	public void setLight(boolean luz) {
		is_light=luz;
		System.out.println("true?");
	}
	
	public Direction getNormal(Point p) {
		if (is_sphere == false) {
			return normal; 
		}
		else {
			System.out.println("on sphere");
			Direction d=  Geometry.subD(p,getCenter()).normalize();
			return d;
		}
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
