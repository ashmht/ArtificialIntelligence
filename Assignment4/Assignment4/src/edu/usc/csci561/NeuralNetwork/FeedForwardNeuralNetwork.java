package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/FeedForwardNeuralNetwork.java


public class FeedForwardNeuralNetwork implements FunctionApproximator {

	public static final String UPPER_LIMIT_WEIGHTS = "upper_limit_weights";
	public static final String LOWER_LIMIT_WEIGHTS = "lower_limit_weights";
	public static final String NUMBER_OF_OUTPUTS = "number_of_outputs";
	public static final String NUMBER_OF_HIDDEN_NEURONS = "number_of_hidden_neurons";
	public static final String NUMBER_OF_INPUTS = "number_of_inputs";
	private final Layer hiddenLayer;
	private final Layer outputLayer;

	private NNTrainingScheme trainingScheme;

	public FeedForwardNeuralNetwork(NNConfig config) {

		int numberOfInputNeurons = config
				.getParameterAsInteger(NUMBER_OF_INPUTS);
		int numberOfHiddenNeurons = config
				.getParameterAsInteger(NUMBER_OF_HIDDEN_NEURONS);
		int numberOfOutputNeurons = config
				.getParameterAsInteger(NUMBER_OF_OUTPUTS);

		double lowerLimitForWeights = config
				.getParameterAsDouble(LOWER_LIMIT_WEIGHTS);
		double upperLimitForWeights = config
				.getParameterAsDouble(UPPER_LIMIT_WEIGHTS);

		hiddenLayer = new Layer(numberOfHiddenNeurons, numberOfInputNeurons,
				lowerLimitForWeights, upperLimitForWeights,
				new LogSigActivationFunction());

		outputLayer = new Layer(numberOfOutputNeurons, numberOfHiddenNeurons,
				lowerLimitForWeights, upperLimitForWeights,
				new PureLinearActivationFunction());

	}

	public FeedForwardNeuralNetwork(Matrix hiddenLayerWeights,
			Vector hiddenLayerBias, Matrix outputLayerWeights,
			Vector outputLayerBias) {

		hiddenLayer = new Layer(hiddenLayerWeights, hiddenLayerBias,
				new LogSigActivationFunction());
		outputLayer = new Layer(outputLayerWeights, outputLayerBias,
				new PureLinearActivationFunction());

	}

	public void processError(Vector error) {

		trainingScheme.processError(this, error);

	}

	public Vector processInput(Vector input) {
		return trainingScheme.processInput(this, input);
	}


	public Matrix getHiddenLayerWeights() {

		return hiddenLayer.getWeightMatrix();
	}

	public Vector getHiddenLayerBias() {

		return hiddenLayer.getBiasVector();
	}

	public Matrix getOutputLayerWeights() {

		return outputLayer.getWeightMatrix();
	}

	public Vector getOutputLayerBias() {

		return outputLayer.getBiasVector();
	}

	public Layer getHiddenLayer() {
		return hiddenLayer;
	}

	public Layer getOutputLayer() {
		return outputLayer;
	}

	
}