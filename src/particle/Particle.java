package particle;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Particle {
	private Vector2D xVector;
	private double likelihood;

	public Particle() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public Particle clone() {
		Particle particle = new Particle();
		particle.setXVector(xVector);
		particle.setLikelihood(likelihood);
		return particle;
	}

	public Vector2D getXVector() {
		return xVector;
	}

	public void setXVector(Vector2D vector) {
		this.xVector = vector;
	}

	public double getLikelihood() {
		return likelihood;
	}

	public void setLikelihood(double likelihood) {
		this.likelihood = likelihood;
	}

}
