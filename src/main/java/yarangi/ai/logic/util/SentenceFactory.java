package yarangi.ai.logic.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import yarangi.ai.logic.propositional.BoolModel;
import yarangi.ai.logic.propositional.BoolSentenceNode;
import yarangi.ai.logic.propositional.OperatorNodeFactory;

public class SentenceFactory 
{
	
	private static final char LEFT_BRACKET   = "(".toCharArray()[0];
	private static final char RIGHT_BRACKET  = ")".toCharArray()[0];
	
	// operator symbols 
	private static final String CONJUNCTION  = "&";
	private static final String DISJUNCTION  = "|";
	private static final String IMPLICATION  = "=>";
	private static final String EQUIVALENCE  = "<=>";
	private static final String NEGATION     = "!";
	
	/** mapping of operator symbols to factories */
	private static Map <String, OperatorNodeFactory> NODE_FACTORIES = new HashMap <String, OperatorNodeFactory> ();
	static {
		NODE_FACTORIES.put(CONJUNCTION, OperatorNodeFactory.CONJUNCTION_FACTORY);
		NODE_FACTORIES.put(DISJUNCTION, OperatorNodeFactory.DISJUNCTION_FACTORY);
		NODE_FACTORIES.put(IMPLICATION, OperatorNodeFactory.IMPLICATION_FACTORY);
		NODE_FACTORIES.put(EQUIVALENCE, OperatorNodeFactory.EQUIVALENCE_FACTORY);
		NODE_FACTORIES.put(NEGATION,    OperatorNodeFactory.   NEGATION_FACTORY);
	}
	
	/** operator precedence order */
	private static final String [] PRECEDENCE = new String[] { EQUIVALENCE, IMPLICATION, DISJUNCTION, CONJUNCTION, NEGATION };
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TODO: Knowledge base functionality should be separated from parser:
	
	/** KB sentences */
	private List <BoolSentenceNode> sentences = new LinkedList <BoolSentenceNode> ();
	
	/** truth model */
	private BoolModel model = new BoolModel ();
	
	/** @return truth model */
	public BoolModel getModel() { return model; }
	
	/**
	 * Adds a sentence to knowledge base 
	 * @param str
	 * @return
	 */
	public BoolSentenceNode addSentence(String str)
	{
		BoolSentenceNode node = parse(str); 
		sentences.add(node);
		return node;
	}
	
	/**
	 * Parses sentence string.
	 * 
	 * A recursive function. Tries to find weakest operator that is not surrounded by brackets.
	 * If fails, either removes brackets and tries again, or, if not brackets, extracts the content
	 * into variable sentence node.
	 * If operator is found, the string is broken into right and left (if available) parts,
	 * and operator node is created using results of parts parsing.
	 *  
	 * @param expression
	 * @return
	 */
	private BoolSentenceNode parse(String expression)
	{
		String str = expression.trim();
		String s = str; // bitch string

		// used to separate bracketed expressions:
		int brcount = 0;
		
		// used to find the weakest operand:
		int breakPower = -1;
		int breakIdx = -1;
		String breakOperator = null;
		
		// searching for weakest operator:
		int i = 0;
		while(i < str.length())
		{
			char c = s.charAt(0);
			
			
			if(c == LEFT_BRACKET) // going inside brackets
				brcount ++;

			if(c == RIGHT_BRACKET) // going outside brackets
				brcount --;
			
			if(brcount != 0) // we are inside brackets, ignoring the contents:
			{
				s = s.substring(1);
				i ++;
				continue;
				
			}

			// testing if we have an operator here
			// if nothing will be found, just going to next char
			for(int j = 0; j < PRECEDENCE.length; j ++)
			{
				String operator = PRECEDENCE[j];
				if(s.startsWith(operator)) // some operator found
				{
					// checking if it is weaker that previously found
					if(breakOperator == null || breakPower > j) 
					{  // TODO: should be prettier:
						breakIdx = i;
						breakPower = j;
						breakOperator = s.substring(0, operator.length());
					}
					
					// skipping operator chars:
					s = s.substring(operator.length()-1);
					i += operator.length()-1;
					break;
				}
			}
			
			// to the next char:
			i ++;
			s = s.substring(1);
		}
			
		if(brcount != 0) // as it is:
			throw new IllegalArgumentException("Expression syntax error: missing bracket.");
		
		BoolSentenceNode leftOperand = null, rightOperand = null;

		if(breakOperator == null) // no operator found
		{
			// if no operator found, trying to remove global brackets:
			if(str.charAt(0) == LEFT_BRACKET && str.charAt(str.length()-1) == RIGHT_BRACKET)
				return parse(str.substring(1, str.length()-1));

			// if no brackets, taking the whole string as variable symbol:
			// creating atomic node:
			return model.create(str);
		}
		
		if(breakIdx != 0) // if operator is at 0 index, it must be left unary operator
		{   
			// cutting and parsing left part of the expression:
			String leftStr = str.substring(0, breakIdx);
			leftOperand = parse(leftStr);
		}
		
		// cutting and parsing right part of the expression:
		String rightStr = str.substring(breakIdx + breakOperator.length(), str.length());
		rightOperand = parse(rightStr);
		
		// creating operator node:
		OperatorNodeFactory factory = NODE_FACTORIES.get(breakOperator);
		
		if(factory == null) // sanity check, only can happen if NODE_FACTORIES and OP_PRECENDENCE do not match:
			throw new IllegalStateException("Unrecognized operator [" + breakOperator + "].");
		
		switch(factory.getArity()) // adding branches according to operator arity:
		{
		case OperatorNodeFactory.UNARY:  return factory.create(rightOperand); // left-sided unary operator
		case OperatorNodeFactory.BINARY: return factory.create(leftOperand, rightOperand); // middle binary operator
		default:
			throw new IllegalArgumentException("Operator arity [" + factory.getArity() + "] is not supported.");
		}
	}
	
	/**
	 * Implements a brute force truth table scan.
	 * 
	 * @param question
	 * @return
	 */
	public boolean test(BoolSentenceNode question)
	{
		return test(question, getModel(), getModel().getVariables());
	}
	
	private boolean test(BoolSentenceNode question, BoolModel model, SortedSet <String> variables)
	{
		
		if(variables.size() == 0) // all variables are mapped, evaluating:
		{
			// testing question validity first:
			if(!question.evaluate())
				return false;
			// validating knowledge base:
			for(BoolSentenceNode kbel : sentences)
			{
				boolean mid = kbel.evaluate();
				if(!mid) // contradiction in KB
					return false;
			}
			
			// KB agrees with this mapping:
			return true;
		}
		
		// else
		
		// mapping variables:
		for(String var : variables)
		{
			SortedSet <String> subSet = variables.tailSet(var+"\0");

			// checking 'true' for current variable:
			model.apply(var, true);
			if(test(question, model, subSet))
				return true;
			
			// checking 'false' for current variable:
			model.apply(var, false);
			if(test(question, model, subSet))
				return true;
		}
		
		// mapping doesn't satisfy this question:
		return false;
	}
	
	public static void main(String [] arg)
	{
		SentenceFactory factory = new SentenceFactory();
		
//      parser test		
//		BoolSentenceNode node = factory.parse("!A | B<=> (C =>!(D& A)|B&C&A|B");
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// 6.12 (Adapted from (Davis, 1990).) 
		// Jones, Smith, and Clark hold the jobs of programmer, knowledge engineer, and manager (not necessarily in that order). 
		// Jones owes the programmer $10. 
		// The manager's spouse prohibits borrowing money. 
		// Smith is not married. 
		// Your task is to figure out which person has which job.
		
		// Mapping:   
		//          Pro    Eng     Man
		//  Jones   JP     JE      JM
		//  Smith   SP     SE      SM
		//  Clark   CP     CE      CM
		
		// knowledge base:
		
		// ensure that no one has more than one job:
		factory.addSentence(" !(JP&JM) & !(JP&JE) & !(JM&JE) ");
		factory.addSentence(" !(SP&SM) & !(SP&SE) & !(SM&SE) ");
		factory.addSentence(" !(CP&CM) & !(CP&CE) & !(CM&CE) ");
		
		// some thought:
		factory.addSentence("!JP"); // Jones owes the programmer $10 => Jones not programmer
		factory.addSentence("!JM"); // The manager's spouse prohibits borrowing money => Jones not manager
		factory.addSentence("!SM"); // Smith is not married => Smith not manager
		
		// or replace last two:
		// factory.addSentence("JE<=>!SM"); // how this works?
        
		// can be used to validate KB:
		System.out.println("Answer possible: " + factory.test(
				factory.parse("(JP&SE&CM)|(JP&SM&CP)|(JM&SP&CE)|(JM&SE&CP)|(JE&SP&CM)|(JE&SM&CP)")));
		
		// questions
		String [] questions = new String [] {
				"( JP & SE & CM )",
				"( JP & SM & CE )",
				"( JM & SP & CE )",
				"( JM & SE & CP )", 
				"( JE & SP & CM )", 
				"( JE & SM & CP )"
		};
		
//		System.out.println(System.currentTimeMillis());
		for(String q : questions)
			System.out.println(q + ": " + factory.test(factory.parse(q)));
//		System.out.println(System.currentTimeMillis());
	}

}
