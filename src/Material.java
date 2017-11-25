
public class Material {
	double kd;
	double ks;// expecular o no 0 / 1
	double ir;
	double w = 0;

	public Material(double kd, double ks, double ir, double w) {
		this.kd = kd;
		this.ks = ks;
		this.ir = ir;
		this.w = w;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getKd() {
		return kd;
	}

	public void setKd(double kd) {
		this.kd = kd;
	}

	public double getKs() {
		return ks;
	}

	public void setKs(double ks) {
		this.ks = ks;
	}

	public double getIr() {
		return ir;
	}

	public void setIr(double ir) {
		this.ir = ir;
	}

	// public void (){}

}
