package yarangi.ai.nn.fast;

import yarangi.ai.nn.numeric.NumericAF;

public class NetworkDescriptor
{
	private NumericAF af;
	
	private int [] layers;
	
	public NetworkDescriptor(NumericAF af, int [] layers) 
	{
		this.af = af;
		this.layers = layers;
	}

	public NumericAF getActivationFunction()
	{
		return af;
	}

	public int[] getLayers()
	{
		return layers;
	}
	
	
}
