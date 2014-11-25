package budgie.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.mockito.internal.matchers.LessThan;

import budgie.calculation.IFunction;
import budgie.model.Neuron;

public class NeuronTest {

	private Neuron neuron;
	private IFunction<Double, Double> mockFunction;

	@Before
	public void setup() {
		generateMockFunction();
		neuron = new Neuron(mockFunction);
	}

	@SuppressWarnings("unchecked")
	private void generateMockFunction() {
		mockFunction = mock(IFunction.class);
		when(mockFunction.calculate(new Double(-0.5))).thenReturn(new Double(0.2));
		when(mockFunction.calculate(new Double(0.0))).thenReturn(new Double(0.5));
		when(mockFunction.calculate(new Double(0.5))).thenReturn(new Double(0.8));
		when(mockFunction.calculate(new Double(2.0))).thenReturn(new Double(0.88));
		when(mockFunction.calculate(new Double(5.0))).thenReturn(new Double(0.98));
	}
	
	@Test
	public void output_shouldBeAHalf_atTheBeginning() {
		assertThat(neuron.output(), is(0.5));
	}
	
	@Test
	public void output_shouldBeAHalf_whenInputHasWeightZero() {
		Neuron inputNeuron = mock(Neuron.class);
		when(inputNeuron.output()).thenReturn(2.0);
		neuron.addConnection(inputNeuron, 0);
		assertThat(neuron.output(), is(0.5));
	}
	
	@Test
	public void output_shouldBeBetweenAHalfAndOne_withPositiveWeight() {
		Neuron inputMockNeuron = mock(Neuron.class);
		when(inputMockNeuron.output()).thenReturn(0.5);
		neuron.addConnection(inputMockNeuron, 1);
		
		assertThat(neuron.output(), is(greaterThan(0.5)));
		assertThat(neuron.output(), is(lessThan(1.0)));
	}
	
	@Test
	public void output_shouldBeBetweenAHalfAndOne_withNegativeWeight() {
		Neuron inputMockNeuron = mock(Neuron.class);
		when(inputMockNeuron.output()).thenReturn(0.5);
		neuron.addConnection(inputMockNeuron, -1.0);
		
		assertThat(neuron.output(), is(greaterThan(0.0)));
		assertThat(neuron.output(), is(lessThan(0.5)));
	}
	
	@Test
	public void output_shouldBeBetweenAHalfAndOne_withMoreNeuronsAndPositivWeights() throws Exception {
		HashSet<Neuron> neurons = new HashSet<Neuron>();
		for(int i=0; i < 10; i++)
		{
			neurons.add(new Neuron(mockFunction));
		}
		for (Neuron input : neurons) {
			neuron.addConnection(input, 1);
		}
		
		assertThat(neuron.output(), is(greaterThan(0.5)));
		assertThat(neuron.output(), is(lessThan(1.0)));
	}
	
	@Ignore
	@Test(expected = IllegalArgumentException.class)
	public void addConnection_shouldThrowError_whenInputIsItSelf() {
		// TODO
	}

	@Test(expected = IllegalArgumentException.class)
	public void addConnection_withNullNeuron() {
		double weight = 0.7;
		neuron.addConnection(null, weight);
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
	
	private static <T extends Comparable<T>> org.hamcrest.Matcher<T> lessThan(Comparable<T> threshold){
		return new LessThan<T>(threshold);
	}
}
