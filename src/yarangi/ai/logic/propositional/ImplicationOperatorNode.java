package yarangi.ai.logic.propositional;

import yarangi.ai.logic.BinaryOperatorNode;

/**
 * Implementation of implication (=>, IF-THEN) operator node.
 * @author hazai
 */
public class ImplicationOperatorNode extends BinaryOperatorNode <Boolean> implements BoolSentenceNode
{

	public ImplicationOperatorNode(BoolSentenceNode left, BoolSentenceNode right) {
		super(left, right);
	}

	@Override
	public Boolean evaluate(Boolean value1, Boolean value2) {
		
		return value1 && !value2 ? false : true;
	}

	public String toString() { return "THEN";}

}
