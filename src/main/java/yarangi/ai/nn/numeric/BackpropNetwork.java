package yarangi.ai.nn.numeric;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import yarangi.ai.nn.Input;
import yarangi.ai.nn.NeuralNetwork;

/**
 * This class implements the backpropagation neural network data structure, as well as activation and learning algorithm.
 * @author hazai
 *
 */
public class BackpropNetwork extends NeuralNetwork implements Serializable
{

	private static final long serialVersionUID = 8421544576382305404L;

	/** output neuron layer (layer of linear neurons, with exactly one neuron per output) */
	public CompleteNeuronLayer outputLayer;
	
	/** hidden layers of network */ 
	public LinkedList <CompleteNeuronLayer> layers = new LinkedList <CompleteNeuronLayer> ();
	
	/** 
	 * Create a new backpropagation network with specified number of outputs.
	 * 
	 * @param outN Number of outputs in the resulting network.
	 */
	public BackpropNetwork(int outN)
	{
		outputLayer = new CompleteNeuronLayer(outN, new TransparentAF(), 0);
		layers.add(outputLayer);
	}

	/**
	 * {@inheritDoc}
	 */
	public synchronized void activate() 
	{
		for(CompleteNeuronLayer layer : layers)
			layer.activate();
	}
	
	/** 
	 * Adds a neuron layer to the network. The layer is added as input layer.
	 * 
	 * @param layer Layer to add as the first layer in the network.
	 */
	public void addLayer(CompleteNeuronLayer layer) 
	{
		layers.getFirst().addInput(layer.getOutput());
		layers.addFirst(layer); 
	}

	/** 
	 * {@inheritDoc}
	 */
	public void addInput(Input array) { layers.getFirst().addInput(array); }

	/**
	 * {@inheritDoc}
	 */
	public Collection<? extends Input> getInputs() { return layers.getFirst().getInputs(); }

	/**
	 * {@inheritDoc}
	 */
	public Input getOutput() {	return outputLayer.getOutput(); }
	
	/** 
	 * This method implements error backpropagation throw layers of this network.
	 * 
	 * @param realOutputs Target values.
	 * @param learningRate Weight's learning rate.
	 */
	public void propagateError(double [] realOutputs, double learningRate)
	{
		
		LinkedList <double []> layerDeltas = new LinkedList <double []> ();
		
		
		double [] deltas = new double[realOutputs.length];
		ArrayInput netOutput = (ArrayInput) this.getOutput();
		
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
					deltaSum += currDeltas[neuronIdx] * currLayer.getNeurons()[neuronIdx].getWeight(nni);
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
			
			for(int inputIdx = 0; inputIdx < layer.getInputs().size(); inputIdx ++)
			{
				NumericNeuronInput nni = ((ArrayInput)layer.getInputs()).getInput(inputIdx);
				double step = learningRate /** layer.getAF().derivative(nni.getValue())*/;
				
				for(int neuronIdx = 0; neuronIdx < currDeltas.length; neuronIdx ++)
				{
						NumericNeuron neuron = layer.getNeurons()[neuronIdx];
						double weight = neuron.getWeight(nni) + currDeltas[neuronIdx] * step * nni.getValue();
						neuron.setWeight(nni, weight);
				}

			}
		}
	}
}
