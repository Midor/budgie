package budgie.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;

import budgie.calculation.IFunction;
import budgie.model.Neuron;

public class NeuronTest {

	private Neuron neuron;
	private IFunction<Neuron, Double> mockFunction;

	@SuppressWarnings("unchecked")
	@Before
	public void setup() {
		mockFunction = mock(IFunction.class);
		neuron = new Neuron(mockFunction);
	}
	
	@Test
	public void output_shouldBeAHalf_atTheBeginning() {
		assertThat(neuron.output(), is(0.5));
	}
	
	@Test
	public void output_shouldBeAHalf_whenInputHasWeightZero() {
		Neuron inputNeuron = new Neuron(mockFunction);
		neuron.addConnection(inputNeuron, 0);
		assertThat(neuron.output(), is(0.5));
	}
	
	@Test
	public void output_shouldBeBetweenAHalfAndOne_whenInputIsGreaterThanZero() {
		Neuron inputMockNeuron = mock(Neuron.class);
		when(inputMockNeuron.output()).thenReturn(0.5);
		
		neuron.addConnection(inputMockNeuron, 1);
		
		assertThat(neuron.output(), is(greaterThan(0.5)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void addConnection_withNullNeuron() {
		double weight = 0.7;
		neuron.addConnection(null, weight);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addConnection_withNegativeWeight() {
		Neuron neuron2 = new Neuron(mockFunction);
		neuron.addConnection(neuron2, -1.0);
	}

	@Test
	public void getNeurons_shouldContainAllNeurons() {
		Random rand = new Random();
		Neuron[] neurons = new Neuron[3];
		double[] weights = new double[3];
		for (int i = 0; i < neurons.length; i++) {
			double weight = rand.nextDouble();
			neurons[i] = new Neuron(mockFunction);
			weights[i] = weight;

			neuron.addConnection(neurons[i], weights[i]);
		}

		Map<Neuron, Double> result = neuron.getNeurons();

		assertThat(result.keySet(),
				hasItems(neurons[0], neurons[1], neurons[2]));
	}
	
	private static <T extends Comparable<T>> org.hamcrest.Matcher<T> greaterThan(Comparable<T> threshold){
		return new GreaterThan<T>(threshold);
	}
}
