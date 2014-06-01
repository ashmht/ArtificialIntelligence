package edu.usc.ai.csci561.cf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConnectFour {

	public final static String inputFileName = "input.txt";
	public final static String outputFileName = "output.txt";
	public static final int ROWS = 6;
	public static final int COLUMNS = 7;
	public static String outputS = new String("");

	// DEBUG GAME STATE

	public static void initGame(char[][] GameState) {
		ArrayList<Node> gameTree = new ArrayList<Node>();
		ArrayList<Node> pGameTree = new ArrayList<Node>();
		char player = 'a';
		GameBoard gameBoard = new GameBoard();
		GameBoard tempGameBoard = new GameBoard();
		tempGameBoard.setGameState(GameState);
		gameBoard.setGameState(GameState);
		gameBoard.setCurrentPlayer("B");
		Node root = new Node();
		root.setDepth(0);
		root.setGameBoard(gameBoard);
		root.setParent(null);
		root.setColumn(1);
		
		if(root.getGameBoard().playerWins('a')) {
			outputS = "Player A has already won in the given input\n";
			return;
		} else if(root.getGameBoard().playerLoses('a')) {
			outputS = "Player B has already won in the given input\n";
			return;
		} else if(root.getGameBoard().isGameDraw()) {
			outputS = "The input is a drawn game\n";
			return;
		}

		gameTree.add(root);
		pGameTree.add(root);
		int depth = 1;
		Node parent = root;
		while (depth <= 4 && !gameTree.isEmpty() && !(isEmpty(GameState))) {
			ArrayList<Node> children = new ArrayList<Node>();
			for (int i = 0; i < COLUMNS; ++i) {
				for (int j = 0; j < ROWS; ++j) {
					if (GameState[i][j] == ' ') {
						if (parent.getGameBoard().getCurrentPlayer()
								.equals("B")) {
							player = 'a';
						} else {
							player = 'b';
						}
						Node child = new Node();
						child.setColumn(i + 1);
						child.setParent(parent);
						GameBoard childGameBoard = new GameBoard();
						childGameBoard.setGameState(parent.getGameBoard()
								.getGameState());

						childGameBoard.setGameState(i, j, player);

						// *****DEBUG ***********

						if (parent.getGameBoard().getCurrentPlayer()
								.equals("A")) {
							childGameBoard.setCurrentPlayer("B");
							childGameBoard.initXY(player);
						} else {
							childGameBoard.setCurrentPlayer("A");
							childGameBoard.initXY(player);
						}
						child.setDepth(depth);
						child.setGameBoard(childGameBoard);
						child.computeHeuristic();
						gameTree.add(child);
						pGameTree.add(child);
						children.add(child);
						break;
					}
				}
			}
			gameTree.remove(parent);
			parent.setChildren(children);
			if (!gameTree.isEmpty())
				parent = gameTree.get(0);
			else
				break;
			depth = parent.getDepth() + 1;
			GameState = parent.getGameBoard().getGameState();
		}

		AlphaBetaPruning.alphabeta(root, 4, (int) Double.NEGATIVE_INFINITY,
				(int) Double.POSITIVE_INFINITY, true);
		AlphaBetaPruning.output.append("first move: "
				+ AlphaBetaPruning.firstMove);
		outputS = AlphaBetaPruning.output.toString();
	}

	private static boolean isEmpty(char[][] gameState) {
		// TODO Auto-generated method stub
		for (int i = 0; i < COLUMNS; ++i) {
			for (int j = 0; j < ROWS; ++j) {
				if (gameState[i][j] == ' ')
					return false;
			}
		}
		return true;
	}

	public static void readInput(String args) throws IOException {
		String filename = args;
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		char gameState[][] = new char[COLUMNS][ROWS];
		for (int i = 0; i < COLUMNS; ++i) {
			for (int j = 0; j < ROWS; ++j) {
				gameState[i][j] = ' ';
			}
		}
		int lineNum = 0;
		while ((strLine = br.readLine()) != null) {
			lineNum++;
			if (strLine.matches("[a-zA-Z]*")) {
				for (int i = 0; i < strLine.length(); ++i) {
					if (strLine.charAt(i) == 'a' || strLine.charAt(i) == 'b') {
						gameState[lineNum - 1][i] = strLine.charAt(i);
					}
				}
			}
		}
		in.close();

		// Initialize gamestate

		initGame(gameState);

		// *** DEBUG ***
	}

	public static void writeOutput(String args) throws IOException {
		FileWriter fstream;
		if (args != null)
			fstream = new FileWriter(args);
		else
			fstream = new FileWriter(outputFileName);
		BufferedWriter out = new BufferedWriter(fstream);

		out.write(outputS);
		out.close();
	}

	public static void main(String args[]) throws IOException {

		readInput(args[0]);
		writeOutput(args[1]);
	}

}
