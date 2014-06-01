package edu.usc.ai.csci561.wp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeddingPlanner {
	public static int frenemyMatrix[][];
	public static int no_of_guests;
	public static int no_of_tables;

	public static void readInput(String args) throws IOException {
		String filename = args;
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String firstLine, strLine;

		int lineNum = 0;
		firstLine = br.readLine();
		String[] tabGuests = firstLine.split(" ");
		no_of_guests = Integer.parseInt(tabGuests[1]);
		no_of_tables = Integer.parseInt(tabGuests[0]);

		frenemyMatrix = new int[no_of_guests][no_of_guests];
		while ((strLine = br.readLine()) != null) {
			lineNum++;
			String[] guestList = strLine.split(" ");
			for (int i = 0; i < no_of_guests; ++i) {
				frenemyMatrix[lineNum - 1][i] = Integer.parseInt(guestList[i]);
			}
		}
		in.close();

		// Read matrix now make CNF
		CNFConverter.makeCNF(frenemyMatrix);

		// ___________DEBUG ___________
		/*
		 * for (int i = 0; i < no_of_guests; ++i) { for (int j = 0; j <
		 * no_of_guests; ++j) { System.out.print(frenemyMatrix[i][j] + " "); }
		 * System.out.println(); }
		 */
	}

	public static void main(String args[]) {
		try {
			readInput(args[0]);
			writeOutputWrapper(args[1]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// writeOutput(args[1]);
	}

	private static void writeOutputWrapper(String args) throws IOException {
		// TODO Auto-generated method stub
		FileWriter fstream = null;
		String outputFileName = "output.txt";
		if (args != null)
			try {
				fstream = new FileWriter(args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else
			fstream = new FileWriter(outputFileName);
		BufferedWriter out = new BufferedWriter(fstream);

		out.write(String.valueOf(CNFConverter.output));
		out.close();
	}

}
