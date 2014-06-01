package CSCI561Assignment1;

import java.util.ArrayList;
import java.util.Stack;

public class DFS {
	public static ArrayList<Node> dfs(ArrayList<Node> SocialGraph,
			ArrayList<Node> DFSResult, Node source, Node goal) {

		Stack<Node> s = new Stack<Node>();

		s.push(source);

		while (!s.isEmpty()) {
			Node n = s.pop();

			if (!n.getVisited())
				n.setVisited(true);
			else
				continue;

			if (!DFSResult.contains(n)) {
				DFSResult.add(n);
			}
			// Check for goal
			if (n.equals(goal)) {
				ArrayList<Node> path = new ArrayList<Node>();
				BFS.findPath(n, path, SocialGraph, source);
				path.add(0, source);
				DFSResult.clear();
				DFSResult.addAll(path);
				break;
			}
			ArrayList<Node> sortedNeighbors = new ArrayList<Node>();
			for (Integer i : n.getNeighbors().keySet()) {
				Node node = SocialGraph.get(i - 1);
				if (node.getId() == n.getParentId())
					continue;
				if (!node.getVisited())
					node.setParentId(n.getId());
				if (!sortedNeighbors.contains(node)
						&& n.getParentId() != node.getId())
					sortedNeighbors.add(node);
			}
			BFS.sort(sortedNeighbors);

			for (int i = sortedNeighbors.size() - 1; i >= 0; --i) {
				if (!sortedNeighbors.get(i).getVisited()) {
					s.push(sortedNeighbors.get(i));
				}

			}
		}
		return DFSResult;

	}
}
