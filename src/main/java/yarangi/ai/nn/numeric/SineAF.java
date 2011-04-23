package yarangi.ai.nn.numeric;



/**
 * @author Hazai
 */
public class SineAF implements NumericAF
{

    public double calculate(double sigma) 
    {
        return Math.sin(sigma);
    }

	public double derivative(double sigma) 
	{
		return Math.cos(sigma);
	}
    
}