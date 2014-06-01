package edu.usc.ai.csci561.wp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Clause {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((literals == null) ? 0 : literals.hashCode());
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
		Clause other = (Clause) obj;
		Iterator<Literal> cl1 = this.getLiterals().iterator();
		int count = 0;
		while (cl1.hasNext()) {
			Literal l = cl1.next();
			Iterator<Literal> cl2 = other.getLiterals().iterator();
			while (cl2.hasNext()) {
				Literal l2 = cl2.next();
				if (l.equals(l2)) {
					count++;
					break;
				}
			}
		}
		if (count == this.getLiterals().size()
				&& count == other.getLiterals().size())
			return true;
		return false;
	}

	private List<Literal> literals;

	public Clause() {
		setLiterals(new ArrayList<Literal>());
	}

	/**
	 * @return the literals
	 */
	public List<Literal> getLiterals() {
		return literals;
	}

	@Override
	public String toString() {
		return "Clause " + literals.toString();
	}

	/**
	 * @param literals
	 *            the literals to set
	 */
	public void setLiterals(List<Literal> literals) {
		this.literals = literals;
	}
}
