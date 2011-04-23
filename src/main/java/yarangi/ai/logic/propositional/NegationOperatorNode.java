package yarangi.ai.logic.propositional;

import yarangi.ai.logic.UnaryOperatorNode;

/**
 * Implementation of negation (NOT) operator node.
 * @author hazai
 */
public class NegationOperatorNode extends UnaryOperatorNode <Boolean> implements BoolSentenceNode 
{

	public NegationOperatorNode(BoolSentenceNode child) {
		super(child);
	}

	public Boolean evaluate(Boolean value) 
	{
		
		return !value;
	}
	
	public String toString() { return "NOT";}

}
