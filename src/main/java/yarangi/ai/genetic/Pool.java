package yarangi.ai.genetic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Pool 
{
	private List <ICandidate> soup = new ArrayList <ICandidate> (); 
	
	private IEvaluator evaluator;
	
	public Pool(IEvaluator evaluator) 
	{
		this.evaluator = evaluator;
	}
	
	public void addCandidate(ICandidate candidate) 
	{
		soup.add( candidate );
	}
	
	public void removeCandidate(ICandidate candidate) 
	{
		soup.remove( candidate );
	}
	
	/**
	 * Picks specified amount of candidates that perform best over specified input.
	 * @param num
	 * @param input
	 * @param realOutput
	 * @return
	 */
	public Collection <ICandidate> pickBest(int num, double [] input, double [] realOutput)
	{
		SortedMap <Double, ICandidate> bestCandidates = new TreeMap <Double, ICandidate> ();
		
		for(ICandidate candidate : soup) 
		{
			double performance = evaluator.evaluate(realOutput, candidate.perform( input ) );
			if(bestCandidates.size() < num || bestCandidates.lastKey() > performance)
			{
				bestCandidates.put( performance, candidate );
				if(bestCandidates.size() > num)
					bestCandidates.remove( bestCandidates.lastKey() );
			}
		}
		
		return bestCandidates.values();
	}
	

}
