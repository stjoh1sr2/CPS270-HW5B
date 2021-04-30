package edu.cmich.cps270;

import java.util.Random;

/**
 * 
 */
public class ForestFireSimulation {

	public static final char TREE = '8';
	public static final char SPARK = '.';
	public static final char FIRE = '*';
	public static final char ASH = '^';

	public static void main(String[] args) {

		ForestFireSimulation ffSimulation = new ForestFireSimulation(15, .45, .01, .4);

		for (int i = 0; i < 100; i++) {
			ffSimulation.nextStep();
			ffSimulation.printForest();
			System.out.printf("Forest Density: %.2f\n", ffSimulation.getForestDensity());
			System.out.printf("Fuel: %d\n", ffSimulation.getFuelLevel());
			System.out.printf("Fire Size: %.2f\n", ffSimulation.getSizeOfFire());
			System.out.println("--------------------------------\n");
		}

	}

	char[][] forest;
	int[][] fuel;
	double lightningStrikeProb;
	double spreadProb;
	Random r;

	public ForestFireSimulation(int N, double forestDensity, double lightningStrikeProb, double spreadProb) {
		forest = new char[N][N];
		fuel = new int[N][N];
		this.lightningStrikeProb = lightningStrikeProb;
		this.spreadProb = spreadProb;
		r = new Random();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (r.nextDouble() < forestDensity) {
					forest[i][j] = TREE;
					fuel[i][j] = r.nextInt(5) + 1;
				} else {
					forest[i][j] = ' ';
					fuel[i][j] = 0;
				}

			}
		}

	}

	public void printForest() {
		System.out.print(" ");
		for (int i = 0; i < forest.length; i++) {
			System.out.print("--");
		}
		System.out.println();
		for (int i = 0; i < forest.length; i++) {
			System.out.print("|");
			for (int j = 0; j < forest[i].length; j++) {
				System.out.print(forest[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.print(" ");
		for (int i = 0; i < forest.length; i++) {
			System.out.print("--");
		}
		System.out.println();

	}

	public void nextStep() {
		r = new Random();
		
		// Update fuel	
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				if (fuel[i][j] == 0) {
					// Replacing the tree with ash
					forest[i][j] = ASH;
				} else {
					// Decrementing avaliable fuel
					fuel[i][j]--;
				}
			}
		}
		
		// Spread sparks
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				// Checking spread south
				if (i < forest.length - 1 && forest[i + 1][j] == TREE) {
					if (r.nextDouble() < spreadProb) {
						forest[i + 1][j] = SPARK;
					}
				}
				// Checking spread north
				else if (i > 0 && forest[i - 1][j] == TREE) {
					if (r.nextDouble() < spreadProb) {
						forest[i - 1][j] = SPARK;
					}
				}
				// Checking spread east
				else if (j < forest.length - 1 && forest[i][j + 1] == TREE) {
					if (r.nextDouble() < spreadProb) {
						forest[i][j + 1] = SPARK;
					}
				}
				else if (j > 0 && forest[i][j - 1] == TREE) {
					if (r.nextDouble() < spreadProb) {
						forest[i][j - 1] = SPARK;
					}
				}
			}
		}	

				
		// Change sparks to fire		
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				if (forest[i][j] == SPARK) {
					forest[i][j] = FIRE;
				}
			}
		}
		
		// New fires
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				if (forest[i][j] == TREE && r.nextDouble() < lightningStrikeProb) {
					forest[i][j] = SPARK;
				}
			}
		}

	}

	public double getForestDensity() {

		return 0;
	}

	public double getSizeOfFire() {

		return 0;
	}

	public int getFuelLevel() {

		return 0;
	}

}
