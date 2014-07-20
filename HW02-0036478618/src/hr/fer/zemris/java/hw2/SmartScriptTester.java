package hr.fer.zemris.java.hw2;

import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Test class for SmartScriptParser.
 * Tries to parse a String via SmartScriptParser.
 * Prints parsed document on standard output if it succeeds,
 * Otherwise prints error message.
 */
public class SmartScriptTester {
	
	public static void main(String[] args) {

		String docBody = "This is sample text. \n{$ FOR i 1 10 1 $} \n"
				+ "This is {$= i $}-th time this message is generated. \n"
				+ "{$END$} \n"
				+ "{$FOR i 0 10 2 $} \n"
				+ "sin({$=i$}^2) = {$= i i * @sin \"0.000\" @decfmt $}\n"
				+ "{$END$}";
//		String docBody = "\\\\";
		SmartScriptParser parser = null;
		try {
		 parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println(e.getMessage());
		 System.out.println("Unable to parse document!");
		 System.exit(-1);
		} catch(Exception e) {
		 System.out.println("If this line ever executes, you have failed this class!");
			throw e;
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody((Node)document);
		System.out.println(originalDocumentBody);
	}

	/**
	 * Returns parsed tree as a String.
	 * @param document Root of a tree
	 * @return String that represents parsed tree.
	 */
	private static String createOriginalDocumentBody(Node document) {
	
		String s = "";
		
		s += document.toString();
		int sz = document.numberOfChildren();
		for(int i = 0; i < sz; ++i)
			s += " " + createOriginalDocumentBody(document.getChild(i));
		if(document instanceof ForLoopNode)
			s += " {$END$}";
		return s;
	}
}
