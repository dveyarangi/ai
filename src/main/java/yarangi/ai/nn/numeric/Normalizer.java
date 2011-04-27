package yarangi.ai.nn.numeric;

/**
 * A generic neural network i/o normalizer interface.
 * 
 * @author hazai
 */
public interface Normalizer {
	
	/**
	 * Normalizes given input array.
	 * @param value
	 * @return
	 */
	public double [] normalizeInput(double [] value);
	
	/**
	 * Normalizes given output array.
	 * @param value
	 * @return
	 */
	public double [] normalizeOutput(double [] value);
	
	/**
	 * Denormalizes given input array.
	 * @param value
	 * @return
	 */
	public double [] denormalizeOutput(double [] value);

	public int inputSize();
	
}
