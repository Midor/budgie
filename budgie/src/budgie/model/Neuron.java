package budgie.model;

import java.util.HashMap;
import java.util.Map;

public class Neuron {
	
	private Map<Neuron, Double> inputs = new HashMap<Neuron, Double>();

	public boolean isActivated() {
		// TODO Auto-generated method stub
		return false;
	}

	public void addConnection(Neuron neuron, double weight) {
		if(neuron == null || weight < 0)
		{
			throw new IllegalArgumentException("The neuron was null");
		}
		inputs.put(neuron, new Double(weight));
	}

	public Map<Neuron, Double> getNeurons() {
		return inputs;
	}

}
