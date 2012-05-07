package yarangi.ai.nn.init;


/**
 * Neural network initialization factory provides strategy for NN weights initialization.
 * @author hazai
 *
 */
public abstract class InitializerFactory
{
	public static WeightsInitializer weights = new RandomWeightsInitializer( 0, 0.00100 );
	
	/**
	 * Set default weights initialization strategy.
	 * @param initializer
	 */
	public static void setWeightsInitializer(WeightsInitializer initializer) { InitializerFactory.weights = initializer; }
	
	/**
	 * Creates an initial weight based on set strategy.
	 * 
	 * @param input
	 * @return
	 */
	public static double createWeight() {	return weights.createWeight(); }
	
}
