package yarangi.ai.nn.fast;

import java.util.ArrayList;

import yarangi.ai.nn.numeric.TanHAF;

public class Serializer
{
	
	public static final double SEP = Double.NEGATIVE_INFINITY;

	public static NetworkDescriptor getDescriptor(FullNetwork network)
	{
		ArrayList <FullLayer> layers = network.getLayers();
		int [] desc = new int [layers.size()+1];
//		desc[0] = layers.get( 0 ).getNeuronCount();
		for(int idx = 0; idx < layers.size(); idx ++) {
			desc[idx] = layers.get( idx ).getNeuronCount();
		}
		
		// output size:
		desc[layers.size()] = layers.get( layers.size()-1 ).getOutput().length;
		
		return new NetworkDescriptor( network.getActivationFunction(), desc );
	}
	
	public static FullNetwork createNetwork(NetworkDescriptor descriptor) {
		FullNetwork network = new FullNetwork(descriptor.getActivationFunction());
		
		FullLayer prevLayer = new FullLayer( descriptor.getLayers()[0] );
		
		network.addLayer( prevLayer );
		for(int idx = 1; idx < descriptor.getLayers().length-1; idx ++) {
			
			FullLayer currLayer = new FullLayer( descriptor.getLayers()[idx], prevLayer );
			network.addLayer( currLayer );
			prevLayer = currLayer;
		}
			
		
		return network;
	}
	
	private static double [] toArray(FullLayer layer) {
		return layer.getWeights();
	}
	private static FullLayer fromArray(double [] arr, int nuuronCount) {
		return new FullLayer(arr, nuuronCount);
	
	}
	
	
	
	public static double [] toArray(FullNetwork network) { 
		int count = 0;
		for(FullLayer layer : network.getLayers()) {
			count += toArray(layer).length;
			count ++; // for separator
		}
		
		double [] res = new double [count];
		int offset = 0;
		for(FullLayer layer : network.getLayers()) {
			double [] layerArr = toArray(layer);
			
			System.arraycopy(layerArr, 0, res, offset, layerArr.length);
			offset += layerArr.length;
			res[offset ++] = SEP;
		}
		
		return res;
	}
	
	public static FullNetwork fromArray(double [] arr, NetworkDescriptor descriptor)
	{
		FullNetwork network = new FullNetwork( descriptor.getActivationFunction() );
		double [] buffer;
		int start = 0;
		int descIdx = 0;
		for(int offset = 0; offset < arr.length; offset ++)
		{
			if(arr[offset] != SEP)
				continue;
			int currInputLength = descriptor.getLayers()[descIdx ++];
			
			int size = offset - start;
			buffer = new double[size];
			System.arraycopy(arr, start, buffer, 0, size);
			
			FullLayer layer = fromArray( buffer, currInputLength );
			network.addLayer( layer );
			
			start = offset+1;
		}
		
		return network;
	}
	
	

	public static int[] getNodeIndices(NetworkDescriptor networkDescriptor, int nodeNum)
	{
		int nodeOffset = 0;
		int layerOffset = 0;
		
		
		int inputSize = networkDescriptor.getLayers()[0]-1;
		int outputSize = networkDescriptor.getLayers()[0];
		for(int idx = 0; idx < networkDescriptor.getLayers().length; idx ++)
		{
			outputSize = networkDescriptor.getLayers()[idx];
			nodeOffset += outputSize;
			if(nodeOffset > nodeNum)
			{
				int offset = nodeNum - (nodeOffset - outputSize); 
				layerOffset += (inputSize)*offset;
				break;
			}
			else
				layerOffset += (inputSize)*outputSize + 1;
			//             weights size           separator
			
			inputSize = outputSize;
		}
		
		return new int [] {layerOffset, inputSize};
	}
	
	public static double[] getNodeArray(double [] network, NetworkDescriptor networkDescriptor, int nodeNum)
	{
		int [] nodeIdxs = getNodeIndices( networkDescriptor, nodeNum );
		int nodeOffset = nodeIdxs[0];
		int nodeLength = nodeIdxs[1];
		double [] nodeArr = new double [nodeLength];
		
		System.arraycopy(network, nodeOffset, nodeArr, 0, nodeLength);
	
		return nodeArr;
	}
	
	public static void main(String ... args) {
		FullNetwork network = new FullNetwork(new TanHAF());
		FullLayer l1 = new FullLayer(3);
		FullLayer l2 = new FullLayer(2, l1);
		FullLayer l3 = new FullLayer(1, l2);
		network.addLayer( l1 );
		network.addLayer( l2 );
		network.addLayer( l3 );
		
		
		NetworkDescriptor desc = Serializer.getDescriptor( network );
		double [] net = Serializer.toArray( network );
		FullNetwork restored = Serializer.fromArray( net, desc );

		
		double [] output = restored.activate( new double [] { 1, 0.5, 0} );
		
		double [] nodeArr1 = Serializer.getNodeArray( net, desc, 0 );
		double [] nodeArr2 = Serializer.getNodeArray( net, desc, 1 );
		double [] nodeArr3 = Serializer.getNodeArray( net, desc, 2 );
		double [] nodeArr4 = Serializer.getNodeArray( net, desc, 3 );
		double [] nodeArr5 = Serializer.getNodeArray( net, desc, 4 );
		double [] nodeArr6 = Serializer.getNodeArray( net, desc, 5 );
		double [] nodeArr7 = Serializer.getNodeArray( net, desc, 6 );
		double [] nodeArr8 = Serializer.getNodeArray( net, desc, 7 );
		double [] nodeArr9 = Serializer.getNodeArray( net, desc, 8 );
	}

}
