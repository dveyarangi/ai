package yarangi.ai.logic.propositional;

import yarangi.ai.logic.AtomicNode;

/**
 * Used for bitter syntax sugar.
 * @author hazai
 */
public class BoolAtomicNode extends AtomicNode <Boolean> implements BoolSentenceNode
{
	public BoolAtomicNode(String varName) { super(varName); }
}
