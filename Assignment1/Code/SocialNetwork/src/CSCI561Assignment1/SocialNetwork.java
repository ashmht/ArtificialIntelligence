package CSCI561Assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocialNetwork {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> friendNames = new ArrayList<String>();
		ArrayList<Node> SocialGraph = new ArrayList<Node>();
		ArrayList<Node> bfsResult = new ArrayList<Node>();
		ArrayList<Node> dfsResult = new ArrayList<Node>();
		ArrayList<Node> ucsResult = new ArrayList<Node>();

		// Read the input file
		// try {
		String filename = args[0];
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		int linecount = 0;

		// Read line by line

		// Read all the Node names and store them in a arraylist

		while ((strLine = br.readLine()) != null) {

			// System.out.println(strLine);

			String[] friendshipCost = strLine.split(" ");
			ArrayList<Integer> ifCost = new ArrayList<Integer>();

			// Node names;
			if (strLine.matches("[a-zA-Z]*")) {
				// System.out.println(strLine);
				friendNames.add(strLine);
				Map<Integer, Integer> map = new HashMap<Integer, Integer>();
				Node node = new Node(strLine);
				node.setNeighbors(map);
				// Create Node structure
				SocialGraph.add(node);

			} else if (strLine.matches("[0-9 ]*")) {
				// System.out.println(strLine);
				++linecount;
				for (int i = 0; i < friendshipCost.length; ++i)
					ifCost.add(Integer.parseInt(friendshipCost[i]));

				// Add to adjacency list.
				for (int i = 0; i < SocialGraph.size(); ++i) {
					if (linecount == SocialGraph.get(i).getId()) {
						for (int j = 0; j < ifCost.size(); ++j) {
							if (ifCost.get(j) > 0) {

								SocialGraph.get(i).addNeighbors(
										SocialGraph.get(j).getId(),
										ifCost.get(j));
							}
						}
					}
				}

			} else {
				StringBuffer errmsg = new StringBuffer("Invalid Input");
				System.err.println("Error :: " + errmsg);
			}
			if (linecount > 0) {
				// Check if number of names are equal to adj list size.
				if (ifCost.size() != friendNames.size()) {
					StringBuffer errmsg = new StringBuffer("Size Mismatch");
					System.err.println("Error :: " + errmsg);
				}
			}

		}
		in.close();

		// Print arraylists of friends and numbers

		// *********** DEBUG ******************

		for (int i = 0; i < SocialGraph.size(); ++i) {
			System.out.println("Node :: " + SocialGraph.get(i).getId() + " "
					+ SocialGraph.get(i).getName() + "\n"
					+ "Neighbors and Cost are "
					+ SocialGraph.get(i).getNeighbors().keySet()
					+ SocialGraph.get(i).getNeighbors().values());
		}

		/************************************************/
		// Perform bfs

		// Alice is the source
		Node source = null;
		Node goal = null;

		StringBuffer sourceNode = new StringBuffer("Alice");
		StringBuffer goalNode = new StringBuffer("Noah");

		for (int i = 0; i < SocialGraph.size(); ++i) {
			if (sourceNode.toString().equals(SocialGraph.get(i).getName())) {
				source = SocialGraph.get(i);
			}
			if (goalNode.toString().equals(SocialGraph.get(i).getName())) {
				goal = SocialGraph.get(i);
			}
		}

		// Flush visited nodes
		for (int i = 0; i < SocialGraph.size(); ++i)
			SocialGraph.get(i).setVisited(false);

		// Noah is the goal

		bfsResult = BFS.bfs(SocialGraph, bfsResult, source, goal);
		// HashSet<Node> bfsHS = new HashSet<Node>(bfsResult1);
		// ArrayList<Node> bfsResult = new ArrayList<Node>(bfsHS);

		// Write the output

		// Create file
		FileWriter fstream1 = new FileWriter("breadth-first.result.txt");
		BufferedWriter out = new BufferedWriter(fstream1);

		FileWriter fstream2 = new FileWriter("depth-first.result.txt");
		BufferedWriter out2 = new BufferedWriter(fstream2);

		FileWriter fstream3 = new FileWriter("uniform-cost.result.txt");
		BufferedWriter out3 = new BufferedWriter(fstream3);

		String bfs = null, dfs = null, ucs = null;
		for (int i = 0; i < bfsResult.size(); ++i) {
			StringBuffer sb = new StringBuffer(bfsResult.get(i).getName());
			if (bfs != null)
				bfs += "-" + sb.toString();
			if (i == 0)
				bfs = sb.toString();
		}

		String outputS = new String("" + bfs + "\n");
		out.write(outputS);
		out.close();

		// Flush visited nodes
		for (int i = 0; i < SocialGraph.size(); ++i) {
			SocialGraph.get(i).setVisited(false);
			SocialGraph.get(i).setParentId(-1);
		}
		// Perform dfs
		dfsResult = DFS.dfs(SocialGraph, dfsResult, source, goal);

		for (int i = 0; i < dfsResult.size(); ++i) {
			StringBuffer sb = new StringBuffer(dfsResult.get(i).getName());
			if (dfs != null)
				dfs += "-" + sb.toString();
			if (i == 0)
				dfs = sb.toString();
		}
		String outputS1 = new String("" + dfs + "\n");
		out2.write(outputS1);
		out2.close();

		// Flush visited nodes
		for (int i = 0; i < SocialGraph.size(); ++i) {
			SocialGraph.get(i).setVisited(false);
			SocialGraph.get(i).setParentId(-1);
		}
		// Perform uniform cost search
		ucsResult = UCS.ucs(SocialGraph, ucsResult, source, goal);

		for (int i = 0; i < ucsResult.size(); ++i) {
			StringBuffer sb = new StringBuffer(ucsResult.get(i).getName());
			if (ucs != null)
				ucs += "-" + sb.toString();
			if (i == 0)
				ucs = sb.toString();
		}

		String outputS2 = new String("" + ucs + "\n");

		out3.write(outputS2);
		// Close the output stream

		out3.close();

		// } catch (Exception e) {
		// FileWriter fstream1 = new FileWriter("Error.txt");
		// BufferedWriter out = new BufferedWriter(fstream1);
		// out.write("Error reading input file \n");
		// out.close();
		// }

	}
}
