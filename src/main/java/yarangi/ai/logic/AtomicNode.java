package yarangi.ai.logic;

/**
 * Represents a generic terminator node in expression sentence tree.
 * 
 * @author hazai
 *
 * @param <V>
 */
public abstract class AtomicNode <V> implements ISentenceNode <V>
{
	private String var;
	private V value;
	
	/**
	 * Creates node using variable name.
	 * @param varName
	 */
	public AtomicNode(String varName) 
	{ 
		this.var = varName;
	}	

	/** 
	 * Evaluation is straightforward. 
	 * State model must provide a value before evaluation is invoked.
	 */
	public V evaluate() { return value; }

	/**
	 * Sets value.
	 * @param value
	 */
	public void setValue(V value) { this.value = value; }
	
	public void print(int offset) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < offset; i ++)
			sb.append(" ");
		
		sb.append(toString());
		
		System.out.println(sb);
	}
	
	public String toString() { return var + ":" + value;}
	
}
