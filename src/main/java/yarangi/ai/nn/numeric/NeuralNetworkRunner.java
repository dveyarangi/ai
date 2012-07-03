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
	
	private String name;
	private BackpropNetwork nn;
	private Normalizer normalizer;
	
	private static Logger log = Logger.getLogger(NeuralNetworkRunner.class);
	
	private static final long SAVE_EACH = 10000;
	private long iteration = 0;
	
	public NeuralNetworkRunner(String name, Normalizer normalizer, BackpropNetwork nn)
	{
		this(name);
		
		this.nn = nn;
		this.normalizer = normalizer;
	}

	public NeuralNetworkRunner(String name)
	{
		this.name = name;
		this.normalizer = new TransparentNormalizer();
	}
	
	protected BackpropNetwork getNetwork() {
		return nn;
	}

	/**
	 * Sets network
	 * @param nn
	 */
	public void setNetwork(BackpropNetwork nn) {
		this.nn = nn;
	}

	/**
	 * @return Network input/output normalization strategy
	 */
	public Normalizer getNormalizer() {
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
		
		double [] ninputs = normalizer == null ? inputArray : normalizer.normalizeInput(inputArray);
		
		ArrayInput input = nn.getInput();
		for(int iIdx = 0; iIdx < ninputs.length; iIdx ++)
			input.setValue(iIdx, ninputs[iIdx]);
		
		nn.activate();
		
		ArrayInput output = nn.getOutput();
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
		double [] output = toOutputArray( realOutput );
		if(normalizer != null)
			output = normalizer.normalizeOutput( output );
		nn.propagateError(output, getLearningRate());
		

	}
	
	/**
	 * Loads network from file.
	 * TODO: some metainfo is required here
	 * @param descriptor2 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static BackpropNetwork load(int[] descriptor, String suffix)
	{
		
		BackpropNetwork bpn;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(createNetworkFilename( descriptor, suffix )));
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
	public String save()
	{
		int [] descriptor = nn.createDescriptor();
		String filename = createNetworkFilename(descriptor, name);
		
		ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(new FileOutputStream(filename));
			os.writeObject(nn);
			os.flush();
			
		} 
		catch (FileNotFoundException e) { log.error(e); } 
		catch (IOException e) { log.error(e); }
		finally { try {	os.close();	} catch (IOException e) { log.error(e); } 
		}
		
		return filename;
	}

	protected static String createNetworkFilename(int [] descriptor, String suffix)
	{
		return BackpropNetwork.toString( descriptor ) + "." + suffix;
	}
	
	protected abstract double getLearningRate();

}
