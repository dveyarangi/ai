package yarangi.ai.nn.init;

import yarangi.ai.nn.numeric.NumericNeuronInput;

/**
 * Weight initialization strategy that creates random weights inside defined values interval.
 * 
 * @author hazai
 *
 */
public class RandomWeightsInitializer implements WeightsInitializer
{
	private double minValue;
	private double span;
	
	/**
	 * Creates a new weigh initializer that creates random weights inside defined values interval.
	 * 
	 * @param minValue
	 * @param maxValue
	 * @throws IllegalArgumentException if minValue is bigger than maxValue; or if one of the interval borders has an illegal value.
	 */
	public RandomWeightsInitializer(double minValue, double maxValue)
	{
		if(maxValue < minValue)
			throw new IllegalArgumentException("Min value is bigger than max value.");
		if(maxValue == Double.NaN)
			throw new IllegalArgumentException("Interval supremum has illegal value.");
		if(minValue == Double.NaN)
			throw new IllegalArgumentException("Interval supremum has illegal value.");
		
		this.minValue = minValue;
		this.span = maxValue-minValue;
	}

	/**
	 * 
	 */
	public double createWeight(@SuppressWarnings("unused") NumericNeuronInput input) {
		return Math.random() * span + minValue;
	}
	
	
}
