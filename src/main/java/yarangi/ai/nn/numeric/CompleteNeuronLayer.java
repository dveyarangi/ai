package yarangi.ai.nn.numeric;

import java.io.Serializable;

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
	
/*	public CompleteNeuronLayer(int neuronNum, ArrayInput input, NumericAF naf, double bias)
	{
		NumericAF [] afs = new NumericAF[neuronNum];
		for(int idx = 0; idx < neuronNum; idx ++)
			afs[idx] = naf;
		
		this(afs, input, bias);
	}*/
	
	public CompleteNeuronLayer(NumericAF [] nafs, ArrayInput input, double bias)
	{
		output = new ArrayInput(nafs.length);
		this.input = new ArrayInput(input.size() + 1);
		
		// creating new array with same elements:
		for(int idx = 0; idx < input.size(); idx ++)
		{
			this.input.set( idx, input.getInput( idx ) );
		}
		
		// adding bias input:
		this.input.set( input.size(), new NumericNeuronInput( bias ) );
		
		neurons = new NumericNeuron [nafs.length];
		for(int idx = 0; idx < nafs.length; idx ++)
		{
			neurons[idx] = new NumericNeuron(nafs[idx], this.input);
			output.set(idx, neurons[idx].getOutput());
		}
		
//		if(bias != 0)
//			addInput(new NumericNeuronInput(bias));
	}	
	protected NumericNeuron [] getNeurons() { return neurons; }
	
//	public NumericAF getAF() { return AF; }

	public void activate() 
	{
		for(int idx = 0; idx < neurons.length; idx ++)
			neurons[idx].activate();
	}

	public ArrayInput getOutput() { return output; }
	
	protected ArrayInput getInput() { return input; }


/*	protected double getWeight(int neuronIdx, int inputIdx) {
		return neurons[neuronIdx].getWeight(input.get(inputIdx));
	}
	
	protected void setWeight(int neuronIdx, int inputIdx, double weight) {
		neurons[neuronIdx].setWeight(input.get(inputIdx), weight);
	}*/

}
