package yarangi.ai.nn.fast;

import java.util.ArrayList;

import yarangi.ai.nn.numeric.NumericAF;

public class Serializer
{
	
	public static final double SEP = Double.NEGATIVE_INFINITY;

	private static double [] toArray(FullLayer layer) {
		return layer.getWeights();
	}
	private static FullLayer fromArray(double [] arr, int inputLength) {
		return new FullLayer(arr, inputLength);
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
	
	public static FullNetwork fromArray(double [] arr, int inputLength, NumericAF af)
	{
		FullNetwork network = new FullNetwork( af );
		double [] buffer;
		int start = 0;
		int currInputLength = inputLength;
		for(int offset = 0; offset < arr.length; offset ++)
		{
			if(arr[offset] != SEP)
				continue;
			
			int size = offset - start;
			buffer = new double[size];
			System.arraycopy(arr, start, buffer, 0, size);
			
			FullLayer layer = fromArray( buffer, currInputLength );
			network.addLayer( layer );
			
			currInputLength = layer.getOutput().length; 
			start = offset+1;
		}
		
		return network;
	}
	
	
	public static int [] getDescriptor(FullNetwork network)
	{
		ArrayList <FullLayer> layers = network.getLayers();
		int [] desc = new int [layers.size()+1];
		for(int idx = 0; idx < layers.size(); idx ++) {
			desc[idx] = layers.get( idx ).getInputLendth();
		}
		
		// output size:
		desc[layers.size()] = layers.get( layers.size()-1 ).getOutput().length;
		
		return desc;
	}

	public static double[] getNodeArray(double [] network, int[] networkDescriptor, int nodeNum)
	{
		int nodeOffset = 0;
		int layerOffset = 0;
		
		
		int inputSize, outputSize = inputSize = networkDescriptor[0];
		for(int idx = 1; idx < networkDescriptor.length; idx ++)
		{
			inputSize = outputSize+1;
			outputSize = networkDescriptor[idx];
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
		}
		
		double [] nodeArr = new double [inputSize];
		
		System.arraycopy(network, layerOffset, nodeArr, 0, inputSize);
	
		return nodeArr;
	}

}
