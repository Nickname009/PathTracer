import java.awt.Color;

public class Sphere extends Figure {

	private double radio;

	public Sphere(Point center, double radio, Color color, Material m, Direction n) {
		super(center, color, m,n );
		this.radio = radio;
		is_sphere=true;
		setSphere();
	}


	public Direction getNormal(Point p) {
		Direction d= Geometry.subD(p,super.getCenter()).normalize();
		return d; 
	}
	
	public double intersect(Point O, Direction d) {
		// Interseccion esfera
		t = (-(2 * Geometry.dotProduct(d, center)) + Math.sqrt((2 * Geometry.dotProduct(d, center)) * (2 * Geometry.dotProduct(d, center))
				- 4 * (Geometry.dotProduct(d, d)) * (Geometry.dotProduct(center, center) - radio * radio)))
				/ (2 * (Geometry.dotProduct(d, d)));

		if ((t > (-(2 * Geometry.dotProduct(d, center))
				- Math.sqrt((2 * Geometry.dotProduct(d, center)) * (2 * Geometry.dotProduct(d, center))
						- 4 * (Geometry.dotProduct(d, d)) * (Geometry.dotProduct(center, center) - radio * radio)))
				/ (2 * (Geometry.dotProduct(d, d))))
				&& (-(2 * Geometry.dotProduct(d, center))
						- Math.sqrt((2 * Geometry.dotProduct(d, center)) * (2 * Geometry.dotProduct(d, center))
								- 4 * (Geometry.dotProduct(d, d)) * (Geometry.dotProduct(center, center) - radio * radio)))
						/ (2 * (Geometry.dotProduct(d, d))) > 0) {
			t = (-(2 * Geometry.dotProduct(d, center))
					- Math.sqrt((2 * Geometry.dotProduct(d, center)) * (2 * Geometry.dotProduct(d, center))
							- 4 * (Geometry.dotProduct(d, d)) * (Geometry.dotProduct(center, center) - radio * radio)))
					/ (2 * (Geometry.dotProduct(d, d)));
		}

	//	System.out.println((2 * d.dotProduct(d, center)) * (2 * d.dotProduct(d, center))
	//			- 4 * (d.dotProduct(d, d)) * (d.dotProduct(center, center)));
		if (t > 0) {
			return t;
		}
		return -1;
	}
	
}
