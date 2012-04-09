package yarangi.ai.nn.fast;

import yarangi.ai.nn.numeric.TanHAF;

public class Test
{
	public static void main(String ... args) {
		FullNetwork network = new FullNetwork(new TanHAF());
		network.addLayer( new FullLayer(3, 3) );
		network.addLayer( new FullLayer(2, 3) );
		network.addLayer( new FullLayer(1, 2) );
		
		double [] net = Serializer.toArray( network );
		FullNetwork restored = Serializer.fromArray( net, 3, new TanHAF() );

		
		double [] output = restored.activate( new double [] { 1, 0.5, 0} );
		
		int [] desc = Serializer.getDescriptor( network );
		
		double [] nodeArr = Serializer.getNodeArray( net, desc, 2 );
	}

}
