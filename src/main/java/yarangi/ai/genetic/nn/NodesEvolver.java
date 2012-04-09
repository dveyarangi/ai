package yarangi.ai.genetic.nn;

import yarangi.ai.RandomUtil;
import yarangi.ai.genetic.IEvolver;
import yarangi.ai.nn.fast.Serializer;

public class NodesEvolver implements IEvolver
{

	/**
	 * Network layer nodes counter:
	 */
	private int [] networkDescriptor;
	
	private int count = 0;
	
	public NodesEvolver(int [] networkDescriptor) 
	{
		this.networkDescriptor = networkDescriptor;
		for(int layerSize : networkDescriptor)
			count += layerSize;
	}
	
	
	@Override
	public double [] mutate(double[] parent)
	{
		int nodeNum = RandomUtil.getRandomInt( count );
		double [] node = Serializer.getNodeArray(parent, networkDescriptor, nodeNum);
		
		return null;
	}

	@Override
	public double [] crossover(double[] convexParent, double[] concaveParent)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
