package yarangi.ai.genetic.nn;

import yarangi.ai.genetic.ICandidate;
import yarangi.ai.nn.fast.FullNetwork;
import yarangi.ai.nn.fast.NetworkDescriptor;
import yarangi.ai.nn.fast.Serializer;
import yarangi.ai.nn.numeric.NumericAF;

public class NNCandidate  implements ICandidate
{
	
	private double [] chromosome = null;
	
	private FullNetwork network;
	
	private double errorSum = 0;
	private double errorCount = 0;

	public NNCandidate(FullNetwork network)
	{
		this.network = network;
		this.chromosome = Serializer.toArray( network );
	}
	public NNCandidate(double [] chromosome, NetworkDescriptor descriptor)
	{
		this.network = Serializer.fromArray( chromosome, descriptor );
		this.chromosome = chromosome;
	}

	@Override
	public double[] perform(double[] input, double [] realOutput)
	{
		double [] netOutput = network.activate( input );
		double sum = 0;
		for(int idx = 0; idx < realOutput.length; idx ++) {
			sum += (realOutput[idx] - netOutput[idx])*(realOutput[idx] - netOutput[idx]);
		}
		
		errorSum += Math.sqrt( sum );
		errorCount ++;
		
		return netOutput;
	}

	@Override
	public double[] getChromosome()
	{
		return chromosome;
	}
	@Override
	public double getScore()
	{
		if(!hasScore())
			return Double.MAX_VALUE;
		return errorSum / errorCount;
	}
	@Override
	public boolean hasScore()
	{
		return (errorCount > 10);
	}

}
