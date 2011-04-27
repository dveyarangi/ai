package yarangi.ai.nn.numeric;



/**
 * This is a linear activation function. 
 * 
 * @author Hazai
 *
 */
public final class LinearAF implements NumericAF
{

	private static final long serialVersionUID = -8439875059812463350L;
	
	private double a;

	public LinearAF()
	{
		this(1);
	}
	
	public LinearAF(double a)
	{
		this.a = a;
	}
	
    public double calculate(double sigma) 
    {
        return a*sigma;
    }

	public double derivative(double sigma) 
	{
		return a;
	}
    
}