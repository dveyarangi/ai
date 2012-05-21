package yarangi.ai.nn.numeric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.spinn3r.log5j.Logger;

/**
 * This class encapsulates neural network training and running functionality.
 * 
 * @author hazai
 *
 */
public abstract class NeuralNetworkRunner <I, O> 
{
	private BackpropNetwork nn;
	private Normalizer normalizer;
	
	private static Logger log = Logger.getLogger(NeuralNetworkRunner.class);
	
	private double learningRate;
	
	public NeuralNetworkRunner(Normalizer normalizer, BackpropNetwork nn, double learningRate)
	{
		this(learningRate);
		
		this.nn = nn;
		this.normalizer = normalizer;
	}

	public NeuralNetworkRunner(double learningRate)
	{
		this.learningRate = learningRate;
	}
	
	protected BackpropNetwork getNetwork() {
		return nn;
	}

	/**
	 * Sets network
	 * @param nn
	 */
	protected void setNetwork(BackpropNetwork nn) {
		this.nn = nn;
	}

	/**
	 * @return Network input/output normalization strategy
	 */
	protected Normalizer getNormalizer() {
		return normalizer;
	}

	/**
	 * Defines network input/output normalization strategy.
	 * @param normalizer
	 */
	protected void setNormalizer(Normalizer normalizer) {
		this.normalizer = normalizer;
	}

	/**
	 * Runs network for specified input object and return network output.
	 * @param i
	 * @return
	 */
	public O run(I i)
	{
		return toOutput(run(toInputArray( i )));
	}
	
	/**
	 * Runs the network over specified input array and return nework output values
	 * 
	 * @param inputArray
	 * @return
	 */
	protected double [] run(double [] inputArray)
	{
		
		double [] ninputs = normalizer.normalizeInput(inputArray);
		
		ArrayInput input = (ArrayInput)nn.getInputs();
		for(int iIdx = 0; iIdx < ninputs.length; iIdx ++)
			input.setValue(iIdx, ninputs[iIdx]);
		
		nn.activate();
		
		ArrayInput output = (ArrayInput) nn.getOutput();
		double [] outputs = new double[output.size()];
		for(int oIdx = 0; oIdx < outputs.length; oIdx ++)
			outputs[oIdx] = output.getValue(oIdx);
		
		return normalizer.denormalizeOutput(outputs);
	}
	
	/**
	 * Converts input object to input values array.
	 * @param input
	 * @return
	 */
	protected abstract double [] toInputArray(I input);
	
	/**
	 * Converts output object to output values array.
	 * @param input
	 * @return
	 */
	protected abstract double [] toOutputArray(O input);
	
	/**
	 * Converts output values array to output object.
	 * @param outputs
	 * @return
	 */
	protected abstract O toOutput(double [] outputs);
	
	/**
	 * If inputs array is null, the training will be based on last state of the network.
	 * 
	 * @param inputs
	 * @param outputs
	 * @param learningRate
	 */
	public void train(O realOutput)
	{
		nn.propagateError(normalizer.normalizeOutput(toOutputArray( realOutput )), learningRate);
	}
	
	/**
	 * Loads network from file.
	 * TODO: some metainfo is required here
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static BackpropNetwork load(String filename)
	{
		BackpropNetwork bpn;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			bpn = (BackpropNetwork)ois.readObject();
		} 
		catch (FileNotFoundException e) { throw new RuntimeException(e); } 
		catch (IOException e) { throw new RuntimeException(e); } 
		catch (ClassNotFoundException e) { throw new RuntimeException(e); }
		finally {
			try
			{
				if(ois != null)ois.close();
			} 
			catch ( IOException e ) { throw new RuntimeException(e); }
		}
		
		return bpn;
	}
	
	/**
	 * Stores specified network to file
	 * 
	 * @param net
	 * @param filename
	 */
	public static void save(BackpropNetwork net, String filename)
	{
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(filename));
			os.writeObject(net);
			os.flush();
			
		} 
		catch (FileNotFoundException e) { log.error(e); } 
		catch (IOException e) { log.error(e); }
		finally { try {	os.close();	} catch (IOException e) { log.error(e); } 
		}
	}

}
