package yarangi.ai.genetic;

/**
 * Evaluates candidate performance
 * @author dveyarangi
 *
 */
public interface IEvaluator
{
	public double evaluate(double [] realOutput, double [] candidateOutput);
}
