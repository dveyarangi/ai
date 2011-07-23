package yarangi.ai.nn.numeric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.log4j.Logger;;
/**
 * This class encapsulates neural network training and running functionality.
 * 
 * @author hazai
 *
 */
public class NeuralNetworkRunner 
{
	private BackpropNetwork nn;
	private Normalizer normalizer;
	
	private static Logger log = Logger.getLogger(NeuralNetworkRunner.class);
	
	public NeuralNetworkRunner(Normalizer normalizer, BackpropNetwork nn)
	{
		this.nn = nn;
		this.normalizer = normalizer;
//		System.out.println(normalizer.inputSize());
	}

	public NeuralNetworkRunner()
	{

	}
	
	protected BackpropNetwork getNetwork() {
		return nn;
	}


	protected void setNetwork(BackpropNetwork nn) {
		this.nn = nn;
	}


	protected Normalizer getNormalizer() {
		return normalizer;
	}


	protected void setNormalizer(Normalizer normalizer) {
		this.normalizer = normalizer;
	}


	public double[] run(double [] inputs)
	{
		
		double [] ninputs = normalizer.normalizeInput(inputs);
		
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
	 * If inputs array is null, the training will be based on last state of the network.
	 * 
	 * @param inputs
	 * @param outputs
	 * @param learningRate
	 */
	public void train(double [] outputs, double learningRate)
	{
		nn.propagateError(normalizer.normalizeOutput(outputs), learningRate);
	}
	
	public static BackpropNetwork load(String filename) throws Exception
	{
		BackpropNetwork bpn;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			bpn = (BackpropNetwork)ois.readObject();
		} 
		catch (FileNotFoundException e) { throw new Exception(e); } 
		catch (IOException e) { throw new Exception(e); } 
		catch (ClassNotFoundException e) { throw new Exception(e); }
		finally {
			if(ois != null) ois.close();
		}
		
		return bpn;
	}
	
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
