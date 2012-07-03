package yarangi.ai.nn.numeric;

import java.io.Serializable;

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
	
//	private Map <NumericNeuronInput, Double> inputs = new HashMap<NumericNeuronInput, Double> ();
	
	private double [] weights;
	
	private ArrayInput input;
	
	private NumericNeuronInput output;
	private double sum;
	
	public NumericNeuron(NumericAF naf, ArrayInput input)
	{
		AF = naf;
		output = new NumericNeuronInput();
		
		weights = new double[input.size()];
		for(int idx = 0; idx < weights.length; idx ++)
		{
			weights[idx] = InitializerFactory.createWeight();
		}
		this.input = input;
		
//		addInput(new NumericNeuronInput(bias));
	}

	public void activate() 
	{
		sum = 0;
		
		for(int idx = 0; idx < weights.length; idx ++)
			sum += input.getValue( idx ) * weights[idx];
		
		output.setValue(AF.calculate(sum));
	}

	public NumericNeuronInput getOutput() { return output; }

	protected double getSum() { return sum; }
	
	protected double getWeight(int idx) { return weights[idx]; }
	
	protected void setWeight(int idx, double weight) { weights[idx] = weight; }

	public NumericAF getAF() { return AF; }
	
/*	public String toDescriptor()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("N_")
		sb.append("F=").append(AF.toDescriptor());
		for()
	}*/
}
