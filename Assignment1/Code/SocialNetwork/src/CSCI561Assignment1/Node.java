package CSCI561Assignment1;

import java.util.Map;

public class Node {
	private static int count = 0;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	private String name;
	private Map<Integer, Integer> neighbors;
	private Boolean visited;
	private int parentId;
	private int depth;
	private int id;
	private int pathCost;

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	// Initial constructor values
	public Node(String name) {
		super();
		this.name = name;
		// this.neighbors = null;
		this.visited = false;
		this.parentId = -1;
		this.depth = 0;
		this.id = ++count;
	}

	public void addNeighbors(int nodeId, int cost) {
		if (!neighbors.containsKey(nodeId))
			neighbors.put(nodeId, cost);
	}

	public Map<Integer, Integer> getNeighbors() {
		return neighbors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + depth;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((neighbors == null) ? 0 : neighbors.hashCode());
		result = prime * result + parentId;
		result = prime * result + ((visited == null) ? 0 : visited.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (depth != other.depth)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (neighbors == null) {
			if (other.neighbors != null)
				return false;
		} else if (!neighbors.equals(other.neighbors))
			return false;
		if (parentId != other.parentId)
			return false;
		if (visited == null) {
			if (other.visited != null)
				return false;
		} else if (!visited.equals(other.visited))
			return false;
		return true;
	}

	public void setNeighbors(Map<Integer, Integer> neighbors) {
		this.neighbors = neighbors;
	}

	@Override
	public String toString() {
		return this.name;

	}

	/**
	 * @return the pathCost
	 */
	public int getPathCost() {
		return pathCost;
	}

	/**
	 * @param pathCost the pathCost to set
	 */
	public void setPathCost(int pathCost) {
		this.pathCost = pathCost;
	}
}
