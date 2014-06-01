package edu.usc.csci561.NeuralNetwork;

// Source -  http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/NNTrainingScheme.java

public interface NNTrainingScheme {
	Vector processInput(FeedForwardNeuralNetwork network, Vector input);

	void processError(FeedForwardNeuralNetwork network, Vector error);

	void setNeuralNetwork(FunctionApproximator ffnn);
}