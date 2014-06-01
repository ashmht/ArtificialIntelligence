package edu.usc.ai.csci561.wp;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ClauseSymbols {
	Set<Literal> clause1Literals, clause1PositiveLiterals,
			clause1NegativeLiterals;

	Set<Literal> clause2Literals, clause2PositiveLiterals,
			clause2NegativeLiterals;

	Set<Literal> positiveInClause1NegativeInClause2,
			negativeInClause1PositiveInClause2;

	public ClauseSymbols(Clause clause1, Clause clause2) {

		clause1Literals = new Converter<Literal>().listToSet(clause1
				.getLiterals());
		clause1PositiveLiterals = PLResolution.getPositiveSymbolsIn(clause1);
		clause1NegativeLiterals = PLResolution.getNegativeSymbolsIn(clause1);

		clause2Literals = new Converter<Literal>().listToSet(clause2
				.getLiterals());
		clause2PositiveLiterals = PLResolution.getPositiveSymbolsIn(clause2);
		clause2NegativeLiterals = PLResolution.getNegativeSymbolsIn(clause2);

		positiveInClause1NegativeInClause2 = intersection(
				clause1PositiveLiterals, clause2NegativeLiterals);
		negativeInClause1PositiveInClause2 = intersection(
				clause1NegativeLiterals, clause2PositiveLiterals);

	}

	private Set<Literal> intersection(Set<Literal> c1, Set<Literal> c2) {
		// TODO Auto-generated method stub
		Iterator<Literal> iter1 = c1.iterator();
		Iterator<Literal> iter2 = c2.iterator();
		Set<Literal> commonLiterals = new HashSet<Literal>();
		while (iter1.hasNext()) {
			Literal l = iter1.next();
			while (iter2.hasNext()) {
				Literal l1 = iter2.next();
				if (l.resolve(l1)) {
					// If it resolves remove from one of the sets or add in
					// intersection set
					commonLiterals.add(l);
				}
			}
		}
		return commonLiterals;
	}

	public Set<Literal> getComplementedLiterals() {
		return union(positiveInClause1NegativeInClause2,
				negativeInClause1PositiveInClause2);
	}

	private Set<Literal> union(Set<Literal> c1, Set<Literal> c2) {
		// TODO Auto-generated method stub
		Set<Literal> allLiterals = new HashSet<Literal>(c1);
		allLiterals.addAll(c2);
		return allLiterals;
	}

}
