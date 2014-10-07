package budgie.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import budgie.model.Neuron;

public class NeuronTest {

	private Neuron neuron;


	@Before
	public void setup() {
		neuron = new Neuron();
	}
	
	@Test
	public void isActivated_shouldReturnFalse_atTheBeginning() {
		boolean activated = neuron.isActivated();
		
		assertThat(activated, is(false));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void addConnection_withNullNeuron() {
		double weight = 0.7;
		neuron.addConnection(null, weight);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addConnection_withNegativeWeight(){
		Neuron neuron2 = new Neuron();
		neuron.addConnection(neuron2, -1.0);
	}
	
	@Test
	public void getNeurons_shouldContainAllNeurons() {
		Random rand = new Random();
		Neuron[] neurons = new Neuron[3];
		double[] weights = new double[3];
		for (int i = 0; i < neurons.length; i++) {
			double weight = rand.nextDouble();
			neurons[i] = new Neuron();
			weights[i] = weight;
			
			neuron.addConnection(neurons[i], weights[i]);
		}
		
		Map<Neuron, Double> result = neuron.getNeurons();
		
		assertThat(result.keySet(), hasItems(neurons[0], neurons[1], neurons[2]));
	}

}
