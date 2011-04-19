package yarangi.ai.logic;

/**
 * A generic expression sentence tree node.
 * Not intended to be implemented by package users.
 * 
 * @author hazai
 *
 * @param <V> - logic material type
 */
public interface ISentenceNode <V>
{
	/** sentence evaluation */
	public V evaluate();
	
	/** debugging */
	public void print(int offset);
}
