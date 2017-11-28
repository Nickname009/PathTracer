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
		int antialiasing = 10;
		Random r = new Random();
		int porcentage = 0;

		// Variables Path
		BufferedImage bI = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Geometry geo = new Direction(0, 0, 1);
		File h = new File("triangle3.bmp");

		// Camera
		Point center = new Point(0, 0, 0);
		Point orientation = new Point(0, 0, 25);
		Camera camera = new Camera(center, orientation);

		// RAY
		//punto inicial imagen 
		Point p = new Point(-camera.getl().getX(), -camera.getu().getY(), camera.getf().getZ());
		Point pA = new Point(0, -camera.getu().getY(), camera.getf().getZ());

		//new rayo camara
		Direction d = new Direction(0, 0, 1);
		Ray rayPath = new Ray(center, p, d.normalize());
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
		figures.add(new Plane(new Point(0, 500, -500), new Direction(0, -1, 0), Color.BLUE, 40, l)); // arriba
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 1, 0), Color.GREEN, 40, m));
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 0, -1), Color.PINK, 100, m)); // fondo
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(-1, 0, 0), Color.MAGENTA, 40, m)); // izq
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(1, 0, 0), Color.ORANGE, 40, m));
		// figures.add(new Triangle(new Point(0, 0, 20), new Point(0, 1, 20), new Point(1, 1, 20), new Direction(0, 0, 1),Color.RED, 10, m));
		figures.add(new Sphere(new Point(0, -40, -93), 15, Color.RED, m, new Direction(0, 0, 0)));
		cuadrado light = new cuadrado(new Point(0, -19, -30), new Direction(0, 1, 0), Color.WHITE, 19, new Light(0,0,0,0),-10,-10,10,-20);
		//figures.add(light);
		//figures.add(new Triangle(new Point(-5, -10, -20), new Point(5, -19, -20), new Point(5, -19, -10), new Direction(0, -1, 0),Color.WHITE, 10, m));
		//figures.add(new Triangle(new Point(-5, -10, -10), new Point(-5, -19, -20), new Point(5, -19, -20), new Direction(0, -1, 0),Color.WHITE, 10, m));
		//figures.add(new Triangle(new Point(-20, -20, 0), new Point(-20, 50, 0), new Point(-20, -20, -40), new Direction(0, 0, 1),Color.WHITE, 10, m));
		
		// readFile1(figures, "ket.ply");

		Figure f = null;
		double s = 99999999;
		double t = -1;

		pA.setZ(camera.getf().getZ());
		// Bucle Pixels Image
		for (int py = 0; py < y; py++) {
			for (int px = 0; px < x; px++) {
				finalColor = render(rayPath, rA, gA, bA, sx, sy, antialiasing, pA, f, defaultF, r, geo,
						camera, t, s, figures, rr,light);
				bI.setRGB(px, py, finalColor.getRGB());
				p.setX(p.getX() + sx);
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
			List<Figure> figures, RussianRoulette rr,cuadrado light) {
		rA = 0;
		gA = 0;
		bA = 0;
		// Rays - pixels
		for (int aS = 0; aS < antialiasing; aS++) {
			pA.setX((sx + ((sx + sx) - sx) * r.nextDouble()) + rayPath.getP1().getX());
			pA.setY((sy + ((sy + sy) - sy) * r.nextDouble()) + rayPath.getP1().getY());
			s = 9999999;
			f = defaultF;
			t = -1;
			rayPath.setD(Geometry.subD(pA, camera.getCenter()).normalize());
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
			Color lD = luzDirecta(rayPath.pIntersect, light, figures, f);
			// Recursivity ray

			// end ray

			rA = rA + lD.getRed();
			bA = bA + lD.getBlue();
			gA = gA + lD.getGreen();
		}
		rA = rA / antialiasing;
		bA = bA / antialiasing;
		gA = gA / antialiasing;
		if (rA > 255)
			rA = 255;
		if (bA > 255)
			bA = 255;
		if (gA > 255)
			gA = 255;
		return (new Color(rA, gA, bA));
	}
	public static Color luzDirecta(Point intersect, cuadrado light, List<Figure> f, Figure fi) {
		
		Direction cx ;
		cx= Geometry.subD(light.getCenter(), intersect).normalize();
		Ray r = new Ray(intersect, light.getRNDP(), cx);
		if(block(f,r)) {
			return Color.BLACK;
		}
		else {
			Point pl = new Point (0,40,50);//light.getRNDP();
			int rr=(int)colour(fi.color.getRed(),pl,light,fi,cx,intersect);
			int gg=(int)colour(fi.color.getGreen(),pl,light,fi,cx,intersect);
			int bb=(int)colour(fi.color.getBlue(),pl,light,fi,cx,intersect);
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
	
	public static double colour(int c, Point light, Figure luz, Figure fi, Direction cx,Point intersect) {
		double a;
		double dcx = Geometry.subD(light, intersect).module();
		a=(255*60/(dcx*dcx))*(c/Math.PI)*
		Math.abs((Geometry.dotProduct(fi.getNormal(intersect).normalize(), cx)))
		*Math.abs(Geometry.dotProduct(Geometry.subD(light, intersect).normalize(),luz.getNormal()));
		return a;
	}
	
	public static boolean block (List<Figure> figures, Ray r) {
		for (Figure sf : figures) {
			double t = sf.intersect(r.getP0(), r.getD().normalize());
			if ((r.getD().scale(t).module() < r.getD().module()) && t>0) {
				return true;
			}
		}
		return false;
	}
}