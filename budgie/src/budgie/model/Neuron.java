package budgie.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import budgie.calculation.IFunction;

public class Neuron {

	private Map<Neuron, Double> inputs = new HashMap<Neuron, Double>();
	private IFunction<Double, Double> thresholdFunc;

	public Neuron(IFunction<Double, Double> thresholdFunc) {
		this.thresholdFunc = thresholdFunc;
	}

	public void addConnection(Neuron neuron, double weight) {
		if (neuron == null) {
			throw new IllegalArgumentException("The neuron was null");
		}
		inputs.put(neuron, new Double(weight));
	}

	public Map<Neuron, Double> getNeurons() {
		return inputs;
	}

	public double output() {
		double acc = 0.0;
		for (Entry<Neuron, Double> entry : inputs.entrySet()) {
			Neuron neuron = entry.getKey();
			Double weight = entry.getValue();
			acc += neuron.output() * weight.doubleValue();
		}
		return thresholdFunc.calculate(new Double(acc)).doubleValue();
	}
}
