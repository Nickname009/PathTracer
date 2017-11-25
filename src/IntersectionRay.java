
public class IntersectionRay {
	Point p;
	Direction d;
	Ray r;
	Geometry g = new Geometry(0, 0, 0);
	
	public IntersectionRay() {
		p = new Point(0, 0, 0);
		d = new Direction(0, 0, 0);
		r = new Ray(p, p, d);
		
	}
	
	public Ray getEspecularRay(Point pA, Ray rP) {
	//	r.setD(g.sub(r.d, rP.getD(), ));
		return r;
	}
}
