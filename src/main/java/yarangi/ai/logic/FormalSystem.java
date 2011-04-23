package yarangi.ai.logic;

/**
 * Formal system interface.
 * Represents a formal system L = L(A, O, Z, I)
 * 
 *  where
 *  
 *  A - set of terminal elements.
 *  O - operator set (U Oj ) where j is operator arity.
 *  Z - set of inference rules.
 *  I - axiom set. 
 * 
 * @author hazai
 *
 */
public class FormalSystem <AtomType>
{
	private AtomType [] atoms;
	
	private IOperator [] operators;
	
	private IInferenceRule [] rules;
	
//	private ISentence [] axioms;
}
