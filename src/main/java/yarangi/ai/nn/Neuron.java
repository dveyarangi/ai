package yarangi.ai.nn;

public abstract class Neuron implements Node 
{
	
	private ActivationFunction af;
	
	public Neuron(ActivationFunction af)
	{
		this.af = af;
	}

}
