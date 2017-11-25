import java.awt.Color;

public class Sphere extends Figure {

	private double radio;

	public Sphere(Point center, double radio, Color color, Material m) {
		super(center, color, m);
		this.radio = radio;
	}

	public double intersect(Point O, Direction d) {
		// Implementar interseccion esfera
		/*
		 * oc= d.dotProduct(center, O); b = 2*d.dotProduct(d, center);
		 * c=d.dotProduct(center, center)-radio*radio; b*b-4*a*c a = d.dotProduct(d, d);
		 * t=((-O.dotProduct(O, center))-radio)/d.scale(2).getSize(d, O);
		 * System.out.println(t);
		 */
		t = (-(2 * d.dotProduct(d, center)) + Math.sqrt((2 * d.dotProduct(d, center)) * (2 * d.dotProduct(d, center))
				- 4 * (d.dotProduct(d, d)) * (d.dotProduct(center, center) - radio * radio)))
				/ (2 * (d.dotProduct(d, d)));

		if ((t > (-(2 * d.dotProduct(d, center))
				- Math.sqrt((2 * d.dotProduct(d, center)) * (2 * d.dotProduct(d, center))
						- 4 * (d.dotProduct(d, d)) * (d.dotProduct(center, center) - radio * radio)))
				/ (2 * (d.dotProduct(d, d))))
				&& (-(2 * d.dotProduct(d, center))
						- Math.sqrt((2 * d.dotProduct(d, center)) * (2 * d.dotProduct(d, center))
								- 4 * (d.dotProduct(d, d)) * (d.dotProduct(center, center) - radio * radio)))
						/ (2 * (d.dotProduct(d, d))) > 0) {
			t = (-(2 * d.dotProduct(d, center))
					- Math.sqrt((2 * d.dotProduct(d, center)) * (2 * d.dotProduct(d, center))
							- 4 * (d.dotProduct(d, d)) * (d.dotProduct(center, center) - radio * radio)))
					/ (2 * (d.dotProduct(d, d)));
		}
		if (t > 0) {
			return t;
		}
		return -1;
	}
}
