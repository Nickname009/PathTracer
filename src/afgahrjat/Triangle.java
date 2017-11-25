import java.awt.Color;

import javax.swing.text.Position;

public class Triangle extends Figure {
	private Direction normal;
	Point p1;
	Point p2;
	Point p3;
	Point I = new Point(0, 0, 0);
	Direction u = new Direction(0, 0, 0);
	Direction v = new Direction(0, 0, 0);
	Direction n = new Direction(0, 0, 0);
	Direction w = new Direction(0, 0, 0);
	Direction w0 = new Direction(0, 0, 0);
	Plane plane;
	double s, t, r, a, b,d, uu, uv, vv, wu, wv, diq;
	private Geometry g = new Geometry(0, 0, 0);

	public Triangle(Point p1, Point p2, Point p3, Direction normal, Color color, double d, Material m) {
		super(new Point(0, 0, 0),color, m);
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.normal = normal;
		this.d = d;
		plane = new Plane(center, normal, color, d, m);

		System.out.println(p3.getX());
	    // get triangle edge vectors and plane normal
		center.sub(u, this.p2, this.p1);
		center.sub(v, this.p3, this.p1);
	    n.crossProduct(u, v);              // cross product
	    
	}

	public Direction getNormal() {
		return normal;
	}

	public void setNormal(Direction normal) {
		this.normal = normal;
	}

	public double intersect( Point O,Direction D) {
		/*if (n == 0)             // triangle is degenerate
	        return -1;                  // do not deal with this case
		( Ray R, Triangle T, Point* I )
*/

		    // is I inside T?
		    double uu, uv, vv, wu, wv, diq;
		    uu = center.dotProduct(u, u);
		    uv = center.dotProduct(u,v);
		    vv = center.dotProduct(v,v);

		    a = -center.dotProduct(n,center.sub(w0, O, p1));
		    b=center.dotProduct(n,D);
		    r = a / b;
		    I.add(O, D.scale(r));  
		    w=I.sub(w, I, p1);
		    // plane.intersect(O, D)
		    wu = center.dotProduct(w,u);
		    wv = center.dotProduct(w,v);
		    diq = uv * uv - uu * vv;

		    // get and test parametric coords
		    
		    s = (uv * wv - vv * wu) / diq;
		   // System.out.println(s);
		    if (s < 0.0 || s > 1.0)         // I is outside T
		        return -1;
		    t = (uv * wu - uu * wv) / diq;
		   // System.out.println(t);
		    if (t < 0.0 || (s + t) > 1.0)  // I is outside T
		        return -1;

		    return 1;                       // I is in T
		}
}
