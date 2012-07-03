package yarangi.ai.nn.numeric;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import yarangi.ai.nn.NeuralNetwork;

/**
 * This class implements the backpropagation neural network data structure, as well as
 *  activation and learning algorithm.
 * @author hazai
 *
 */
public class BackpropNetwork extends NeuralNetwork implements Serializable
{

	private static final long serialVersionUID = 8421544576382305404L;

	/** output neuron layer (layer of linear neurons, with exactly one neuron per output) */
	private CompleteNeuronLayer outputLayer;
	
	/** hidden layers of network */ 
	private LinkedList <CompleteNeuronLayer> layers = new LinkedList <CompleteNeuronLayer> ();
	
	private ArrayInput inputBuffer;
	
	/** 
	 * Create a new backpropagation network with specified number of outputs.
	 * 
	 * @param outN Number of outputs in the resulting network.
	 */
	public BackpropNetwork(int inputsNum, List <NumericAF[]> afs, double [] biases)
	{
		init( inputsNum, afs, biases );
	}
	
	private void init(int inputsNum, List <NumericAF[]> afs, double [] biases)
	{
		inputBuffer = new ArrayInput(inputsNum);
		
		ArrayInput currInput = inputBuffer;
		Iterator <NumericAF[]> it = afs.iterator();
		int idx = 0;
		while(it.hasNext())
		{
			NumericAF[] layerAFs = it.next();
			
			CompleteNeuronLayer currLayer = new CompleteNeuronLayer( layerAFs, currInput, biases[idx] );
			layers.add( currLayer );
			
			currInput = currLayer.getOutput();
			idx ++;
			
		}
		
		outputLayer = layers.getLast();
	}
	
	/**
	 * Creates network based on descriptor, with all biases equal 1
	 * @param descriptor
	 * @param af
	 */
	public BackpropNetwork(int [] descriptor, NumericAF af) 
	{
		List <NumericAF []> afs = new LinkedList <NumericAF []> ();
		double [] biases = new double [descriptor.length-1];
		for(int idx = 1; idx < descriptor.length; idx ++) 
		{
			NumericAF [] afsArr = new NumericAF[descriptor[idx]];
			for(int n = 0; n < afsArr.length; n ++)
				afsArr[n] = af;
			
			afs.add( afsArr );
			biases[idx-1] = 1;
		}
		
		init( descriptor[0], afs, biases );
		
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void activate() 
	{
		for(CompleteNeuronLayer layer : layers) {
			layer.activate();
		}
	}


	/**
	 * {@inheritDoc}
	 */
	public ArrayInput getOutput() {	return outputLayer.getOutput(); }
	
	/** 
	 * This method implements error backpropagation through layers of this network.
	 * 
	 * @param realOutputs Target values.
	 * @param learningRate Weight's learning rate.
	 */
	public void propagateError(double [] realOutputs, double learningRate)
	{
		
		LinkedList <double []> layerDeltas = new LinkedList <double []> ();
		
		
		double [] deltas = new double[realOutputs.length];
		ArrayInput netOutput = this.getOutput();
		
		// calculating error deltas for output layer:
		for(int outputIdx = 0; outputIdx < deltas.length; outputIdx ++)
		{
			double ro = realOutputs[outputIdx]; // expected output value for this neuron 
			NumericNeuron neuron = outputLayer.getNeurons()[outputIdx];
			deltas[outputIdx] = neuron.getAF().derivative(neuron.getSum()) * (ro - netOutput.getValue(outputIdx));
		}
		
		layerDeltas.addFirst(deltas);
		
		// moving backwards through the network and calculating deltas:
		CompleteNeuronLayer currLayer = layers.get(layers.size()-1);
		for(int layerIdx = layers.size() - 2; layerIdx >= 0; layerIdx --)
		{
			CompleteNeuronLayer prevLayer = layers.get(layerIdx);
			
			double [] currDeltas = layerDeltas.getFirst();
			double [] prevDeltas = new double [prevLayer.getNeurons().length];
			
//			ArrayInput inputs = ((ArrayInput) currLayer.getInputs());
//			ArrayInput inputs = ((ArrayInput) prevLayer.getOutput());
			for(int prevNeuronIdx = 0; prevNeuronIdx < prevLayer.getNeurons().length; prevNeuronIdx ++)
			{
				NumericNeuron neuron = prevLayer.getNeurons()[prevNeuronIdx];
				NumericNeuronInput nni = ((NumericNeuronInput)neuron.getOutput());
				double deltaSum = 0;
				for(int neuronIdx = 0; neuronIdx < currLayer.getNeurons().length; neuronIdx ++)
				{
					deltaSum += currDeltas[neuronIdx] * currLayer.getNeurons()[neuronIdx].getWeight( prevNeuronIdx );
				}
				prevDeltas[prevNeuronIdx] = neuron.getAF().derivative(neuron.getSum()) * deltaSum;

			}
			
			layerDeltas.addFirst(prevDeltas);
			currLayer = prevLayer;
		}
		
		// moving forward and applying weight changes:
		for(int layerIdx = 0; layerIdx < layers.size(); layerIdx ++)
		{
			CompleteNeuronLayer layer = layers.get(layerIdx);
			double [] currDeltas = layerDeltas.get(layerIdx);
			
			for(int inputIdx = 0; inputIdx < layer.getInput().size(); inputIdx ++)
			{
				double inputValue = layer.getInput().getValue( inputIdx );
				double step = learningRate /** layer.getAF().derivative(nni.getValue())*/;
				
				for(int neuronIdx = 0; neuronIdx < currDeltas.length; neuronIdx ++)
				{
						NumericNeuron neuron = layer.getNeurons()[neuronIdx];
						double weight = neuron.getWeight(inputIdx) + currDeltas[neuronIdx] * step * inputValue;
						neuron.setWeight(inputIdx, weight);
				}

			}
		}
	}

	public ArrayInput getInput()
	{
		return inputBuffer;
	}

	public int getLayersNum()
	{
		return layers.size();
	}

	
	public int [] createDescriptor() 
	{
		int [] descriptor = new int [getLayersNum() + 1];
		descriptor[0] = inputBuffer.size();
		for(int idx = 0; idx < getLayersNum(); idx ++) {
			descriptor[idx+1] = layers.get(idx).getOutput().size();
		}
		
		return descriptor;
	}
	
	public int [] parseDescriptor(String descriptorStr) {
		String [] parts = descriptorStr.split( "-" );
		int [] descriptor = new int[parts.length];
		for(int idx = 0; idx < parts.length; idx ++)
			descriptor[idx] = Integer.parseInt( parts[idx] );
		
		return descriptor;
	}
	
	public static String toString(int [] descriptor)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("net");
		for(int idx = 0; idx < descriptor.length; idx ++) {
			sb.append("-").append(descriptor[idx]);
		}
		
		return sb.toString(); 

	}
}
