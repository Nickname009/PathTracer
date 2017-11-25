import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class PathTracer {

	public static void main(String[] args) throws IOException {

		// Settings photo
		int y = 720;
		int x = 980;
		int antialiasing = 8;
		Random r = new Random();
		int porcentage = 0;

		// Variables Path
		BufferedImage bI = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Geometry geo = new Direction(0, 0, 1);
		File h = new File("triangle2.bmp");

		// Camera
		Point center = new Point(0, 0, 0);
		Point orientation = new Point(0, 0, 22);
		Camera camera = new Camera(center, orientation);

		// RAY
		//punto inicial imagen 
		Point p = new Point(-camera.getl().getX(), -camera.getu().getY(), camera.getf().getZ());
		Point pA = new Point(0, -camera.getu().getY(), camera.getf().getZ());

		//new rayo camara
		Direction d = new Direction(0, 0, 1);
		Direction directLight = new Direction(0, 0, 1);
		Ray rayPath = new Ray(center, p, d);
		Ray rayPath2 = new Ray(center, p, d);
		//IntersectionRay iR = new IntersectionRay();

		// Resolucion tamaño pixel
		double sx = camera.getl().getX() * 2 / x;
		double sy = camera.getu().getY() * 2 / y;

		// CODE 360º
		/*
		 * Point p = new Point(0, -camera.getu().getY(), -camera.getf().getZ());
		 * 
		 * double sx = (camera.getl().getX()) / (x / 4); double sy =
		 * camera.getu().getY() * 2 / y; double sz = (camera.getf().getZ() * 2) / (x /
		 * 2);
		 */
		
		RussianRoulette rr = new RussianRoulette();
		// ANTIALIASING variable
		int rA = 0;
		int gA = 0;
		int bA = 0;

		// List of figures
		Light l = new Light(0, 0, 0, 1000);
		Material m = new Material(0.5, 0.5, 0,0);
		List<Figure> figures = new ArrayList<Figure>();
		Figure defaultF = new Figure(new Point(0, 0, 0), Color.BLACK, m, new Direction(0, 0, 0));
		Color finalColor;
		figures.add(new Plane(new Point(0, 500, 500), new Direction(0, -1, 0), Color.BLUE, 200, l)); // arriba
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 1, 0), Color.GREEN, 200, m));
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 0, -1), Color.DARK_GRAY, 500, m)); // fondo
		// figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 0, 1),
		// Color.RED, 94, m));
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(-1, 0, 0), Color.MAGENTA, 200, m)); // izq
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(1, 0, 0), Color.ORANGE, 200, m));
		// figures.add(new Triangle(new Point(-0.2, 1, 14),new Point(1, 0.2, 10),new
		// Point(0, 0, 4), new Direction(1, 0, 4), Color.GREEN, 10, m));
		// figures.add(new Triangle(new Point(0, 0, 20), new Point(0, 1, 20), new
		// Point(1, 1, 20), new Direction(0, 0, 1),
		// Color.RED, 10, m));
		//figures.add(new Sphere(new Point(5, 5, -15), 1, Color.WHITE, m, new Direction(0, 0, 0)));
		// figures.add(new Triangle(new Point(0, 2, 20), new Point(-2, 9, 20), new
		// Point(0, 9, -10), d, Color.WHITE, 90, l));
		// figures.add(new Triangle(new Point(0, 2, 20), new Point(2, 9, 20), new
		// Point(0, 9, 10), d, Color.WHITE, 90,l));
		// readFile1(figures, "ket.ply");

		Figure f = null;
		double s = 99999999;
		double t = -1;

		pA.setZ(camera.getf().getZ());
		// Bucle Pixels Image
		for (int py = 0; py < y; py++) {
			for (int px = 0; px < x; px++) {
				finalColor = render(rayPath, rA, gA, bA, sx, sy, antialiasing, pA, f, defaultF, r, geo,
						camera, t, s, figures, rr);
				bI.setRGB(px, py, finalColor.getRGB());

				p.setX(p.getX() + sx);

				// CODE 360º
				/*
				 * if (px < ((x / 4))) { p.setX(p.getX() - sx); p.setZ(p.getZ() + sz); } else if
				 * (px < ((x / 4) * 2)) { p.setX(p.getX() + sx); p.setZ(p.getZ() + sz); } else
				 * if (px < ((x / 4)) * 3) { p.setX(p.getX() + sx); p.setZ(p.getZ() - sz); }
				 * else if (px >= ((x / 4)) * 3) { p.setX(p.getX() - sx); p.setZ(p.getZ() - sz);
				 * }
				 */
			}
			porcentage = py;
			if (porcentage % 10 == 0)
				System.out.println(porcentage);
			p.setY(p.getY() + sy);
			p.setX(-camera.getl().getX());
		}
		ImageIO.write(bI, "bmp", h);
	}

	public static void readFile1(List<Figure> figures, String f) throws FileNotFoundException {
		File file = new File(f);
		Scanner scan = new Scanner(file);
		// setting file
		while (scan.hasNextDouble()) {
			Point p1 = new Point(scan.nextDouble() + 1, scan.nextDouble() + 1, scan.nextDouble() + 5);
			Point p2 = new Point(scan.nextDouble() + 1, scan.nextDouble() + 1, scan.nextDouble() + 5);
			Point p3 = new Point(scan.nextDouble() + 1, scan.nextDouble() + 1, scan.nextDouble() + 5);
			figures.add(new Triangle(p1, p2, p3, new Direction(0, 0, 0), Color.RED, 0, null));
		}
	}

	public static Color render(Ray rayPath, int rA, int gA, int bA, double sx, double sy, int antialiasing,
			Point pA, Figure f, Figure defaultF, Random r, Geometry geo, Camera camera, double t, double s,
			List<Figure> figures, RussianRoulette rr) {
		rA = 0;
		gA = 0;
		bA = 0;
		Direction directLight = new Direction(0, 0, 0);
		// Rays - pixels
		for (int aS = 0; aS < antialiasing; aS++) {
			pA.setX((sx + ((sx + sx) - sx) * r.nextDouble()) + rayPath.getP1().getX());
			pA.setY((sy + ((sy + sy) - sy) * r.nextDouble()) + rayPath.getP1().getY());
			s = 9999999;
			f = defaultF;
			t = -1;
			rayPath.setD(Geometry.subD(pA, camera.getCenter()));
		//	System.out.println(rayPath.getD().getZ());
			// Intersect figures
			for (Figure sf : figures) {
				t = sf.intersect(camera.getCenter(), rayPath.getD());
				if (s > t && t > 0) {
					s = t;
					f = sf;
					//direct light
					rayPath.setPIntersect(Geometry.addP(rayPath.getP0(),rayPath.getD().scale(t)));
					// Light of direct Light
				}
			}
			Color lD = luzDirecta(rayPath.pIntersect, new Point(0, 0, -300), figures, f);
			// Recursivity ray
/*
			double rrr = rr.calculateRussian(f.getKD(), f.getKS());
			if (f.getM().getW() == 0) {
				if (rrr == 1.0) { // difuse ray
					// Montain Carlo

					// new ray

				} else if (rrr == 2.0) { // especular ray
					// New ray
				}
				// renderAntialiasing(rayPath, rA, gA, bA, sx, sy, antialiasing, pA, f,
				// defaultF, r, geo, camera, t, aS,
				// figures, rr);
			}*/
			// end ray

			/* d^2 luz directa */
			//light2 = light2 + ((directLight.module(directLight) * directLight.module(directLight)) / 2);

			rA = rA + lD.getRed();
			bA = bA + lD.getBlue();
			gA = gA + lD.getGreen();
		}
		rA = rA / antialiasing;
		bA = bA / antialiasing;
		gA = gA / antialiasing;

		/*****************************************************************************************/
		// ((colorLuz * intensidad)/d^2)*colorObjeto*coef.Difusa ****************** LUZ
		// DIRECTA
		/*****************************************************************************************/
		//rA = (int) (((255 * 255) / light2) * rA);
		//bA = (int) (((255 * 255) / light2) * bA);
		//gA = (int) (((255 * 255) / light2) * gA);
		if (rA > 255)
			rA = 255;
		if (bA > 255)
			bA = 255;
		if (gA > 255)
			gA = 255;
		return (new Color(rA, gA, bA));
	}
	public static Color luzDirecta(Point intersect, Point light, List<Figure> f, Figure fi) {
		Direction cx = new Direction(0, 0, 0);
		Direction p = new Direction(0, 0, 0);
		
		cx= Geometry.subD(light, intersect);
		p=cx;
		//double w= cx.module(cx);
		//p.setX(p.getX()/w);
		//p.setY(p.getY()/w);
		//p.setZ(p.getZ()/w);

		
		//System.out.println(intersect.getSize()+ " " + light.getSize());
		Ray r = new Ray(intersect, light, cx);
		if(block(f,r)) {
			return new Color(0,0,0);
		}
		else {
			int rr=/*fi.color.getRed()*/(int)colour(fi.color.getRed(),light,new Light(0.2,0.5,0,50),cx.getSize(),fi,cx,intersect);
			int gg=/*fi.color.getGreen()*/(int)colour(fi.color.getGreen(),light,new Light(0.2,0.5,0,50),cx.getSize(),fi,cx,intersect);
			int bb=/*fi.color.getBlue()*/(int)colour(fi.color.getBlue(),light,new Light(0.2,0.5,0,50),cx.getSize(),fi,cx,intersect);
			if (rr > 255)
				rr = 255;
			if (bb > 255)
				bb = 255;
			if (gg > 255)
				gg = 255;
			Color c= new Color(rr,gg,bb);
			return c;
		}
	}
	
	public static double colour(int c, Point light, Material luz, double dcx, Figure fi, Direction cx,Point intersect) {
		double a;

		double w;
		w= cx.module(cx);
	/*	cx.doModule(cx);
		cx.setX(-cx.getX());
		cx.setY(-cx.getY());
		cx.setZ(-cx.getZ());
*/		Plane p = new Plane(new Point(0, 0, -100), new Direction(0, 1, 0), Color.BLUE, 500, luz);
		dcx = Geometry.subD(p.getCenter(), intersect).getSize();
		a=(255*luz.getW()/(Math.pow(dcx, 2)))*(c/Math.PI)*
		Math.abs((cx.dotProduct(fi.getNormal(intersect), cx)))
		*Math.abs(cx.dotProduct(Geometry.subD(intersect, p.getCenter()).doModule(), p.getNormal()));
		//a=(luz.getW()*255/Math.abs((Math.pow(d, 2))))*(c*Math.abs(di.dotProduct(fi.getNormal(intersect), di)/w));
		
		//else a=(luz.getW()/(d*d))*(0.5/Math.PI)*Math.abs(Geometry.dotProduct(fi.getNormal(intersect), di));

	//	System.out.println((Math.abs((di.dotProduct(fi.getNormal(intersect), di)/w))));
//		System.out.println("afgsh"+(0.5/Math.PI));
//		System.out.println("feninrago"+Math.abs(di.dotProduct(fi.getNormal(intersect), di)));
		return a;
	}
	
	public static boolean block (List<Figure> f, Ray r) {
		for (Figure sf : f) {
			double t = sf.intersect(r.getP0(), r.getD());
			if (r.getD().getSize(r.getD().scale(t), r.getP0()) < r.getD().getSize() && t>0) {
				return true;
			}
		}
		return false;
		
	}
}