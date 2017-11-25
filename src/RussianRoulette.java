import java.util.Random;

public class RussianRoulette {
	Random rnd;
	double i;
	public RussianRoulette() {
		rnd = new Random();
	}
	public double calculateRussian(double ks, double kd) {
		
		i = rnd.nextDouble();
		//difuso
		if(i<kd)
			return 1.0;
		//especular
		else if(i<kd+ks)
			return 2.0;
		//acabar rayo
		else 
			return 0.0;
	}
}
