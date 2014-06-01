package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/ActivationFunction.java

public interface ActivationFunction {
	double activation(double parameter);

	double deriv(double parameter);
}
