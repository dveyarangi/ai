package yarangi.ai.nn.numeric;

/**
 * This implementation of normalizer scales input/output values to fit predefined values interval.
 * @author hazai
 *
 */
public class ScalingNormalizer implements Normalizer 
{
	double [] inputOffset;
	double [] inputRange;
	double [] outputOffset;
	double [] outputRange;
	
	/**
	 * 
	 * @param minInput
	 * @param maxInput
	 * @param minOutput
	 * @param maxOutput
	 */
	public ScalingNormalizer(double [] minInput, double [] maxInput, double [] minOutput, double [] maxOutput)
	{
		this.inputRange = new double [minInput.length];
		this.inputOffset = new double [minInput.length];
		for(int idx = 0; idx < minInput.length; idx ++)
		{
			inputRange[idx] = maxInput[idx] - minInput[idx];
			inputOffset[idx] = - minInput[idx] / inputRange[idx];
		}
		
		this.outputRange = new double [minOutput.length];
		this.outputOffset = new double [minInput.length];
		for(int idx = 0; idx < minOutput.length; idx ++)
		{
			outputRange[idx] = maxOutput[idx] - minOutput[idx];
			outputOffset[idx] = - minOutput[idx] / outputRange[idx];
		}
	}

	public double [] denormalizeOutput(double [] value) {
		double [] output = new double [value.length];
		for(int iIdx = 0; iIdx < output.length; iIdx ++)
			output[iIdx] = (value[iIdx] - outputOffset[iIdx]) * outputRange[iIdx];
		return output;
	}

	public double [] normalizeInput(double [] value) 
	{
		double [] input = new double [value.length];
		for(int iIdx = 0; iIdx < input.length; iIdx ++)
			input[iIdx] = value[iIdx] / inputRange[iIdx] + inputOffset[iIdx];
		
		return input;
	}

	public double [] normalizeOutput(double [] value) 
	{
		double [] output = new double [value.length];
		for(int oIdx = 0; oIdx < output.length; oIdx ++)
			output[oIdx] = value[oIdx] / outputRange[oIdx] + outputOffset[oIdx];
		
		return output;
	}

	@Override
	public int inputSize() { return inputRange.length; }

}
