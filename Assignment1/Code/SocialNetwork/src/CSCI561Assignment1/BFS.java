package CSCI561Assignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
	public static void sort(ArrayList<Node> nList) {

		Boolean swapped = true;
		int j = 0;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < nList.size() - j; i++) {
				if (nList.get(i).getName()
						.compareTo(nList.get(i + 1).getName()) > 0) {
					Node temp = nList.get(i);
					nList.set(i, nList.get(i + 1));
					nList.set(i + 1, temp);
					swapped = true;
				}
			}
		}
	}

	public static ArrayList<Node> bfs(ArrayList<Node> SocialGraph,
			ArrayList<Node> BFSResult, Node source, Node goal) {

		// Make a queue

		Queue<Node> q = new LinkedList<Node>();
		ArrayList<Node> sortedNeighbors = new ArrayList<Node>();
		ArrayList<Node> path = new ArrayList<Node>();

		// Add source node
		q.add(source);
		source.setVisited(true);

		while (!q.isEmpty()) {
			Node n = q.poll();
			if (!BFSResult.contains(n))
				BFSResult.add(n);
			// Check for goal
			if (n.equals(goal)) {
				findPath(n, path, SocialGraph, source);
				path.add(0, source);
				BFSResult.clear();
				BFSResult.addAll(path);
				break;
			}
			for (Integer i : n.getNeighbors().keySet()) {
				Node node = SocialGraph.get(i - 1);
				if (!node.getVisited()) {
					node.setVisited(true);
					if (node.getId() == n.getParentId())
						continue;
					node.setParentId(n.getId());
					if (node.equals(goal)) {
						if (!BFSResult.contains(node))
							BFSResult.add(node);
						findPath(node, path, SocialGraph, source);
						path.add(0, source);
						BFSResult.clear();
						BFSResult.addAll(path);
						return path;
					}
					if (!sortedNeighbors.contains(node))
						sortedNeighbors.add(node);
				}
			}
			sort(sortedNeighbors);

			for (int i = 0; i < sortedNeighbors.size(); ++i) {
				if (!q.contains(sortedNeighbors.get(i)))
					q.add(sortedNeighbors.get(i));
			}
		}
		return path;
	}

	public static Boolean isDuplicate(ArrayList<Node> nList, Node n) {

		for (int i = 0; i < nList.size(); ++i) {
			if (nList.get(i).getId() == n.getId())
				return true;
		}
		return false;
	}

	public static void findPath(Node n, ArrayList<Node> path,
			ArrayList<Node> SG, Node source) {
		while (n.getParentId() != source.getId()) {
			int pId = n.getParentId();
			path.add(n);
			n = SG.get(pId - 1);
		}
		path.add(n);
		Collections.reverse(path);
	}

}
