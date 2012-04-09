package yarangi.ai.nn.fast;

import yarangi.ai.nn.init.InitializerFactory;
import yarangi.ai.nn.numeric.NumericAF;

public class FullLayer
{
	private double [] weights;
	
	private double [] output;
	
	private int bias = 1;
	
	private int inputLendth;
	
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	public FullLayer(int size, int inputLength)
	{
		// input weights + bias weights
		this.weights = new double [(inputLength+1)*size];
		
		for(int idx = 0; idx < weights.length; idx ++)
		{
			weights[idx] = InitializerFactory.createWeight();
		}
		
		this.output = new double [size+1];
		output [size] = 1;
		
		this.inputLendth = inputLength;
	}
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	protected FullLayer(double [] weigths, int inputLength)
	{
		this.weights = weigths;
		
		this.output = new double [weigths.length/(inputLength+1)];
		
	}
	
	
	public double [] getOutput() { return output; }
	
	public int getInputLendth() { return inputLendth; }
	
	public double [] activate(double [] input, NumericAF af)
	{
		double sigma;
		int neurons = input.length;
		int offset = 0;
		for(int nIdx = 0; nIdx < output.length-1; nIdx ++) 
		{
			sigma = bias * weights[offset ++];
			for(int wIdx = 0; wIdx < neurons; wIdx ++)
			{
				sigma += input[wIdx] * weights[offset ++]; 
			}
			
			
			output[nIdx] = af.calculate( sigma );
		}
		
		return output;
	}
	
	protected double [] getWeights()
	{
		return weights;
	}
	
}
