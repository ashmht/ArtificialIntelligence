package edu.usc.ai.csci561.wp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PLResolution {

	public static boolean plResolution(Sentence s) throws IOException {
		try {
			// Source ----
			// https://code.google.com/p/aima-java/source/browse/trunk/aima-core/src/main/java/aima/core/logic/propositional/algorithms/PLResolution.java?r=933
			Set<Clause> clauses = new HashSet<Clause>(s.getSentence());
			clauses = filterOutClausesWithTwoComplementaryLiterals(clauses);

			System.out.println("Initial Clauses generated \n" + clauses);
			System.out.println("Initial Clauses size \n" + clauses.size());
			Set<Clause> newClauses = new HashSet<Clause>();
			while (true) {
				List<List<Clause>> pairs = getCombinationPairs(new Converter<Clause>()
						.setToList(clauses));

				for (int i = 0; i < pairs.size(); i++) {
					List<Clause> pair = pairs.get(i);
					// System.out.println("pair number" +
					// i+" of "+pairs.size());
					Set<Clause> resolvents = plResolve(pair.get(0), pair.get(1));
					resolvents = filterOutClausesWithTwoComplementaryLiterals(resolvents);
					// Empty clause is with guest -1, -1, false
					if (doesresolventsContainEmptyClause(resolvents)) {
						System.out.println(clauses.size());
						System.out.println(clauses);
						System.out.println("false");
						return false;
					}
					newClauses = union(newClauses, resolvents);
					// System.out.println(" New Clauses added " + newClauses);
					// System.out.println("clauseslist size = "
					// +clauses.size());

				}
				if (checkintersection(clauses, newClauses)) {// subset test
					System.out.println(clauses.size());
					System.out.println(clauses);
					System.out.println("true");
					return true;
				}
				clauses = union(newClauses, clauses);
				clauses = filterOutClausesWithTwoComplementaryLiterals(clauses);
				// System.out.println("Every Clause added " + clauses);

			}
		} catch (Exception e) {
			String errorMsg = " Complexity too high ! Cannot be done with current CPU and Power !!";
			FileWriter fstream = null;
			String outputFileName = "error.log";
			fstream = new FileWriter(outputFileName);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write(errorMsg);
			out.close();
		}
		return false;
	}

	private static boolean doesresolventsContainEmptyClause(
			Set<Clause> resolvents) {
		// TODO Auto-generated method stub
		Clause c = new Clause();
		c.getLiterals().add(new Literal(-1, -1, false));
		Iterator<Clause> iter1 = resolvents.iterator();
		while (iter1.hasNext()) {
			Clause c2 = iter1.next();
			if (c2.equals(c)) {
				return true;
			}
		}
		return false;
	}

	private static Set<Clause> union(Set<Clause> newClauses,
			Set<Clause> resolvents) {
		// TODO Auto-generated method stub
		Set<Clause> newC = new HashSet<Clause>(newClauses);
		Set<Clause> rC = new HashSet<Clause>(resolvents);

		Iterator<Clause> iter1 = resolvents.iterator();
		while (iter1.hasNext()) {
			Clause c = iter1.next();
			Iterator<Clause> iter2 = newClauses.iterator();
			while (iter2.hasNext()) {
				Clause c1 = iter2.next();
				if (c.equals(c1)) {
					rC.remove(c);
					break;
				}
			}
		}
		newC.addAll(rC);
		return newC;
	}

	private static boolean checkintersection(Set<Clause> c1, Set<Clause> c2) {
		// TODO Auto-generated method stub
		int count = 0;
		Iterator<Clause> iter1 = c1.iterator();
		while (iter1.hasNext()) {
			Clause l = iter1.next();
			Iterator<Clause> iter2 = c2.iterator();
			while (iter2.hasNext()) {
				Clause l1 = iter2.next();
				if (l.equals(l1)) {
					count++;
					break;
				}
			}
		}
		if (count == c2.size()) {
			return true;
		}
		return false;
	}

	private static Set<Clause> plResolve(Clause clause1, Clause clause2) {
		// TODO Auto-generated method stub
		Set<Clause> resolvents = new HashSet<Clause>();
		ClauseSymbols cs = new ClauseSymbols(clause1, clause2);
		Iterator<Literal> iter1 = clause1.getLiterals().iterator();
		while (iter1.hasNext()) {
			Literal l = iter1.next();
			Iterator<Literal> iter2 = clause2.getLiterals().iterator();
			while (iter2.hasNext()) {
				Literal l1 = iter2.next();
				if (l.resolve(l1)) {
					resolvents.add(createResolventClause(cs, l, l1));
				}
			}
		}
		return resolvents;
	}

	private static Clause createResolventClause(ClauseSymbols cs,
			Literal toRemove, Literal toRemove2) {
		// TODO Auto-generated method stub
		List<Literal> positiveLiterals = new Converter<Literal>()
				.setToList(SetOps.union(cs.clause1PositiveLiterals,
						cs.clause2PositiveLiterals));
		List<Literal> negativeLiterals = new Converter<Literal>()
				.setToList(SetOps.union(cs.clause1NegativeLiterals,
						cs.clause2NegativeLiterals));
		// get positive literal
		if (positiveLiterals.contains(toRemove)) {
			positiveLiterals.remove(toRemove);
		}
		if (negativeLiterals.contains(toRemove)) {
			negativeLiterals.remove(toRemove);
		}
		if (positiveLiterals.contains(toRemove2)) {
			positiveLiterals.remove(toRemove2);
		}
		if (negativeLiterals.contains(toRemove2)) {
			negativeLiterals.remove(toRemove2);
		}

		Clause clauses = new Clause();
		for (int i = 0; i < positiveLiterals.size(); i++) {
			clauses.getLiterals().add(positiveLiterals.get(i));
		}
		for (int i = 0; i < negativeLiterals.size(); i++) {
			clauses.getLiterals().add(negativeLiterals.get(i));
		}
		if (clauses.getLiterals().size() == 0) {
			clauses.getLiterals().add(new Literal(-1, -1, false));// == empty
																	// clause
		}
		return clauses;
	}

	private static List<List<Clause>> getCombinationPairs(
			List<Clause> clausesList) {
		// TODO Auto-generated method stub
		List<List<Clause>> pairs = new ArrayList<List<Clause>>();
		for (int i = 0; i < clausesList.size(); i++) {
			for (int j = i; j < clausesList.size(); j++) {
				List<Clause> pair = new ArrayList<Clause>();
				Clause first = clausesList.get(i);
				Clause second = clausesList.get(j);

				if (!(first.equals(second))) {
					pair.add(first);
					pair.add(second);
					pairs.add(pair);
				}
			}
		}
		return pairs;
	}

	private static Set<Clause> filterOutClausesWithTwoComplementaryLiterals(
			Set<Clause> clauses) {
		// TODO Auto-generated method stub
		Set<Clause> filtered = new HashSet<Clause>(clauses);
		Iterator<Clause> iter = clauses.iterator();
		while (iter.hasNext()) {
			Clause clause = iter.next();
			Iterator<Literal> iter1 = clause.getLiterals().iterator();
			while (iter1.hasNext()) {
				Literal l = iter1.next();
				Iterator<Literal> iter2 = clause.getLiterals().iterator();
				while (iter2.hasNext()) {
					Literal l1 = iter2.next();
					if (l.resolve(l1) && !l.equals(l1)) {
						filtered.remove(clause);
						break;
					}
				}
			}

		}
		return filtered;
	}

	public static Set<Clause> intersection(Set<Literal> positiveSymbols,
			Set<Literal> negativeSymbols) {
		// TODO Auto-generated method stub
		Iterator<Literal> iter1 = positiveSymbols.iterator();
		Set<Clause> clauses = new HashSet<Clause>();
		while (iter1.hasNext()) {
			Literal l = iter1.next();
			Iterator<Literal> iter2 = negativeSymbols.iterator();
			while (iter2.hasNext()) {
				Literal l1 = iter2.next();
				if (!l.resolve(l1)) {
					Clause c = new Clause();
					c.getLiterals().add(l);
					c.getLiterals().add(l1);
					clauses.add(c);
				}
			}
		}
		return clauses;
	}

	public static Set<Literal> getNegativeSymbolsIn(Clause clause) {
		// TODO Auto-generated method stub
		Set<Literal> setLiteral = new HashSet<Literal>();
		for (Literal l : clause.getLiterals()) {
			if (l.isNegatedLiteral()) {
				setLiteral.add(l);
			}
		}
		return setLiteral;
	}

	public static Set<Literal> getPositiveSymbolsIn(Clause clause) {
		// TODO Auto-generated method stub
		Set<Literal> setLiteral = new HashSet<Literal>();
		for (Literal l : clause.getLiterals()) {
			if (!l.isNegatedLiteral()) {
				setLiteral.add(l);
			}
		}
		return setLiteral;
	}

}
