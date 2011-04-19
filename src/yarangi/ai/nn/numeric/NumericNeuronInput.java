package yarangi.ai.nn.numeric;

import java.io.Serializable;

import yarangi.ai.nn.NeuronInput;

public class NumericNeuronInput implements NeuronInput, Serializable 
{

	private static final long serialVersionUID = 7706267690086565007L;
	
	double value = Double.NaN;
	
	public NumericNeuronInput(double value) { this.value = value; }
	
	public NumericNeuronInput()	{ }
	
	public double getValue() { return value; }
	
	public void setValue(double value) { this.value = value; }
	
	public String toString() { return String.valueOf(value); }
}
