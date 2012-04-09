package yarangi.ai.genetic;

public class MinSquaresEvaluator implements IEvaluator
{

	@Override
	public double evaluate(double[] realOutput, double[] candidateOutput)
	{
		int size = realOutput.length;
	
		if(size != candidateOutput.length)
			throw new IllegalArgumentException("Output lengths must be the same.");
		
		
		double sum = 0;
		for(int idx = 0; idx < size; idx ++) 
		{
			sum += (candidateOutput[idx] - realOutput[idx])*(candidateOutput[idx] - realOutput[idx]);
		}
		
		return sum / size;
	}

}
