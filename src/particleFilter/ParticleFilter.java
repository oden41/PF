package particleFilter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.stream.Stream;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import model.DynamicSystem;
import particle.Particle;

public class ParticleFilter {

	private final int N;
	private int kGeneration;
	private Particle[] particles;
	private final Random random;
	private final DynamicSystem system;

	private Vector2D xVector;
	private final Vector2D initEnsembleVec;

	public ParticleFilter(int N, Vector2D vec, DynamicSystem sysmem) {
		this.N = N;
		kGeneration = 0;
		particles = new Particle[N];
		random = new Random();
		xVector = new Vector2D(Math.PI / 24.0, 0);
		this.system = sysmem;
		initEnsembleVec = vec;
	}

	/**
	 * 初期化を行う
	 *
	 */
	public void init() {
		for (int i = 0; i < N; i++) {
			Vector2D vector = new Vector2D(initEnsembleVec.getX() + getSystemNoise(),
					initEnsembleVec.getY() + getSystemNoise());
			Particle particle = new Particle();
			particle.setXVector(vector);
			particles[i] = particle;
		}
	}

	/**
	 * 予測を行う
	 *
	 */
	public void predict() {
		Stream.of(particles).forEach(p -> {
			double noise = getSystemNoise();
			p.setXVector(system.stateEq(p.getXVector(), noise));
		});

		//実際の状態も更新
		xVector = system.stateEq(xVector, getSystemNoise());
	}

	/**
	 * フィルタリング(修正)を行う
	 *
	 */
	public void filtering() {
		//観測値y_{k+1}
		double y = system.observeEq(xVector, getObserveNoise());
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
		particles = newGeneration;
	}

	/**
	 * アンサンブルの重心を取得する
	 * @return
	 */
	public Vector2D getgVector() {
		double x = 0, y = 0;
		for (int i = 0; i < particles.length; i++) {
			x += particles[i].getXVector().getX();
			y += particles[i].getXVector().getY();
		}
		return new Vector2D(x / N, y / N);
	}

	/**
	 * 終了条件を判定する．今回は10000回固定
	 * @return
	 */
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

	/**
	 * システムノイズ
	 * @return
	 */
	private final double getSystemNoise() {
		return random.nextGaussian() * 0.2;
	}

	/**
	 * 観測ノイズ
	 * @return
	 */
	private final double getObserveNoise() {
		return random.nextGaussian() * 0.1;
	}

}
