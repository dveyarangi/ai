package yarangi.ai.nn.fast;

import yarangi.ai.nn.init.InitializerFactory;
import yarangi.ai.nn.numeric.NumericAF;

public class FullLayer
{
	private double [] weights;
	
	private double [] output;
	
	private int bias = 1;
	
	private int neurons;
	
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	public FullLayer(int size, FullLayer prevLayer)
	{
		this(size, prevLayer.getNeuronCount());
	}
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	public FullLayer(int inputSize)
	{
		this(inputSize, inputSize);
	}
	
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	private FullLayer(int ns, int inputSize)
	{
		this.neurons = ns+1;
		// input weights + bias weights
		this.weights = new double [inputSize*(neurons-1)];
		
		for(int idx = 0; idx < weights.length; idx ++)
		{
			weights[idx] = InitializerFactory.createWeight();
		}
		
		this.output = new double [neurons];
		output [neurons-1] = bias;
	}
	/**
	 * Creates full layer
	 * @param size
	 * @param input
	 * @param af
	 */
	protected FullLayer(double [] weigths, int neurons)
	{
		this.weights = weigths;
		
		this.neurons = neurons;
		
		this.output = new double [neurons];
		
	}
	
	
	public double [] getOutput() { return output; }

	public double [] activate(double [] input, NumericAF af)
	{
		double sigma;
		int offset = 0;
		for(int nIdx = 0; nIdx < output.length-1; nIdx ++) 
		{
			sigma = 0;
			for(int wIdx = 0; wIdx < input.length; wIdx ++)
				sigma += input[wIdx] * weights[offset ++];
			
			output[nIdx] = af.calculate( sigma );
		}
		
		output[output.length-1] = bias;
		
		return output;
	}
	
	protected double [] getWeights()
	{
		return weights;
	}
	public int getNeuronCount()
	{
		return neurons;
	}
	
}
