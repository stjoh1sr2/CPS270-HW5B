package edu.cmich.cps270;

import java.util.Random;

/**
 * Simulates a forest fire.
 * 
 * @author stjoh1sr, black9m, gupt4a
 * Homework 5B
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
	
	/**
	 * Initializes the simulator.
	 * @param N THe number of iterations.
	 * @param forestDensity The density of the forest.
	 * @param lightningStrikeProb The probability of a lightning strike.
	 * @param spreadProb The probability of the fire spreading.
	 */
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
	
	/**
	 * Prints the forest at a certain step.
	 */
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
	
	/**
	 * Computes the next step in the simulation.
	 */
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
	
	/**
	 * Gets the density of the forest.
	 * @return The density of the forest.
	 */
	public double getForestDensity() {
		int numTrees = 0;
		int forestSize = forest.length * forest[0].length;
		
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				if (forest[i][j] == TREE) {
					numTrees++;
				}
			}
		}
		
		double forestDensity = (numTrees != 0) ? (double)numTrees / (double)forestSize : 0;
		return forestDensity;
	}
	
	/**
	 * Gets size of fire.
	 * @return Size of fire.
	 */
	public double getSizeOfFire() {
		int amountOfFire = 0;
		int forestSize = forest.length * forest[0].length;
		
		for(int i = 0; i < forest.length; i++) {
			for(int j = 0; j < forest[i].length; j++) {
				if (forest[i][j] == FIRE) {
					amountOfFire++;
				}
			}
		}
		
		double sizeOfFire = (amountOfFire != 0) ? (double)amountOfFire /(double)forestSize : 0;
		
		return sizeOfFire;
	}
	
	/**
	 * Gets the fuel level.
	 * @return The level of fuel.
	 */
	public int getFuelLevel() {
		int fuelLevel = 0;
		
		for(int i = 0; i < fuel.length; i++) {
			for(int j = 0; j < fuel[i].length; j++) {
				fuelLevel += fuel[i][j];
			}
		}
		
		return fuelLevel;
	}

}
