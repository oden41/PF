package particleFilter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import particle.Particle;

public class ParticleFilter {

	private final int N;
	private int kGeneration;
	private final Particle[] particles;
	private final Random random;
	private final double deltaT;

	private Vector2D xVector;

	public ParticleFilter(int N, Vector2D vec) {
		this.N = N;
		kGeneration = 0;
		particles = new Particle[N];
		random = new Random();
		deltaT = 0.001;
		xVector = vec;
	}

	public void init() {
		for (int i = 0; i < N; i++) {
			Vector2D vector = new Vector2D(xVector.getX() + getSystemNoise(), xVector.getY() + getSystemNoise());
			Particle particle = new Particle();
			particle.setXVector(vector);
			particles[i] = particle;
		}
	}

	public void predict() {
		Stream.of(particles).forEach(p -> {
			double noise = getSystemNoise();
			p.setXVector(vibSystem(p.getXVector(), noise));
		});

		//実際の状態も更新
		xVector = vibSystem(xVector, getSystemNoise());
	}

	public void filtering() {
		//観測値y_{k+1}
		double y = observeSystem(xVector, getObserveNoise());
		//尤度計算
		Stream.of(particles).forEach(p -> p.setLikelihood((1.0 / (Math.sqrt(4 * Math.PI * Math.PI * 0.1 * 0.1))
				* Math.exp(-(y - p.getXVector().getY()) * (y - p.getXVector().getY()) / (2 * 0.1 * 0.1)))));

		//選択
		double sum = Stream.of(particles).mapToDouble(p -> p.getLikelihood()).sum();
		Particle[] newGeneration = new Particle[particles.length];
		for (int i = 0; i < particles.length; i++) {
			double plot = random.nextDouble();
			int index = 0;
			double a = 0.0;
			while (index < particles.length) {
				a += particles[index].getLikelihood() / sum;
				if (a > plot)
					break;
				index++;
			}
			newGeneration[i] = particles[index].clone();
		}
		kGeneration++;
	}

	public Vector2D getgVector() {
		double x = 0, y = 0;
		for (int i = 0; i < particles.length; i++) {
			x += particles[i].getXVector().getX();
			y += particles[i].getXVector().getY();
		}
		return new Vector2D(x / N, y / N);
	}

	public boolean isEnd() {
		return kGeneration >= 10000;
	}

	public void writeFiles(String elem1Path, String elem2Path) throws IOException {
		PrintWriter elem1 = new PrintWriter(new FileWriter(elem1Path, true));
		PrintWriter elem2 = new PrintWriter(new FileWriter(elem2Path, true));
		Vector2D vec = getgVector();
		elem1.println(kGeneration + "," + vec.getX() + "," + xVector.getX());
		elem2.println(kGeneration + "," + vec.getY() + "," + xVector.getY());

		elem1.close();
		elem2.close();
	}

	private final double getSystemNoise() {
		return random.nextGaussian() * 0.2 * 0.2;
	}

	private final double getObserveNoise() {
		return random.nextGaussian() * 0.1 * 0.1;
	}

	private final Vector2D vibSystem(Vector2D vec, double noise) {
		double x, y;
		x = vec.getX() + vec.getY() * deltaT;
		y = vec.getY() + ((-9.8 / 2.0) * Math.sin(vec.getX()) + (-1 / (1.0 * 2.0)) * noise) * deltaT;
		return new Vector2D(x, y);
	}

	private final double observeSystem(Vector2D vec, double noise) {
		return vec.getY() + noise;
	}
}
