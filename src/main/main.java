package main;

import matrix2013.TCMatrix;
import particleFilter.ParticleFilter;

public class main {

	public static void main(String[] args) {
		int n = 1000;
		TCMatrix xMatrix = new TCMatrix(2, 1);
		xMatrix.setValue(0, 0, Math.PI / 24.0);
		xMatrix.setValue(1, 0, 0);
		ParticleFilter pFilter = new ParticleFilter(n, xMatrix);
		pFilter.init();

		while (!pFilter.isEnd()) {

		}

	}

}
