package yarangi.ai.nn.numeric;

import java.io.Serializable;

import yarangi.ai.nn.Input;

/**
 * Modifiable buffer array.
 * Used to store and transfer previous network/layer input 
 * @author dveyarangi
 *
 */
public final class ArrayInput implements Input, Serializable 
{

	private static final long serialVersionUID = -1124329525864412292L;

	private NumericNeuronInput [] inputBuffer;

	public ArrayInput(int size)
	{
		this(new double [size]);
	}
	
	public ArrayInput(double [] array)
	{
		if(array.length <= 0)
			throw new IllegalArgumentException("Input size is too small: " + array.length);
		
		this.inputBuffer = new NumericNeuronInput [ array.length ];
		for(int idx = 0; idx < array.length; idx ++)
			inputBuffer[idx] = new NumericNeuronInput(array[idx]);
	}
	
	public double getValue(int idx) { return inputBuffer[idx].getValue(); }
	
	protected NumericNeuronInput getInput(int idx) { return inputBuffer[idx]; }
	
	public void setValue(int idx, double value) { inputBuffer[idx].setValue( value); }

	protected void set(int idx, NumericNeuronInput output)
	{
		inputBuffer[idx] = output;
	}

	public int size()
	{
		return inputBuffer.length;
	}
	

/*	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(getValue(0));
		for(int idx = 1; idx < size(); idx ++)
			sb.append(", ").append(get(idx));
		sb.append("]");
		return sb.toString();
	}*/
}
