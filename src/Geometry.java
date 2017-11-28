
public class Geometry {
	private double x, y, z;

	public Geometry(double x, double y, double z) {
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public double getSize( Geometry g, Geometry d) {
		return (Math.sqrt(((g.getX()-d.getX())*(g.getX()-d.getX()))+
				((g.getY()-d.getY())*(g.getY()-d.getY())) +
				((g.getZ()-d.getZ())*(g.getZ()-d.getZ()))));
	}
	public double getSize() {
		return (Math.sqrt((this.getX()*this.getX())+
				(this.getY()*this.getY()) +
				(this.getZ()*this.getZ())));
	}
	public Point add( Point d ,Geometry O, Geometry P) {
		d.setX(O.getX() + P.getX());
		d.setY(O.getY() + P.getY());
		d.setZ(O.getZ() + P.getZ());
		return d;
	}
	public static Geometry addG(Geometry O, Geometry P) {
		return new Geometry(O.getX() + P.getX(), O.getY() + P.getY(), O.getZ() + P.getZ());
	}
	public static Direction addD(Geometry O, Geometry P) {
		return new Direction(O.getX() + P.getX(), O.getY() + P.getY(), O.getZ() + P.getZ());
	}
	public static Point addP(Geometry O, Geometry P) {
		return new Point(O.getX() + P.getX(), O.getY() + P.getY(), O.getZ() + P.getZ());
	}
	
	public static Geometry subG(Geometry O, Geometry P) {
		return new Geometry(O.getX() - P.getX(), O.getY() - P.getY(), O.getZ() - P.getZ());
	}
	

	public static Direction subD(Geometry O, Geometry P) {
		return new Direction(O.getX() - P.getX(),O.getY() - P.getY() , O.getZ() - P.getZ());
	}
	
	
	public static double dotProduct(Geometry O, Geometry P) {
		return (O.getX()*P.getX()+ O.getY()*P.getY()+O.getZ()*P.getZ());
	}

	public double[][] changeBase( Direction X, Direction Y, Direction Z, Point O) {
		Matrix4d change = new Matrix4d(X,Y,Z,O);
		return change.getMatrix() ;
	}
	
	public Point crossPoint4d(Point d, Point p, Matrix4d change) {
		d.setX(change.getMatrix()[0][0]*p.getX()+change.getMatrix()[0][1]*p.getY()+change.getMatrix()[0][2]*p.getZ()+change.getMatrix()[0][3]);
		d.setY(change.getMatrix()[1][0]*p.getX()+change.getMatrix()[1][1]*p.getY()+change.getMatrix()[1][2]*p.getZ()+change.getMatrix()[1][3]);
		d.setZ(change.getMatrix()[2][0]*p.getX()+change.getMatrix()[2][1]*p.getY()+change.getMatrix()[2][2]*p.getZ()+change.getMatrix()[2][3]);
		return d;
	}
	
	public double[][] dcrossMatrix4d(Point p,Matrix4d change) {
		double m[][]= new double[1][4];
		double matrixChange[][]= change.getMatrix();
		m[0][0]=(matrixChange[0][0]*p.getX()+matrixChange[0][1]*p.getY()+matrixChange[0][2]*p.getZ()+matrixChange[0][3]*1);
		m[0][1]=(matrixChange[1][0]*p.getX()+matrixChange[1][1]*p.getY()+matrixChange[1][2]*p.getZ()+matrixChange[1][3]*1);
		m[0][2]=(matrixChange[2][0]*p.getX()+matrixChange[2][1]*p.getY()+matrixChange[2][2]*p.getZ()+matrixChange[2][3]*1);
		m[0][3]=(matrixChange[3][0]*p.getX()+matrixChange[3][1]*p.getY()+matrixChange[3][2]*p.getZ()+matrixChange[3][3]*1);
		System.out.println("x: " +  m[0][0] +"y: " +  m[0][1] +"z: " + m[0][2]+"w: " + m[0][3]);
		return m;
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}
}