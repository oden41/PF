package main;

import java.io.IOException;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import particleFilter.ParticleFilter;

public class main {

	public static void main(String[] args) {
		int n = 1000;
		//Vector2D xVector = new Vector2D(-Math.PI / 12.0, -Math.PI / 12.0);
		Vector2D xVector = new Vector2D(Math.PI / 24.0, 0);
		ParticleFilter pFilter = new ParticleFilter(n, xVector);
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
			pFilter.predict();

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
