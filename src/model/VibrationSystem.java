package model;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class VibrationSystem extends DynamicSystem {
	public VibrationSystem(double delta) {
		super.deltaT = delta;
	}

	/**
	 * 振り子の運動方程式
	 * @see model.DynamicSystem#stateEq(org.apache.commons.math3.geometry.euclidean.twod.Vector2D, double)
	 */
	public final Vector2D stateEq(Vector2D vec, double noise) {
		double x, y;
		x = vec.getX() + vec.getY() * deltaT;
		y = vec.getY() + ((-9.8 / 2.0) * Math.sin(vec.getX()) + (-1 / (1.0 * 2.0)) * noise) * deltaT;
		return new Vector2D(x, y);
	}

	/**
	 * y_k = [0 1]x_k + w_k
	 * @param vec
	 * @param noise
	 * @return
	 */
	public final double observeEq(Vector2D vec, double noise) {
		return vec.getY() + noise;
	}
}
