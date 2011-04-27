package yarangi.ai.nn;


/**
 * This is a high-level interface that characterizes behavior of neural network elements.
 * Network data structure is combined of Nodes, which can be grouped in custom hierarchy.
 * Neuron, Layer and NeuralNetwork all implementing the Node interface.
 * 
 * This allows a multiple networks to be chained. 
 * @author hazai
 */
public interface INode <I extends IInput>
{
	/** 
	 * Sets an input array for the node.
	 * The Input object's reference will remain unchanged during node lifecycle.
	 * The values for the Input are to be changed using the implementing classes means.
	 */
//	public void setInput(I input);

	
	/**
	 * Return the array of inputs of this node.
	 */
	public I getInput();
	
	/** 
	 * Encapsulates the activation logic for this node. 
	 */
	public abstract void activate();
	
	/**
	 * Retrieves the output array of the node in the form of Input objects' array.
	 * This allows to 
	 */	
	public abstract I getOutput();
}
