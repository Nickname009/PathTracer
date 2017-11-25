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
		int y = 1080;
		int x = 1980;
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
		Material m = new Material(0.5, 0, 0);
		List<Figure> figures = new ArrayList<Figure>();
		Figure defaultF = new Figure(new Point(0, 0, 0), Color.BLACK, m);
		Color finalColor;
		figures.add(new Plane(new Point(100, 0, 0), new Direction(0, -1, 0), Color.BLUE, 150, l)); // arriba
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 1, 0), Color.GREEN, 150, m));
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 0, -1), Color.DARK_GRAY, 500, m)); // fondo
		// figures.add(new Plane(new Point(-100, 0, 0), new Direction(0, 0, 1),
		// Color.RED, 94, m));
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(-1, 0, 0), Color.MAGENTA, 150, m)); // izq
		figures.add(new Plane(new Point(-100, 0, 0), new Direction(1, 0, 0), Color.ORANGE, 150, m));
		// figures.add(new Triangle(new Point(-0.2, 1, 14),new Point(1, 0.2, 10),new
		// Point(0, 0, 4), new Direction(1, 0, 4), Color.GREEN, 10, m));
		// figures.add(new Triangle(new Point(0, 0, 20), new Point(0, 1, 20), new
		// Point(1, 1, 20), new Direction(0, 0, 1),
		// Color.RED, 10, m));
		figures.add(new Sphere(new Point(2, 2, -15), 1, Color.WHITE, m));
		// figures.add(new Triangle(new Point(0, 2, 20), new Point(-2, 9, 20), new
		// Point(0, 9, -10), d, Color.WHITE, 90, l));
		// figures.add(new Triangle(new Point(0, 2, 20), new Point(2, 9, 20), new
		// Point(0, 9, 10), d, Color.WHITE, 90,l));
		// readFile1(figures, "ket.ply");

		Figure f = null;
		double s = 99999999;
		double t = -1;
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
		Ray lightRay = new Ray(pA, pA, directLight);
		double light2 = 0;
		// Rays - pixels
		for (int aS = 0; aS < antialiasing; aS++) {
			pA.setX((sx + ((sx + sx) - sx) * r.nextDouble()) + rayPath.getP1().getX());
			pA.setY((sy + ((sy + sy) - sy) * r.nextDouble()) + rayPath.getP1().getY());
			s = 9999999;
			f = defaultF;
			t = -1;
			rayPath.setD(Geometry.sub(rayPath.getD(), pA, camera.getCenter()));

			// Intersect figures
			for (Figure sf : figures) {
				t = sf.intersect(camera.getCenter(), rayPath.getD());
				if (s > t && t > 0) {
					s = t;
					f = sf;
					//direct light
					rayPath.setPIntersect((rayPath.getPintersect().add(rayPath.getPintersect(), rayPath.getP0(),
							rayPath.getD().scale(t))));
					
					// Light of direct Light
					lightRay.setD(geo.sub(directLight, new Point(0, 0, -400), rayPath.pIntersect));
					Ray rp = new Ray(rayPath.pIntersect, new Point(0, 0, 0), directLight);
					for (Figure sf2 : figures) {
						double t2 = sf2.intersect(rp.getP0(), rp.getD());
					//	System.out.println(rp.getD().getSize(pA, new Point(0, 10, -400))+ "   " +t2);
						if (rp.getD().getSize(rayPath.getPintersect(), new Point(0, 10, -400)) <
								rp.getD().getSize(rp.d.scale(t2), rp.getP0())  && t2 > 0) {
							directLight = new Direction(99999999, 999999999, 99999999);
						}
					}
					// System.out.println(lightRay.getD().module(lightRay.getD()));
				}
			}

			// Recursivity ray

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
			}
			// end ray

			/* d^2 luz directa */
			light2 = light2 + ((directLight.module(directLight) * directLight.module(directLight)) / 2);

			rA = rA + f.getColor().getRed();
			bA = bA + f.getColor().getBlue();
			gA = gA + f.getColor().getGreen();
		}
		rA = rA / antialiasing;
		bA = bA / antialiasing;
		gA = gA / antialiasing;

		/*****************************************************************************************/
		// ((colorLuz * intensidad)/d^2)*colorObjeto*coef.Difusa ****************** LUZ
		// DIRECTA
		/*****************************************************************************************/
		rA = (int) (((255 * 255) / light2) * rA);
		bA = (int) (((255 * 255) / light2) * bA);
		gA = (int) (((255 * 255) / light2) * gA);
		/*if (rA > 255)
			rA = 255;
		if (bA > 255)
			bA = 255;
		if (gA > 255)
			gA = 255;
		*/return (new Color(rA, gA, bA));
	}
}