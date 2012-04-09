package yarangi.ai.genetic;

public interface IEvolver
{
	public double [] mutate(double [] parent);
	public double [] crossover(double [] convexParent, double [] concaveParent);
}
