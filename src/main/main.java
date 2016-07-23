package main;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import particleFilter.ParticleFilter;

public class main {

	public static void main(String[] args) {
		int n = 1000;
		Vector2D xVector = new Vector2D(Math.PI / 24.0, 0);
		ParticleFilter pFilter = new ParticleFilter(n, xVector);
		pFilter.init();

		while (!pFilter.isEnd()) {
			pFilter.predict();

			pFilter.filtering();
		}

	}

}
