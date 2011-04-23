package yarangi.ai.nn.init;

import yarangi.ai.nn.numeric.NumericNeuronInput;

public interface WeightsInitializer 
{
	public abstract double createWeight(NumericNeuronInput input);
	

}
