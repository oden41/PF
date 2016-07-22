package particle;

import matrix2013.TCMatrix;

public class Particle {
	private TCMatrix xVector;
	private double likelihood;

	public Particle() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public TCMatrix getXVector() {
		return xVector;
	}

	public void setXVector(TCMatrix xVector) {
		this.xVector = xVector;
	}

	public double getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(double likelihood) {
		this.likelihood = likelihood;
	}

}
