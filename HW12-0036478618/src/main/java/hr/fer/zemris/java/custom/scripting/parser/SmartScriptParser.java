package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.tokens.*;


/**
 * 
 * @author Ivan Krpelnik
 * 
 * Implementation of a parser.
 * Parser makes a tree structure with DocumentNode as root.
 * Parser expects at least one space after TAG name.
 * These are valid tag names: FOR, END, =.
 * Tag names are case insensitive.
 * Leading and trailing spaces are ignored.
 * Everything outside of tags is considered text.
 * In text \{ is escaped - treated as {.
 * FOR tag must have 3 or 4 expressions. = tag can be empty, but can have any number of expressions.
 * Valid expressions are variables, integers, doubles, operators, functions and strings.
 * Variables must start by letter with zero or more letters, digits or underscores to follow.
 * Operators are: + - * \
 * Functions start with @ followed by Variable expression
 * String must start with \" and end with \"
 * In strings parser accepts following escapes:
 * \\ treats as \
 * \" treats as "
 * \n, \r, \t have it's usual meaning
 * Example of valid FOR tags:
 * 		{$FOR variable_expression start_expression end_expression step_expression $}
 * 		{$FOR variable_expression start_expression end_expression $}
 * 
 * Example of valid END tags:
 * 		{$END$}
 * 		{$       END     $}
 * Example of valid = tags:
 * 		{$= $}
 * 		{$=  first_expression second_expression $}
 * 
 * @throws SmartScriptParserException
 */
public class SmartScriptParser {

	private DocumentNode document;
	private ObjectStack parserStack;
	
	
	/**
	 * Calls parse method.
	 * @param documentBody Document to be parsed
	 * @throws SmartScriptParserException
	 */
	public SmartScriptParser(String documentBody) {
		try {
			parse(documentBody);
		} catch(SmartScriptParserException s) {
			throw s;
		} catch(Exception e) {
			throw new SmartScriptParserException("Document is not valid.", e);
		}
	}
	
	/**
	 * Returns root of parsed text.
	 * @return document
	 */
	public DocumentNode getDocumentNode() {
		return this.document;
	}
	
	/**
	 * Parses String documentBody as a tree structure with DocumentNode document as root element.
	 * @param documentBody
	 */
	private void parse(String documentBody) {
		
		document = new DocumentNode();
		parserStack = new ObjectStack();
		
		parserStack.push(document);
		int i, pocetak = 0, kraj = 0, tekst = 0, j, br;
		int sz = documentBody.length();
		
		for(i = 0; i < sz; ) {
			
			String tekstString = "";
			pocetak = documentBody.indexOf("{$", i);
			
			if(pocetak > 0) {
				if(documentBody.charAt(pocetak - 1) == '\\') {
					pocetak = pocetak + 2;
					i = pocetak;
					continue;
				}
			}
		
			for(j = pocetak + 2, br = 0; pocetak > -1 && j < sz - 1; ++j) {
				if(documentBody.charAt(j) == '\"' && documentBody.charAt(j - 1) != '\\') {
					br++;
				}
				else if(documentBody.charAt(j) == '$' && documentBody.charAt(j+1) == '}' && br % 2 == 0) {
					break;
				}
			}
			
			if(j == sz - 1)
				kraj = -1;
			else
				kraj = j;
			
			if(pocetak != -1 && kraj != -1) {
				
				tekstString = documentBody.substring(tekst, pocetak);
				parseTekst(tekstString);
				
				String tagTekst = "";
				tagTekst = documentBody.substring(pocetak+2, kraj);
				tagTekst = tagTekst.trim();
				int type = typeOfTag(tagTekst);
				
				if(type == 0) {
					String tagString = tagTekst.substring(1);
					parseEcho(tagString);
				}
				
				else if(type == 1) {
					String tagString = tagTekst.substring(3);
					parseFor(tagString);
				}
				else {
					parserStack.pop();
				}
				i = kraj + 2;
				tekst = kraj + 2;
			}
			
			else if(pocetak != -1 && kraj == -1) {
				throw new SmartScriptParserException("Unclosed tag!");
			}
			
			else
				break;
		}
		
		if(parserStack.size() != 1)
			throw new SmartScriptParserException("Invalid number of END tags.");
		
		if(i < sz) {
			String tekstString = documentBody.substring(tekst, sz);
			parseTekst(tekstString);
		}
	}
	
	/**
	 * Parses FOR tag to ForLoopNode.
	 * @param tag String to be parsed
	 */
	
	private void parseFor(String tag) {
		if(tag.length() == 0)
			throw new SmartScriptParserException("Empty FOR tag.");
		
		tag = tag.trim();
		TokenVariable variable;
		Token startExpression;
		Token endExpression;
		Token stepExpression;
		int i = 0;
		int sz = tag.length();
		String temp;
		
		//variable
		i = getTokenSize(tag);
		
		temp = tag.substring(0, i);
		variable = new TokenVariable(temp);
		
		tag = tag.substring(i);
		tag = tag.trim();
		sz = tag.length();
		
		if(sz == 0)
			throw new SmartScriptParserException("FOR - not enough arguments.");
		
		//startExpression
		i = getTokenSize(tag);
		
		temp = tag.substring(0, i);
		startExpression = getToken(temp);
		
		tag = tag.substring(i);
		tag = tag.trim();
		sz = tag.length();

		if(sz == 0)
			throw new SmartScriptParserException("FOR - not enough arguments.");
		
		//endExpression
		i = getTokenSize(tag);
		
		temp = tag.substring(0, i);
		endExpression = getToken(temp);
		
		tag = tag.substring(i);
		tag = tag.trim();
		sz = tag.length();
		
		if(sz == 0) {
			ForLoopNode newNode = new ForLoopNode(variable, startExpression, endExpression, null);
			Node father = (Node)this.parserStack.peek();
			father.addChildNode(newNode);
			parserStack.push(newNode);
			return;
		}
		
		//stepExpression
		i = getTokenSize(tag);
		
		temp = tag.substring(0, i);
		stepExpression = getToken(temp);
		
		tag = tag.substring(i);
		tag = tag.trim();
		sz = tag.length();
		
		if(sz != 0) {
			throw new SmartScriptParserException("FOR - too many arguments.");
		}
		
		ForLoopNode newNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		Node father = (Node)this.parserStack.peek();
		father.addChildNode(newNode);
		parserStack.push(newNode);
	}
	
	/**
	 * Parses = tag to EchoNode.
	 * @param tag String to be parsed
	 */
	private void parseEcho(String tag) {
		
		int i = 0;
		tag = tag.trim();
		int sz = tag.length();
		ArrayBackedIndexedCollection echoArray = new ArrayBackedIndexedCollection();
		
		while(sz > 0) {
			i = getTokenSize(tag);
			String temp = tag.substring(0, i);
			Token token = getToken(temp);
			echoArray.add(token);
			tag = tag.substring(i);
			tag = tag.trim();
			sz = tag.length();
		}
		
		sz = echoArray.size();
		Token[] tokens = new Token[sz];
		for(i = 0; i < sz; ++i)
			tokens[i] = (Token)echoArray.get(i);
		EchoNode newNode = new EchoNode(tokens);
		Node father = (Node)parserStack.peek();
		father.addChildNode(newNode);
	}
	
	/**
	 * Parses text to TextNode.
	 * @param tekstString String to be parsed
	 */
	private void parseTekst(String tekstString) {
		if(tekstString.length() == 0)
			return;
		tekstString = tekstString.replace("\\{", "{");
		TextNode tekst = new TextNode(tekstString);
		((Node)parserStack.peek()).addChildNode(tekst);
	}
	
	/**
	 * Replaces escape characters in String and returns it.
	 * @param string 
	 * @return new String with replaced escape characters
	 */
	private String replaceStringEscapes(String string) {
		String ret = string;
		ret = ret.replace("\\\\", "\\");
		ret = ret.replace("\\\"", "\"");
		ret = ret.replace("\\n", "\n");
		ret = ret.replace("\\r", "\r");
		ret = ret.replace("\\t", "\t");
		return ret;
	}
	
	/**
	 * Checks what type of TAG is a given string.
	 * Returns following values:
	 * 	- 0 if tag is =
	 *  - 1 if tag is FOR
	 *  - 2 if tag is END
	 * @param tag
	 * @return integer value of tag
	 */
	private int typeOfTag(String tag) {
		
		if(tag.isEmpty())
			throw new SmartScriptParserException("Invalid tag.");
		
		else if(tag.length() < 2) {
			if(tag.charAt(0) == '=')
				return 0;
			throw new SmartScriptParserException("Invalid tag.");
		}
		
		else {
			if(tag.charAt(0) == '=')
				return 0;
			if((tag.charAt(0) == 'F' || tag.charAt(0) == 'f') &&
					(tag.charAt(1) == 'O' || tag.charAt(1) == 'o') &&
					(tag.charAt(2) == 'R' || tag.charAt(2) == 'r') && tag.charAt(3) == ' ')
				return 1;
			if((tag.charAt(0) == 'E' || tag.charAt(0) == 'e') &&
					(tag.charAt(1) == 'N' || tag.charAt(1) == 'n') &&
					(tag.charAt(2) == 'D' || tag.charAt(2) == 'd') && tag.length() == 3)
				return 2;
			throw new SmartScriptParserException("Invalid tag.");
		}
	}
	
	/**
	 * Returns integer size of a given Token (expression).
	 * @param s expression to be checked for size
	 * @return size of expression s
	 */
	private int getTokenSize(String s) {
		int i;
		int sz = s.length();
		s = s.trim();
		if(s.charAt(0) == '\"')
			for(i = 1; i < sz && !(s.charAt(i) == '\"' && s.charAt(i-1) != '\\'); ++i);
		else
			for(i = 0; i < sz && !(s.charAt(i) == '\"' || s.charAt(i) == ' ' || s.charAt(i) == '\n' || s.charAt(i) == '\r' || s.charAt(i) == '\t'); ++i);
		if(i > sz || i == 0)
			throw new SmartScriptParserException("Invalid expression.");

		if(s.charAt(0) == '\"')
			i++;
		return i;
	}
	
	/**
	 * Returns adequate Token for expressions s.
	 * @param s expression
	 * @return Token
	 */
	private Token getToken(String s) {
		
		if(isVariable(s)) {
			return new TokenVariable(s);
		}
		
		if(isString(s)) {
			s = replaceStringEscapes(s);
			return new TokenString(s);
		}
		
		if(isFunction(s)) {
			return new TokenFunction(s);
		}
		
		if(isOperator(s)) {
			return new TokenOperator(s);
		}
		
		if(isInteger(s)) {
			return new TokenConstantInteger(Integer.valueOf(s));
		}
		
		if(isDouble(s)) {
			return new TokenConstantDouble(Double.valueOf(s));
		}
		
		throw new SmartScriptParserException("Invalid expression: " + s);
	}
	
	/**
	 * Checks if givens String is Variable token.
	 * @param s String to be checked
	 * @return true if it's Variable, false otherwise
	 */
	private boolean isVariable(String s) {
		int sz = s.length();
		
		if(sz < 1)
			return false;
		if(!isLetter(s.charAt(0)))
			return false;
		
		for(int i = 0; i < sz; ++i)
			if(!isLetter(s.charAt(i)) && s.charAt(i) != '_' && !isDigit(s.charAt(i)))
				return false;
		
		return true;
	}
	
	/**
	 * Checks if given char is letter.
	 * @param c char to be checked
	 * @return true if it's letter, false otherwise
	 */
	private boolean isLetter(char c) {
		if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return true;
		return false;
	}

	/**
	 * Checks if given char is digit.
	 * @param c char to be checked
	 * @return true if it's digit, false otherwise
	 */
	private boolean isDigit(char c) {
		if(c >= '0' && c <= '9')
			return true;
		return false;
	}
	
	/**
	 * Checks if given String is String expression.
	 * @param s String to be checked
	 * @return true if it's string expression, false otherwise
	 */
	private boolean isString(String s) {
		if(s.length() < 2)
			return false;
		
		if(s.charAt(0) == '\"' && s.charAt(s.length() - 1) == '\"')
			return true;
		return false;
	}
	
	/**
	 * Checks if given String is Function expression.
	 * @param s String to be checked
	 * @return true if it's Function expression, false otherwise
	 */
	private boolean isFunction(String s) {
		if(s.length() < 2)
			return false;
		
		if(s.charAt(0) == '@' && isVariable(s.substring(1)))
				return true;
		return false;
	}
	
	/**
	 * Checks if given String is Operator expression.
	 * @param s String to be checked
	 * @return true if it's Operator expression, false otherwise
	 */
	private boolean isOperator(String s) {
		if(s.length() != 1)
			return false;
		
		if(s.charAt(0) == '+' || s.charAt(0) == '-' || s.charAt(0) == '*' || s.charAt(0) == '/')
			return true;
		return false;
	}
	
	/**
	 * Checks if given String is Integer expression.
	 * @param s String to be checked
	 * @return true if it's Integer expression, false otherwise
	 */
	private boolean isInteger(String s) {
		s = s.trim();
		try {
			Integer.valueOf(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if given Double is Double expression.
	 * @param s String to be checked
	 * @return true if it's Double expression, false otherwise
	 */
	private boolean isDouble(String s) {
		s = s.trim();
		try {
			Double.valueOf(s);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}