package yarangi.ai.logic;

/**
 * Implementation of operator node for unary operators.
 * Requires to implement the operator function. 
 * 
 * @author hazai
 *
 * @param <V>
 */
public abstract class UnaryOperatorNode <V> extends OperatorNode <V> 
{
	/** has an only child */
	public ISentenceNode <V> child;
	
	/** 
	 * Requires a child to be created.
	 * @param child
	 */
	public UnaryOperatorNode(ISentenceNode <V> child)
	{
		this.child = child;
	}
	
	public V evaluate()
	{
		return evaluate(child.evaluate());
	}

	/**
	 * Operator logic is implemented here.
	 * @param value 
	 * @return operation result
	 */
	public abstract V evaluate(V value);
	
	/**
	 * Print the node and its successors.
	 */
	public void print(int offset)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < offset; i ++)
			sb.append(" ");
			
		sb.append(this.toString());
		System.out.println(sb);
			
		child.print(offset + 1); 
	}
	
}
