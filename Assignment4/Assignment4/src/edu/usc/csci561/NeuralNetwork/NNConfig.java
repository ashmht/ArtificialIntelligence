package edu.usc.csci561.NeuralNetwork;

// Source - http://aima-java.googlecode.com/svn/trunk/aima-core/src/main/java/aima/core/learning/neural/NNConfig.java

import java.util.Hashtable;

public class NNConfig {
	private final Hashtable<String, Object> hash;

	public NNConfig(Hashtable<String, Object> hash) {
		this.hash = hash;
	}

	public NNConfig() {
		this.hash = new Hashtable<String, Object>();
	}

	public double getParameterAsDouble(String key) {

		return (Double) hash.get(key);
	}

	public int getParameterAsInteger(String key) {

		return (Integer) hash.get(key);
	}

	public void setConfig(String key, Double value) {
		hash.put(key, value);
	}

	public void setConfig(String key, int value) {
		hash.put(key, value);
	}
}
