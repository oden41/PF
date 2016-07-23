package main;

import java.io.IOException;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import model.DynamicSystem;
import model.VibrationSystem;
import particleFilter.ParticleFilter;

public class main {

	public static void main(String[] args) {
		int n = 1000;
		double delta = 0.001;
		//Vector2D xVector = new Vector2D(-Math.PI / 12.0, -Math.PI / 12.0);
		Vector2D xVector = new Vector2D(Math.PI / 24.0, 0);
		DynamicSystem system = new VibrationSystem(delta);
		ParticleFilter pFilter = new ParticleFilter(n, xVector, system);
		String string = "N1000";
		String elem1Path = "element1_" + string + ".csv";
		String elem2Path = "element2_" + string + ".csv";
		pFilter.init();
		try {
			pFilter.writeFiles(elem1Path, elem2Path);
		} catch (IOException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		while (!pFilter.isEnd()) {
			//予測
			pFilter.predict();
			//フィルタリング
			pFilter.filtering();

			try {
				pFilter.writeFiles(elem1Path, elem2Path);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}

	}

}
