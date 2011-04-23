package yarangi.ai.logic;

/**
 * Serves both as predicate node factory and logic state model
 * 
 * @author hazai
 *
 * @param <V> - logic material type
 */
public interface ModelFactory <V>
{
	/** 
	 * Creates a sentence node using variable name.
	 * @param varName
	 * @return
	 */
	public ISentenceNode <V> create(String varName);
	
	/**
	 * Applies logical value to predicate.
	 * All sentence tree created using this factory will be assigned with this value.
	 * @param varName
	 * @param value
	 */
	public void apply(String varName, V value);
}
