import java.awt.Color;

import javax.swing.text.Position;

public class Plane extends Figure{
	double d;
	private Geometry g= new Geometry(0, 0, 0);
	public Plane(Point center, Direction normal, Color color, double d, Material m){
		super(center, color, m, normal);
		this.normal = normal;
		this.d = d;
		//this.d = Math.sqrt(center.getX()*normal.getX()+center.getY()*normal.getY()+center.getZ()*normal.getZ());
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
		t=-(g.dotProduct(O, normal)+d) / (g.dotProduct(D, normal));
		
		if (t>0) {
			return t;
		}
		return -1;
	}
}


