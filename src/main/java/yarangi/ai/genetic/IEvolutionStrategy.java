package yarangi.ai.genetic;


public interface IEvolutionStrategy <C extends ICandidate>
{
	public C createCandidate(Pool <C> pool);
	public void withholdCandidate(C candidate);
	public double evaluateCandidate(C candidate);

}
