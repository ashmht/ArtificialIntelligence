package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/LogSigActivationFunction.java

public class LogSigActivationFunction implements ActivationFunction {

	public double activation(double parameter) {

		return 1.0 / (1.0 + Math.pow(Math.E, (-1.0 * parameter)));
	}

	public double deriv(double parameter) {
		double e = 1.0 / (1.0 + Math.pow(Math.E, (-1.0 * parameter)));
		return e * (1.0 - e);
	}
}
