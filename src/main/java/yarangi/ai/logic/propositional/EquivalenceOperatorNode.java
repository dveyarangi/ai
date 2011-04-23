package yarangi.ai.logic.propositional;

import yarangi.ai.logic.BinaryOperatorNode;

/**
 * Implementation of equivalence (<=>, IFF) operator node.
 * @author hazai
 */
public class EquivalenceOperatorNode extends BinaryOperatorNode <Boolean> implements BoolSentenceNode
{

	public EquivalenceOperatorNode(BoolSentenceNode left, BoolSentenceNode right) {
		super(left, right);
	}

	@Override
	public Boolean evaluate(Boolean value1, Boolean value2) {
		return value1.equals(value2);
	}

	public String toString() { return "IFF";}

}
