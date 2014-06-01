package edu.usc.ai.csci561.cf;

import java.util.HashMap;

public class AlphaBetaPruning {

	// Source Citation: http://en.wikipedia.org/wiki/Alphabeta_pruning
	static String hue = "";
	static HashMap<Integer, Integer> hMap = new HashMap<Integer, Integer>();
	static int firstMove = 0;
	static StringBuffer output = new StringBuffer();

	public static int alphabeta(Node node, int depth, int alpha, int beta,
			boolean maximizingPlayer) {
		if (!hMap.keySet().toString()
				.contains(String.valueOf(node.getColumn())))
			hMap.put(node.getColumn(), node.getHeuristicValue());
		else {
			hMap.put(node.getColumn(),
					node.getHeuristicValue() + hMap.get(node.getColumn()));
		}
		// firstMove = getMax(hMap);
		if (node.isTerminalNode()) {
			hue = String.valueOf(node.getHeuristicValue());
		} else {
			if (node.getGameBoard().playerWins('a')
					|| node.getGameBoard().playerLoses('a')) {
				hue = String.valueOf(node.getHeuristicValue());
				for (int i = 0; i < node.getDepth() - 1; ++i) {
					System.out.print("|-");
					output.append("|-");
				}

				System.out.print(node.getGameBoard().getCurrentPlayer()
						+ node.getDepth() + ": " + node.getColumn());
				if (node.getDepth() > 0) {
					output.append(node.getGameBoard().getCurrentPlayer()
							+ node.getDepth() + ": " + node.getColumn());

					System.out.println("; h=" + hue);
					output.append("; h=" + hue + "\n");
				}
				return Integer.parseInt(hue);
			}
		}
		if (node.getDepth() > 0) {
			for (int i = 0; i < node.getDepth() - 1; ++i) {
				System.out.print("|-");
				output.append("|-");

			}
			System.out.print(node.getGameBoard().getCurrentPlayer()
					+ node.getDepth() + ": " + node.getColumn());
			output.append(node.getGameBoard().getCurrentPlayer()
					+ node.getDepth() + ": " + node.getColumn());
			if (node.isTerminalNode() && !hue.equals("")) {
				System.out.println("; h=" + hue);
				output.append("; h=" + hue + "\n");

			} else {
				System.out.println();
				output.append("\n");
			}
		}
		if (depth == 0 || node.isTerminalNode()) {
			hue = String.valueOf(node.getHeuristicValue());
			return node.getHeuristicValue();
		}
		if (maximizingPlayer) {
			for (Node child : node.getChildren()) {
				int fMove = Math.max(alpha,
						alphabeta(child, depth - 1, alpha, beta, false));
				if (alpha < fMove) {
					alpha = fMove;
					if (depth == 4) {
						firstMove = child.getColumn();
					}
				}
				if (beta <= alpha) {
					printPruning(child, node, alpha, beta);
					break;
				}
			}
			return alpha;
		} else {
			for (Node child : node.getChildren()) {
				beta = Math.min(beta,
						alphabeta(child, depth - 1, alpha, beta, true));
				if (beta <= alpha) {
					printPruning(child, node, alpha, beta);
					break;
				}

			}
			return beta;
		}
	}

	/*
	 * private static int getMax(HashMap<Integer, Integer> hMap2) { // TODO
	 * Auto-generated method stub Entry<Integer, Integer> maxEntry = null; for
	 * (Entry<Integer, Integer> entry : hMap2.entrySet()) { if (maxEntry == null
	 * || entry.getValue().compareTo(maxEntry.getValue()) > 0) { maxEntry =
	 * entry; } } return maxEntry.getKey(); }
	 */

	private static void printPruning(Node child, Node node, int alpha, int beta) {
		// TODO Auto-generated method stub
		if (child.getDepth() > 0) {
			if (!getOtherChildren(child, node).equals("")) {
				for (int i = 0; i < child.getDepth() - 1; ++i) {
					System.out.print("|-");
					output.append("|-");
				}
				System.out.print(child.getGameBoard().getCurrentPlayer()
						+ child.getDepth());
				output.append(child.getGameBoard().getCurrentPlayer()
						+ child.getDepth());
				System.out.println(": pruning " + getOtherChildren(child, node)
						+ "; alpha=" + alpha + ", beta=" + beta);
				output.append(": pruning " + getOtherChildren(child, node)
						+ "; alpha=" + alpha + ", beta=" + beta + "\n");
			}
		}

	}

	private static String getOtherChildren(Node child, Node node) {
		// TODO Auto-generated method stub
		StringBuffer s = new StringBuffer();
		int childIndex = node.getChildren().indexOf(child);
		for (int i = childIndex + 1; i < node.getChildren().size(); ++i) {
			if (i == node.getChildren().size() - 1) {
				s.append(node.getChildren().get(i).getColumn());
				return s.toString();
			} else
				s.append(node.getChildren().get(i).getColumn() + ", ");
		}
		return "";
	}
}
