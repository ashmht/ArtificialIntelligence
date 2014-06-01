package CSCI561Assignment1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class UCS {
	public static ArrayList<Node> ucs(ArrayList<Node> SocialGraph,
			ArrayList<Node> UCSResult, Node source, Node goal) {

		Queue<Node> openList = new LinkedList<Node>();
		Queue<Node> closedList = new LinkedList<Node>();

		openList.add(source);
		source.setPathCost(0);

		while (true) {
			if (openList.isEmpty())
				return UCSResult;

			Node cur = openList.poll();

			if (cur.equals(goal)) {
				ArrayList<Node> path = new ArrayList<Node>();
				BFS.findPath(cur, path, SocialGraph, source);
				path.add(0, source);
				UCSResult.addAll(path);
				return UCSResult;
			}
			int curCost = cur.getPathCost();

			//for (int i = 0; i < cur.getNeighbors().size(); ++i) {
				for (Integer j : cur.getNeighbors().keySet()) {

					Node child = SocialGraph.get(j - 1);
					
					
					if (child.getId() == cur.getParentId())
						continue;
					
					if (child.getVisited() == false) {
						child.setVisited(true);
					}

					int parentPathCost = curCost;
					int childPathCost = curCost
							+ cur.getNeighbors().get(child.getId());
					if(child.getPathCost() == 0) {
						child.setParentId(cur.getId());
						child.setPathCost(childPathCost);
					}
					if(child.getPathCost() != 0 && (child.getPathCost() > childPathCost)) {
							child.setParentId(cur.getId());
							child.setPathCost(childPathCost);
					}
					if (!openList.contains(child)
							&& !closedList.contains(child)) {
						openList.add(child);
					} else if (openList.contains(child)) {

						if (childPathCost < parentPathCost) {
							openList.remove(cur);
							openList.add(child);
						}
					} else if (closedList.contains(child)) {
						if (childPathCost < parentPathCost) {
							closedList.remove(cur);
							openList.add(child);
						}
					}

				}
		//	}
			closedList.add(cur);
			sortByCost(openList);
		}

	}

	public static void sortByCost(Queue<Node> openList) {
		Boolean swapped = true;
		int j = 0;
		Node[] nodes = openList.toArray(new Node[openList.size()]);
		Node temp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < nodes.length - j; i++) {
				if (nodes[i].getPathCost() > nodes[i + 1].getPathCost()) {
					temp = nodes[i];
					nodes[i] = nodes[i + 1];
					nodes[i + 1] = temp;
					swapped = true;
				}
				if (nodes[i].getPathCost() == nodes[i + 1].getPathCost()) {
					if (nodes[i].getName().compareTo(nodes[i+1].getName()) > 0)
					{
						temp = nodes[i];
						nodes[i] = nodes[i + 1];
						nodes[i + 1] = temp;
						swapped = true;
					}
				}
			}
		}
		openList.clear();

		for (Node n : nodes)
			openList.add(n);

	}
}
