package edu.usc.ai.csci561.wp;

import java.util.ArrayList;
import java.util.List;

public class Sentence {
	private List<Clause> sentence;

	public Sentence() {
		setSentence(new ArrayList<Clause>());
	}

	/**
	 * @return the sentence
	 */
	public ArrayList<Clause> getSentence() {
		return (ArrayList<Clause>) sentence;
	}

	/**
	 * @param arrayList
	 *            the sentence to set
	 */
	public void setSentence(ArrayList<Clause> arrayList) {
		this.sentence = arrayList;
	}

	@Override
	public String toString() {
		return "Sentence + " + sentence.toString();
	}

}
