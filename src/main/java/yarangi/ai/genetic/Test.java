package yarangi.ai.genetic;

import yarangi.ai.genetic.nn.NNCandidate;
import yarangi.ai.genetic.nn.NNEvolutionStrategy;
import yarangi.ai.genetic.nn.NodesEvolver;
import yarangi.ai.nn.fast.FullLayer;
import yarangi.ai.nn.fast.FullNetwork;
import yarangi.ai.nn.fast.NetworkDescriptor;
import yarangi.ai.nn.fast.Serializer;
import yarangi.ai.nn.numeric.TanHAF;

public class Test
{
	public static void main(String ... args)
	{ 
		NetworkDescriptor descriptor = new NetworkDescriptor(  new TanHAF(), new int [] {10, 10, 2, 1} );
	
		NNEvolutionStrategy strategy = new SinEvolution( descriptor );
		
		Pool <NNCandidate> pool = new Pool <NNCandidate> (strategy, 300);
		
		
		while(true) {
			pool.step();
		}
	}
	
	
	private static class SinEvolution extends NNEvolutionStrategy
	{
		
		private double [] points;
		private double [] values;

		public SinEvolution(NetworkDescriptor descriptor)
		{
			super( descriptor );
			
			double step = 0.01;
			double span = 3;
			int samples = (int)Math.round( 2*span/step)+1;
			points = new double [samples];
			values = new double [samples];
			int idx = 0;
			for(double p = -3; p <= 3; p += 0.01) {
				points[idx] = p;
				values[idx] = Math.sin( p );
				idx ++;
			}
		}

		@Override
		public double evaluateCandidate(NNCandidate candidate)
		{
			double [] input = new double [1];
			double [] output = new double [1];
			for(int idx = 0; idx < points.length; idx ++) 
			{
				input[0] = points[idx];
				output[0] = points[idx];
				
				candidate.perform( input, output );
			}
			
			return candidate.getScore();
		}
		
	}


}
