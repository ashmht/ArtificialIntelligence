package edu.usc.ai.csci561.cf;

import java.util.ArrayList;

public class Node {
	private static int count = 0;
	private int id;
	private GameBoard gameBoard;
	private ArrayList<Node> children;
	private int heuristicValue;
	private int depth;
	public Node parent;
	public int column;

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public Node() {
		++count;
		this.setId(count);
		children = new ArrayList<Node>();
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return Integer.toString(getId());
	}

	public boolean isTerminalNode() {
		if (children.isEmpty())
			return true;
		return false;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;

	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	/**
	 * @return the heuristicValue
	 */
	public int getHeuristicValue() {
		return heuristicValue;
	}

	/**
	 * @param heuristicValue
	 *            the heuristicValue to set
	 */
	public void setHeuristicValue(int heuristicValue) {
		this.heuristicValue = heuristicValue;
	}

	/**
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void computeHeuristic() {
		int HV = 0;
		if (gameBoard.playerWins('a')) {
			HV = 1000;
		} else if (gameBoard.playerLoses('a')) {
			HV = -1000;
		} else if (gameBoard.isGameDraw()) {
			HV = 0;
		} else {
			HV = (gameBoard.getxA() - gameBoard.getxB()) + 5
					* (gameBoard.getyA() - gameBoard.getyB());
		}
		setHeuristicValue(HV);
	}
	
	public void printGameState() {
		for (int a = 0; a < 7; ++a) {
			for (int b = 0; b < 6; ++b) {
				System.out
						.print(this.getGameBoard().getGameState()[a][b]
								+ " ");
			}
			System.out.println("");
		}

		System.out.println("\n");
	}
}
