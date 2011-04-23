package yarangi.ai.logic.propositional;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import yarangi.ai.logic.ModelFactory;

/**
 * Implements truth logic model.
 * 
 * @author hazai
 */
public class BoolModel implements ModelFactory <Boolean>
{
	/** registered predicates */
	private SortedSet <String> variables = new TreeSet <String> ();
	
	/** predicate to node mapping */
	private Map <String, BoolAtomicNode> atoms = new HashMap <String, BoolAtomicNode> ();
	
	/** 
	 * Creates a atomic node based on predicate name.
	 *  Nodes that created from the same names are actually same objects.
	 */
	public BoolSentenceNode create(String atom)
	{
		BoolAtomicNode val = atoms.get(atom);
		variables.add(atom);
		if(val == null)
		{
			val = new BoolAtomicNode(atom);
			atoms.put(atom, val);
		}
		
		return val;
	}

	public void apply(String varName, Boolean value) {
		
		BoolAtomicNode val = atoms.get(varName);
		val.setValue(value);
	}
	
	/** exposes variables list */
	public SortedSet <String> getVariables() { return variables; }
}
