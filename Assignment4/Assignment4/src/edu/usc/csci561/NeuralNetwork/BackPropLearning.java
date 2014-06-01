package edu.usc.csci561.NeuralNetwork;

// Source 

// http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/BackPropLearning.java
public class BackPropLearning implements NNTrainingScheme {
	private final double learningRate;
	private final double momentum;

	private Layer hiddenLayer;
	private Layer outputLayer;
	private LayerSensitivity hiddenSensitivity;
	private LayerSensitivity outputSensitivity;

	public BackPropLearning(double learningRate, double momentum) {

		this.learningRate = learningRate;
		this.momentum = momentum;

	}

	public void setNeuralNetwork(FunctionApproximator fapp) {
		FeedForwardNeuralNetwork ffnn = (FeedForwardNeuralNetwork) fapp;
		this.hiddenLayer = ffnn.getHiddenLayer();
		this.outputLayer = ffnn.getOutputLayer();
		this.hiddenSensitivity = new LayerSensitivity(hiddenLayer);
		this.outputSensitivity = new LayerSensitivity(outputLayer);
	}

	public Vector processInput(FeedForwardNeuralNetwork network, Vector input) {

		hiddenLayer.feedForward(input);
		outputLayer.feedForward(hiddenLayer.getLastActivationValues());
		return outputLayer.getLastActivationValues();
	}

	public void processError(FeedForwardNeuralNetwork network, Vector error) {
		// TODO calculate total error somewhere
		// create Sensitivity Matrices
		outputSensitivity.sensitivityMatrixFromErrorMatrix(error);

		hiddenSensitivity
				.sensitivityMatrixFromSucceedingLayer(outputSensitivity);

		// calculate weight Updates
		calculateWeightUpdates(outputSensitivity,
				hiddenLayer.getLastActivationValues(), learningRate);
		calculateWeightUpdates(hiddenSensitivity,
				hiddenLayer.getLastInputValues(), learningRate);

		// calculate Bias Updates
		calculateBiasUpdates(outputSensitivity, learningRate);
		calculateBiasUpdates(hiddenSensitivity, learningRate);

		// update weightsAndBiases
		outputLayer.updateWeights();
		outputLayer.updateBiases();

		hiddenLayer.updateWeights();
		hiddenLayer.updateBiases();

	}

	public Matrix calculateWeightUpdates(LayerSensitivity layerSensitivity,
			Vector previousLayerActivationOrInput, double alpha, double momentum) {
		Layer layer = layerSensitivity.getLayer();
		Matrix activationTranspose = previousLayerActivationOrInput.transpose();
		Matrix momentumLessUpdate = layerSensitivity.getSensitivityMatrix()
				.times(activationTranspose).times(alpha).times(-1.0);
		Matrix updateWithMomentum = layer.getLastWeightUpdateMatrix()
				.times(momentum).plus(momentumLessUpdate.times(1.0 - momentum));
		layer.acceptNewWeightUpdate(updateWithMomentum.copy());
		return updateWithMomentum;
	}

	public static Matrix calculateWeightUpdates(
			LayerSensitivity layerSensitivity,
			Vector previousLayerActivationOrInput, double alpha) {
		Layer layer = layerSensitivity.getLayer();
		Matrix activationTranspose = previousLayerActivationOrInput.transpose();
		Matrix weightUpdateMatrix = layerSensitivity.getSensitivityMatrix()
				.times(activationTranspose).times(alpha).times(-1.0);
		// Matrix weightUpdateMatrix = layerSensitivity.getSensitivityMatrix()
		// .times(activationTranspose).times(alpha);
		layer.acceptNewWeightUpdate(weightUpdateMatrix.copy());
		return weightUpdateMatrix;
	}

	public Vector calculateBiasUpdates(LayerSensitivity layerSensitivity,
			double alpha, double momentum) {
		Layer layer = layerSensitivity.getLayer();
		Matrix biasUpdateMatrixWithoutMomentum = layerSensitivity
				.getSensitivityMatrix().times(alpha).times(-1.0);

		Matrix biasUpdateMatrixWithMomentum = layer.getLastBiasUpdateVector()
				.times(momentum)
				.plus(biasUpdateMatrixWithoutMomentum.times(1.0 - momentum));
		Vector result = new Vector(
				biasUpdateMatrixWithMomentum.getRowDimension());
		for (int i = 0; i < biasUpdateMatrixWithMomentum.getRowDimension(); i++) {
			result.setValue(i, biasUpdateMatrixWithMomentum.get(i, 0));
		}
		layer.acceptNewBiasUpdate(result.copyVector());
		return result;
	}

	public static Vector calculateBiasUpdates(
			LayerSensitivity layerSensitivity, double alpha) {
		Layer layer = layerSensitivity.getLayer();
		Matrix biasUpdateMatrix = layerSensitivity.getSensitivityMatrix()
				.times(alpha).times(-1.0);
		// Matrix biasUpdateMatrix = layerSensitivity.getSensitivityMatrix()
		// .times(alpha);

		Vector result = new Vector(biasUpdateMatrix.getRowDimension());
		for (int i = 0; i < biasUpdateMatrix.getRowDimension(); i++) {
			result.setValue(i, biasUpdateMatrix.get(i, 0));
		}
		layer.acceptNewBiasUpdate(result.copyVector());
		return result;
	}
}
