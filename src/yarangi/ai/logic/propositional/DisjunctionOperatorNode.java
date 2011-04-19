package yarangi.ai.logic.propositional;

import yarangi.ai.logic.BinaryOperatorNode;

/**
 * Implementation of disjunction (OR) operator node.
 * @author hazai
 */
public class DisjunctionOperatorNode extends BinaryOperatorNode <Boolean> implements BoolSentenceNode
{
	public DisjunctionOperatorNode(BoolSentenceNode left, BoolSentenceNode right) {
		super(left, right);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Boolean evaluate(Boolean value1, Boolean value2) {
		return value1 || value2;
	}
	public String toString() { return "OR";}

}
