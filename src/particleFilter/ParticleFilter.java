package particleFilter;

import java.util.Random;
import java.util.stream.Stream;

import matrix2013.TCMatrix;
import particle.Particle;

public class ParticleFilter {

	private final int N;
	private int kGeneration;
	private final Particle[] particles;
	private final Random random;
	private final double deltaT;

	private final TCMatrix x0Matrix;

	public ParticleFilter(int N, TCMatrix xMatrix) {
		this.N = N;
		kGeneration = 0;
		particles = new Particle[N];
		random = new Random();
		deltaT = 0.001;

		x0Matrix = xMatrix;
	}

	public void init() {
		for (int i = 0; i < N; i++) {
			TCMatrix xMatrix = new TCMatrix(2, 1);
			xMatrix.setValue(0, 0, x0Matrix.getValue(0, 0) + getSystemNoise());
			xMatrix.setValue(1, 0, x0Matrix.getValue(1, 0) + getSystemNoise());

			Particle particle = new Particle();
			particle.setXVector(xMatrix);
			particles[i] = particle;
		}
	}

	public void predict() {

	}

	public void filtering() {

	}

	public TCMatrix getgVector() {
		TCMatrix matrix = new TCMatrix(2, 1);
		Stream.of(particles).forEach(p -> {
			matrix.add(p.getXVector());
		});
		matrix.div(N);
		return matrix;
	}

	public boolean isEnd() {
		return kGeneration >= 10000;
	}

	private final double getSystemNoise() {
		return random.nextGaussian() * 0.2 * 0.2;
	}

	private final double getObserveNoise() {
		return random.nextGaussian() * 0.1 * 0.1;
	}
}
