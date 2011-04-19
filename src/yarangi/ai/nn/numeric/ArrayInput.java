package yarangi.ai.nn.numeric;

import java.util.Vector;

import yarangi.ai.nn.Input;

public final class ArrayInput extends Vector <NumericNeuronInput> implements Input 
{

	private static final long serialVersionUID = -1124329525864412292L;


	public ArrayInput(int size)
	{
		super(size);
	}
	
	public ArrayInput(double [] array)
	{
		super(array.length);
		for(int idx = 0; idx < array.length; idx ++)
			add(idx, new NumericNeuronInput(array[idx]));
	}
	
	public double getValue(int idx) { return get(idx).getValue(); }
	
	public NumericNeuronInput getInput(int idx) { return get(idx); }
	
	public void setValue(int idx, double value) { get(idx).setValue(value); }
	

	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("[").append(getValue(0));
		for(int idx = 1; idx < size(); idx ++)
			sb.append(", ").append(get(idx));
		sb.append("]");
		return sb.toString();
	}
}
