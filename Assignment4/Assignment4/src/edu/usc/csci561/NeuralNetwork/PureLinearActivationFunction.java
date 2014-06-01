package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/PureLinearActivationFunction.java
public class PureLinearActivationFunction implements ActivationFunction {

	public double activation(double parameter) {
		return parameter;
	}

	public double deriv(double parameter) {

		return 1;
	}
}
