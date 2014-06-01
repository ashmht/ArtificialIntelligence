package edu.usc.ai.csci561.wp;

import java.io.IOException;

public class CNFConverter {
	public static int output;

	public static void makeCNF(int[][] matrix) {
		// Construct Constraint 1
		// One player can sit on one and only 1 table
		Sentence sentence = new Sentence();
		// X11 v X12 v ... v X1N
		for (int i = 0; i < WeddingPlanner.no_of_guests; ++i) {
			Clause clause = new Clause();

			for (int j = 0; j < WeddingPlanner.no_of_tables; ++j) {
				// Add the literal to the clause
				clause.getLiterals().add(new Literal(i, j, false));
			}
			sentence.getSentence().add(clause);
		}
		// ¬X1i v ¬X1j
		for (int i = 0; i < WeddingPlanner.no_of_guests; ++i) {
			for (int j = 0; j < WeddingPlanner.no_of_tables; ++j) {
				for (int k = j + 1; k < WeddingPlanner.no_of_tables; ++k) {
					// Add the literal to the clause
					Clause clause = new Clause();
					clause.getLiterals().add(new Literal(i, j, true));
					clause.getLiterals().add(new Literal(i, k, true));
					sentence.getSentence().add(clause);
				}
			}
		}

		// Constraint 2
		// If two guests are friends they sit on the same table

		//
		for (int i = 0; i < WeddingPlanner.no_of_guests; ++i) {
			for (int j = i + 1; j < WeddingPlanner.no_of_guests; ++j) {
				if (checkFriends(i, j)) {
					// i and j are friends
					// Generate the CNF clauses
					for (int k = 0; k < WeddingPlanner.no_of_tables; ++k) {
						// For each table create the friendship clauses for i
						// and j
						Clause clause = new Clause();

						// (¬X1n v X2n)
						clause.getLiterals().add(new Literal(i, k, true));
						clause.getLiterals().add(new Literal(j, k, false));
						sentence.getSentence().add(clause);

						clause = new Clause();
						// (¬X2n v X1n)
						clause.getLiterals().add(new Literal(j, k, true));
						clause.getLiterals().add(new Literal(i, k, false));
						sentence.getSentence().add(clause);
					}
				}
			}

		}

		// Constraint 3
		// Enemies cannot sit on the same table
		for (int i = 0; i < WeddingPlanner.no_of_guests; ++i) {
			for (int j = i + 1; j < WeddingPlanner.no_of_guests; ++j) {
				if (checkEnemy(i, j)) {
					// i and j are friends
					// Generate the CNF clauses
					for (int k = 0; k < WeddingPlanner.no_of_tables; ++k) {
						// For each table create the friendship clauses for i
						// and j
						Clause clause = new Clause();

						// (¬Xin v ¬Xjn)
						clause.getLiterals().add(new Literal(i, k, true));
						clause.getLiterals().add(new Literal(j, k, true));
						sentence.getSentence().add(clause);
					}
				}
			}

		}
		System.out.println(sentence.getSentence().size());
		boolean result = false;
		try {
			result = PLResolution.plResolution(sentence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);

		if (result) {
			output = 1;
		} else
			output = 0;
	}

	public static boolean checkFriends(int i, int j) {
		if (WeddingPlanner.frenemyMatrix[i][j] == 1)
			return true;
		else
			return false;

	}

	public static boolean checkEnemy(int i, int j) {
		if (WeddingPlanner.frenemyMatrix[i][j] == -1)
			return true;
		else
			return false;

	}
}
