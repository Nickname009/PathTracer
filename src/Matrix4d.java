
public class Matrix4d {

	public double matrix[][];
	public Matrix4d(Direction X, Direction Y, Direction Z, Point O){
		matrix = new double[4][4];
		matrix[0][0]=X.getX();
		matrix[0][1]=Y.getX();
		matrix[0][2]=Z.getX();
		matrix[0][3]=O.getX();
		matrix[1][0]=X.getY();
		matrix[1][1]=Y.getY();
		matrix[1][2]=Z.getY();
		matrix[1][3]=O.getY();
		matrix[2][0]=X.getZ();
		matrix[2][1]=Y.getZ();
		matrix[2][2]=Z.getZ();
		matrix[2][3]=O.getZ();
		matrix[3][0]=0;
		matrix[3][1]=0;
		matrix[3][2]=0;
		matrix[3][3]=1;
	}
	public Matrix4d(double m[][]){
		matrix = m;
		for(int i=0;i<m.length;i++) {
			for(int j=0;j<m.length;j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}
	public double[][] getMatrix () {
		return matrix;
	}
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v01() {
//		return matrix[0][0];
//	}
//
//	public double v02() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
//
//	public double v00() {
//		return matrix[0][0];
//	}
	
	/*
public direction mul() {
		
		return m;
	}

	public Direction mul(Direction X, Direction Y, Direction Z, Point O) {
	
		return ;
	}
	*/
	
	
}
