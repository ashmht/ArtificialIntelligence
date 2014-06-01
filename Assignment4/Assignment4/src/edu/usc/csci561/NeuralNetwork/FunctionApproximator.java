package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/FunctionApproximator.java
public interface FunctionApproximator {

	Vector processInput(Vector input);

	void processError(Vector error);
}
