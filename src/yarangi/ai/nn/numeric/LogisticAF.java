package yarangi.ai.nn.numeric;



/** 
 * Implements a logistic exponent activation function. 
 * Best suited for logic flow approximations.
 * 
 * @author Hazai
 *
 */
public class LogisticAF implements NumericAF
{
	
	private static final long serialVersionUID = -670386687507961426L;
	
	private double k = 1;
	
	public LogisticAF() { }
	
	public LogisticAF(double k) { this.k = k; }

    public double calculate(double sigma) 
    {
        return 1.0 / (1.0 + Math.exp(-k * sigma));
    }

	public double derivative(double sigma) 
	{
        double e = calculate(sigma);        

		return k * e * (1 - e);
	}
    
}