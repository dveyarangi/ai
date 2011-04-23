package yarangi.ai.nn.numeric;



/**
 * Hyperbolic tangent implementation of activation function.
 * Best suited for numeric approximations.
 * 
 * @author hazai
 */
public class TanHAF implements NumericAF
{

	private static final long serialVersionUID = 8465886779386541646L;

	public double calculate(double sigma) 
    {
        double ex= Math.exp(sigma);
        double emx= Math.exp(-sigma);        
        return (ex - emx) / (ex + emx);
    }

	public double derivative(double sigma) 
	{
		return 1 - Math.pow(calculate(sigma), 2);
	}
    
}