package yarangi.ai.genetic;

import java.util.List;

public interface IEvolver <C extends ICandidate>
{
	public C mutate(C parent);
	public C crossover(List <C> parents);

}
