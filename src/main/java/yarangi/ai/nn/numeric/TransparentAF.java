package yarangi.ai.nn.numeric;



/**
 * This is a linear activation function. The weight calculations, which are 
 * performed in NNLayer implementation, will make the adjustments of 
 * line parameters.
 * @author Hazai
 *
 */
public class TransparentAF implements NumericAF
{

    public double calculate(double sigma) 
    {
        return sigma;
    }

	public double derivative(double sigma) 
	{
		return 1.;
	}
    
}