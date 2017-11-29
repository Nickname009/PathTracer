import java.awt.Color;

import javax.swing.text.Position;

public class Plane extends Figure{
	double d;

	public Plane(Point center, Direction normal, Color color, double d, Material m,String name){
		super(center, color, m, normal);
		this.normal = normal;
		this.d = d;
		super.name = name;
	//	this.d = Math.sqrt(center.getX()*normal.getX()+center.getY()*normal.getY()+center.getZ()*normal.getZ());
	/*	this.d = (Math.sqrt(((center.getX()-normal.getX())*(center.getX()-normal.getX()))+
				((center.getY()-normal.getY())*(center.getY()-normal.getY())) +
				((center.getZ()-normal.getZ())*(center.getZ()-normal.getZ()))));
		*///System.out.println(d);
	}
	public Direction getNormal() {
		return normal;
	}
	
	public void setNormal(Direction normal) {
		this.normal = normal;
	}
	
	public double intersect( Point O,Direction D) {
		t=-(Geometry.dotProduct(O, normal)+d) / (Geometry.dotProduct(D, normal));
		if (t>0) {
			return t;
		}
		else return -1;
	}
	

	/*public void getRNDP() {
		Point p	= new Point((-30),
				(-30 + ((60) - 30) * r.nextDouble()) + (-30)),

				0+((0 + ((40) - 0) * r.nextDouble()) + 40));
	}*/
}


