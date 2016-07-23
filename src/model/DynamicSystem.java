package model;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public abstract class DynamicSystem {
	protected double deltaT;

	/**
	 * 状態方程式
	 * @param vec
	 * @param noise
	 * @return
	 */
	public abstract Vector2D stateEq(Vector2D vec, double noise);

	/**
	 * 観測方程式
	 * @param vec
	 * @param noise
	 * @return
	 */
	public abstract double observeEq(Vector2D vec, double noise);

	public final double getDeltaT() {
		return deltaT;
	}
}
