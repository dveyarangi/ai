package yarangi.ai.genetic;

public interface ICandidate
{
	
	public double [] perform(double [] input, double [] output);
	
	public double getScore();
	public boolean hasScore();
	
	public double [] getChromosome();
}
