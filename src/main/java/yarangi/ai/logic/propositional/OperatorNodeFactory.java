package yarangi.ai.logic.propositional;
/**
 * Provides a set of factory objects for prettier operator node creation.
 *
 * @author hazai
 */
public abstract class OperatorNodeFactory 
{
	public static final int UNARY = 1;
	public static final int BINARY = 2;
	
	public static final OperatorNodeFactory IMPLICATION_FACTORY = new OperatorNodeFactory(BINARY) {
		public BoolSentenceNode create(BoolSentenceNode ... nodes) { return new ImplicationOperatorNode(nodes[0], nodes[1]); }
	};
		
	public static final OperatorNodeFactory EQUIVALENCE_FACTORY = new OperatorNodeFactory(BINARY) {
		public BoolSentenceNode create(BoolSentenceNode ... nodes) { return new EquivalenceOperatorNode(nodes[0], nodes[1]); }
	};
		
	public static final OperatorNodeFactory NEGATION_FACTORY    = new OperatorNodeFactory(UNARY)  {
		public BoolSentenceNode create(BoolSentenceNode ... nodes) { return new NegationOperatorNode(nodes[0]); }
	};
		
	public static final OperatorNodeFactory CONJUNCTION_FACTORY = new OperatorNodeFactory(BINARY) {
		public BoolSentenceNode create(BoolSentenceNode ... nodes) { return new ConjunctionOperatorNode(nodes[0], nodes[1]); }
	};
		
	public static final OperatorNodeFactory DISJUNCTION_FACTORY = new OperatorNodeFactory(BINARY) {
		public BoolSentenceNode create(BoolSentenceNode  ... nodes) { return new DisjunctionOperatorNode(nodes[0], nodes[1]); }
	};
	
	private int arity;
	
	protected OperatorNodeFactory(int arity) { this.arity = arity; }

	
	public abstract BoolSentenceNode create(BoolSentenceNode ... nodes);
	public int getArity() { return arity; }
}
