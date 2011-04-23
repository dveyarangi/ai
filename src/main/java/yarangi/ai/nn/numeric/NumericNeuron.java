package yarangi.ai.nn.numeric;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import yarangi.ai.nn.Input;
import yarangi.ai.nn.Node;
import yarangi.ai.nn.init.InitializerFactory;

/**
 * This is a numeric neuron implementation that uses a NumericAF for activation. 
 * 
 * @author hazai
 *
 */
public class NumericNeuron implements Node, Serializable
{

	private static final long serialVersionUID = -1389077614948612177L;

	private NumericAF AF;
	
	private Map <NumericNeuronInput, Double> inputs = new HashMap<NumericNeuronInput, Double> ();
	
	private NumericNeuronInput output;
	private double sum;
	
	public NumericNeuron(NumericAF naf)
	{
		AF = naf;
		output = new NumericNeuronInput();
		
//		addInput(new NumericNeuronInput(bias));
	}

	public void activate() 
	{
		sum = 0;
		for(NumericNeuronInput input : inputs.keySet())
			sum += inputs.get(input) * input.getValue();
		output.setValue(AF.calculate(sum));
	}

	public Input getOutput() { return output; }

	protected double getSum() { return sum; }
	
	public void addInput(Input input) 
	{
		NumericNeuronInput i = (NumericNeuronInput) input;
		inputs.put(i, InitializerFactory.createWeight(i));
	}
	
	

	public Collection <? extends Input> getInputs() { return inputs.keySet(); }
	
	protected double getWeight(NumericNeuronInput input) { return inputs.get(input); }
	
	protected void setWeight(NumericNeuronInput input, double weight) { inputs.put(input, weight); }

	public NumericAF getAF() { return AF; }
}
