package yarangi.ai.logic;

/**
 * Implementation of operator node for binary operators.
 * Requires to implement the operator function. 
 * 
 * @author hazai
 *
 * @param <V>
 */
public abstract class BinaryOperatorNode <V> extends OperatorNode <V> 
{
	/** left operand */
	public ISentenceNode <V> left;
	/** right operand */
	public ISentenceNode <V> right;
	
	public BinaryOperatorNode(ISentenceNode <V> left, ISentenceNode <V> right)
	{
		this.left = left;
		this.right = right;
	}
	public V evaluate()
	{
		return evaluate(left.evaluate(), right.evaluate());
	}
	
	/**
	 * Operator logic is implemented here.
	 * @return operation result
	 */
	public abstract V evaluate(V value1, V value2);

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
	
		left.print(offset + 1); 
		right.print(offset + 1); 
	}

}
