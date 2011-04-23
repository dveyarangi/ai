package yarangi.ai.logic.propositional;

import yarangi.ai.logic.BinaryOperatorNode;

/**
 * Implementation of conjunction (AND) operator node.
 * @author hazai
 *
 */
public class ConjunctionOperatorNode extends BinaryOperatorNode <Boolean> implements BoolSentenceNode
{

	public ConjunctionOperatorNode(BoolSentenceNode left, BoolSentenceNode right) {
		super(left, right);
	}

	@Override
	public Boolean evaluate(Boolean value1, Boolean value2) {
		return value1 && value2;
	}
	public String toString() { return "AND";}


}
