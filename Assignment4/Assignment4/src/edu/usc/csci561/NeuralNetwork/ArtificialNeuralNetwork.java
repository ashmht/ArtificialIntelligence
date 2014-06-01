package edu.usc.csci561.NeuralNetwork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArtificialNeuralNetwork {

	private static ArrayList<ImageData> imagesTrain;
	private static ArrayList<ImageData> imagesTest;
	private static FeedForwardNeuralNetwork ffnn;
	private static BackPropLearning bpl;
	private static List<Integer> predictedValues;

	public static void main(String args[]) {
		// Read the data and construct NNConfig
		readInputData(args);
		constructNeuralNetwork();
		trainNN();
		testNN();
		writeOutput(args);

	}

	private static void writeOutput(String[] args) {
		// TODO Auto-generated method stub
		FileWriter fstream = null;
		String outputFileName = "output.txt";
		try {
			if (args != null)
				fstream = new FileWriter(args[3]);
			else
				fstream = new FileWriter(outputFileName);
			BufferedWriter out = new BufferedWriter(fstream);

			for (Integer i : predictedValues)
				out.write(i.toString() + "\n");

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void testNN() {
		// TODO Auto-generated method stub
		int[] result = new int[] { 0, 0 };
		for (int i = 0; i < imagesTest.size(); ++i) {
			ImageData image = imagesTest.get(i);
			List<Double> idata = new ArrayList<Double>();
			List<Double> tdata = new ArrayList<Double>();
			for (int k = 0; k < image.cols; ++k) {
				for (int j = 0; j < image.rows; ++j) {
					idata.add((double) image.data[j][k] / 150);
				}
			}

			for (int l = 0; l < 10; ++l)
				tdata.add(0.0);
			tdata.set(image.label, 1.0);
			// idata = Util.normalize(idata);
			// tdata = Util.normalize(tdata);
			NNExample example = new NNExample(idata, tdata);

			Vector prediction = predict(example);

			// Add to arrayList of predicted values
			getPredictedValues().add(prediction.indexHavingMaxValue());

			if (example.isCorrect(prediction)) {
				result[0] = result[0] + 1;
			} else {
				result[1] = result[1] + 1;
				// System.out.println("Prediction is "
				// + prediction.indexHavingMaxValue() + " Desired is "
				// + example.getTarget().indexHavingMaxValue());
			}
		}
		// System.out.println("Correct " + result[0]);
		// System.out.println("Incorrect " + result[1]);
		// float acc = (float) (result[0] * 100) / (result[0] + result[1]);
		// System.out.println("Accuracy " + acc);

		// int[] result = new int[] { 0, 0 };
		// ImageData image = imagesTrain.get(500);
		// List<Double> idata = new ArrayList<Double>();
		// List<Double> tdata = new ArrayList<Double>();
		// for (int k = 0; k < image.cols; ++k) {
		// for (int j = 0; j < image.rows; ++j) {
		// idata.add((double) image.data[j][k]);
		// }
		// }
		//
		// for (int l = 0; l < 10; ++l)
		// tdata.add(0.0);
		// tdata.set(image.label, 1.0);
		// idata = Util.normalize(idata);
		// tdata = Util.normalize(tdata);
		// NNExample example = new NNExample(idata, tdata);
		//
		// Vector prediction = predict(example);
		//
		// // Add to arrayList of predicted values
		// getPredictedValues().add(prediction.indexHavingMaxValue());
		// System.out.println("Prediction is " +
		// prediction.indexHavingMaxValue()
		// + " Desired is " + example.getTarget().indexHavingMaxValue());
		// if (example.isCorrect(prediction)) {
		// result[0] = result[0] + 1;
		// } else {
		// result[1] = result[1] + 1;
		// }
		// System.out.println("Correct " + result[0]);
		// System.out.println("Incorrect " + result[1]);
		// float acc = (float) (result[0] * 100) / (result[0] + result[1]);
		// System.out.println("Accuracy " + acc);
	}

	private static Vector predict(NNExample example) {
		// TODO Auto-generated method stub
		return bpl.processInput(ffnn, example.getInput());

	}

	private static void trainNN() {
		// TODO Auto-generated method stub

		for (int n = 0; n < 3; ++n) {
			for (int i = 0; i < imagesTrain.size(); ++i) {
				ImageData image = imagesTrain.get(i);
				List<Double> idata = new ArrayList<Double>();
				List<Double> tdata = new ArrayList<Double>();
				for (int k = 0; k < image.cols; ++k) {
					for (int j = 0; j < image.rows; ++j) {
						idata.add((double) image.data[j][k] / 150);
					}
				}
				for (int l = 0; l < 10; ++l)
					tdata.add(0.0);
				tdata.set(image.label, 1.0);
				// idata = Util.normalize(idata);
				// tdata = Util.normalize(tdata);
				NNExample example = new NNExample(idata, tdata);

				bpl.processInput(ffnn, example.getInput());
				Vector error = ffnn.getOutputLayer().errorVectorFrom(
						example.getTarget());
				bpl.processError(ffnn, error);
			}
		}

		// ImageData image = imagesTrain.get(500);
		// List<Double> idata = new ArrayList<Double>();
		// List<Double> tdata = new ArrayList<Double>();
		// for (int k = 0; k < image.cols; ++k) {
		// for (int j = 0; j < image.rows; ++j) {
		// idata.add((double) image.data[j][k]);
		// }
		// }
		//
		// for (int l = 0; l < 10; ++l)
		// tdata.add(0.0);
		// tdata.set(image.label, 1.0);
		// idata = Util.normalize(idata);
		// tdata = Util.normalize(tdata);
		// NNExample example = new NNExample(idata, tdata);
		// for (int m = 0; m < 100; ++m) {
		// bpl.processInput(ffnn, example.getInput());
		// Vector error = ffnn.getOutputLayer().errorVectorFrom(
		// example.getTarget());
		// bpl.processError(ffnn, error);
		// }

	}

	private static void constructNeuralNetwork() {
		// TODO Auto-generated method stub
		NNConfig config = new NNConfig();
		config.setConfig("upper_limit_weights", 0.15);
		config.setConfig("lower_limit_weights", -0.15);
		config.setConfig("number_of_outputs", 10);
		config.setConfig("number_of_hidden_neurons", 100);
		config.setConfig("number_of_inputs", 784);
		ffnn = new FeedForwardNeuralNetwork(config);
		bpl = new BackPropLearning(0.08, 0.0);
		bpl.setNeuralNetwork(ffnn);
		setPredictedValues(new ArrayList<Integer>());
		// Number of Iterations n= 3
	}

	private static void readInputData(String[] args) {
		// TODO Auto-generated method stub
		setImagesTrain(ImageData.readData(args[0] + ".idx3-ubyte", args[1]
				+ ".idx1-ubyte"));
		setImagesTest(ImageData.readImageFile(args[2] + ".idx3-ubyte"));
		// setImagesTest(ImageData.readData(args[2] + ".idx3-ubyte",
		// "test-labels.idx1-ubyte"));
		// System.out.println(getImagesTrain());
		// System.out.println(getImagesTest());
	}

	/**
	 * @return the imagesTrain
	 */
	public static ArrayList<ImageData> getImagesTrain() {
		return imagesTrain;
	}

	/**
	 * @param imagesTrain
	 *            the imagesTrain to set
	 */
	public static void setImagesTrain(ArrayList<ImageData> imagesTrain) {
		ArtificialNeuralNetwork.imagesTrain = imagesTrain;
	}

	/**
	 * @return the imagesTest
	 */
	public static ArrayList<ImageData> getImagesTest() {
		return imagesTest;
	}

	/**
	 * @param imagesTest
	 *            the imagesTest to set
	 */
	public static void setImagesTest(ArrayList<ImageData> imagesTest) {
		ArtificialNeuralNetwork.imagesTest = imagesTest;
	}

	/**
	 * @return the predictedValues
	 */
	public static List<Integer> getPredictedValues() {
		return predictedValues;
	}

	/**
	 * @param predictedValues
	 *            the predictedValues to set
	 */
	public static void setPredictedValues(List<Integer> predictedValues) {
		ArtificialNeuralNetwork.predictedValues = predictedValues;
	}
}
