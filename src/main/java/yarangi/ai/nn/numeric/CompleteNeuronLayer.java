package yarangi.ai.nn.numeric;

import java.io.Serializable;
import java.util.Collection;

import yarangi.ai.nn.Input;
import yarangi.ai.nn.Node;

/**
 * TODO: this is not very efficient implementation.
 * @author hazai
 *
 */
public class CompleteNeuronLayer implements Node, Serializable
{
	
	private static final long serialVersionUID = 3680535935428250424L;

	private NumericNeuron [] neurons;
	
	private ArrayInput output;
	
	private ArrayInput input;
	
//	private NumericAF AF;
	
	public CompleteNeuronLayer(int n, NumericAF naf, double bias)
	{
		output = new ArrayInput(n);
		neurons = new NumericNeuron [n];
		for(int idx = 0; idx < n; idx ++)
		{
			neurons[idx] = new NumericNeuron(naf);
			output.add(idx, (NumericNeuronInput)neurons[idx].getOutput());
		}
		
		if(bias != 0)
			addInput(new NumericNeuronInput(bias));
	}
	
	public CompleteNeuronLayer(NumericAF [] nafs, double bias)
	{
		output = new ArrayInput(nafs.length);
//		this.AF = naf;
		neurons = new NumericNeuron [nafs.length];
		for(int idx = 0; idx < nafs.length; idx ++)
		{
			neurons[idx] = new NumericNeuron(nafs[idx]);
			output.add(idx, (NumericNeuronInput)neurons[idx].getOutput());
		}
		
		if(bias != 0)
			addInput(new NumericNeuronInput(bias));
	}	
	protected NumericNeuron [] getNeurons() { return neurons; }
	
//	public NumericAF getAF() { return AF; }

	public void activate() 
	{
		for(int idx = 0; idx < neurons.length; idx ++)
			neurons[idx].activate();
	}

	public void addInput(Input array) 
	{
		input = (ArrayInput) array;
		for(int idx = 0; idx < neurons.length; idx ++)
			for(NumericNeuronInput nni : input)
				neurons[idx].addInput(nni);
	}
	
	protected void addInput(NumericNeuronInput nni) 
	{
		for(int idx = 0; idx < neurons.length; idx ++)
				neurons[idx].addInput(nni);
	}

	public Collection<? extends Input> getInputs() {
		return input;
	}

	public Input getOutput() { return output; }

/*	protected double getWeight(int neuronIdx, int inputIdx) {
		return neurons[neuronIdx].getWeight(input.get(inputIdx));
	}
	
	protected void setWeight(int neuronIdx, int inputIdx, double weight) {
		neurons[neuronIdx].setWeight(input.get(inputIdx), weight);
	}*/

}
