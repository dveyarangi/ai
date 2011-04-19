package yarangi.ai.nn.numeric;

import java.io.Serializable;

import yarangi.ai.nn.ActivationFunction;

/**
 * Template interface for activation function implementation.
 * 
 * @author Hazai
 *
 * @param <T> - type of data
 */
public interface NumericAF extends ActivationFunction, Serializable
{
	/**
	 * Applies the activation function to given value.
	 * 
	 * @param sigma - input value.
	 * @return Calculation results.
	 */
    public double calculate(double sigma);
    
    /**
     * Calculates derivative of activation function at sigma.
     * 
     * @param sigma
     * @return
     */
    public double derivative(double sigma);
}