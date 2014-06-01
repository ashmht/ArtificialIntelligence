package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/NNExample.java
import java.util.ArrayList;
import java.util.List;

public class NNExample {
	private final List<Double> normalizedInput, normalizedTarget;

	public NNExample(List<Double> normalizedInput, List<Double> normalizedTarget) {
		this.normalizedInput = normalizedInput;
		this.normalizedTarget = normalizedTarget;
	}

	public NNExample copyExample() {
		List<Double> newInput = new ArrayList<Double>();
		List<Double> newTarget = new ArrayList<Double>();
		for (Double d : normalizedInput) {
			newInput.add(new Double(d.doubleValue()));
		}
		for (Double d : normalizedTarget) {
			newTarget.add(new Double(d.doubleValue()));
		}
		return new NNExample(newInput, newTarget);
	}

	public Vector getInput() {
		Vector v = new Vector(normalizedInput);
		return v;

	}

	public Vector getTarget() {
		Vector v = new Vector(normalizedTarget);
		return v;

	}

	public boolean isCorrect(Vector prediction) {
		/*
		 * compares the index having greatest value in target to index having
		 * greatest value in prediction. If identical, correct
		 */
		return getTarget().indexHavingMaxValue() == prediction
				.indexHavingMaxValue();
	}
}
