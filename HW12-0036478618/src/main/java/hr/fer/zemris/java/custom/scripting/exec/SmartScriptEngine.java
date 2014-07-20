package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantDouble;
import hr.fer.zemris.java.custom.scripting.tokens.TokenConstantInteger;
import hr.fer.zemris.java.custom.scripting.tokens.TokenFunction;
import hr.fer.zemris.java.custom.scripting.tokens.TokenOperator;
import hr.fer.zemris.java.custom.scripting.tokens.TokenString;
import hr.fer.zemris.java.custom.scripting.tokens.TokenVariable;
import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SmartScriptEngine {

	private DocumentNode documentNode;
	private RequestContext requestContext;
	private ObjectMultistack multistack = new ObjectMultistack();
	
	private INodeVisitor visitor = new INodeVisitor() {
		
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				throw new RuntimeException("Nije uspijelo zapisivanje u requestContex.", e);
			}
			visitChildren(node);
		}
		
		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String var = node.getVariable().asText();
			multistack.push(var, new ValueWrapper(node.getStartExpression().asText()));
			String end = node.getEndExpression().asText();
			String step = null;
			if(node.getStepExpression() != null) {
				step = node.getStepExpression().asText();
			}
			while(multistack.peek(var).numCompare(end) <= 0) {
				visitChildren(node);
				multistack.peek(var).increment(step);
			}
		}
		
		@Override
		public void visitEchoNode(EchoNode node) {
			ObjectStack stack = new ObjectStack();
			Token[] tokens = node.getTokens();
			for(int i = 0; i < tokens.length; ++i) {
				if(isConstant(tokens[i])) {
					stack.push(tokens[i].asText());
				} else if(tokens[i] instanceof TokenVariable) {
					if(multistack.peek(tokens[i].asText()) == null) {
						throw new IllegalArgumentException("Ne postoji varijabla: " + tokens[i].asText());
					} else {
						stack.push(multistack.peek(tokens[i].asText()).getValue());
					}
				} else if(tokens[i] instanceof TokenOperator) {
					Object drugi = stack.pop();
					ValueWrapper prvi = new ValueWrapper(stack.pop());
					stack.push(odradiOperaciju(tokens[i], drugi, prvi));
				} else if(tokens[i] instanceof TokenFunction) {
					doFunction(tokens[i], stack);
				} else {
					throw new IllegalArgumentException("Nepoznati token: " + tokens[i]);
				}
			}
			writeStackContent(stack);
		}
		
		private void writeStackContent(ObjectStack stack) {
			if(stack.isEmpty()) {
				return;
			}
			Object value = stack.pop();
			writeStackContent(stack);
			try {
				requestContext.write(value.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void doFunction(Token token, ObjectStack stack) {
			String function = token.asText();
			if("sin".equalsIgnoreCase(function)) {
				sinx(stack);
			} else if("decfmt".equalsIgnoreCase(function)) {
				decfmt(stack);
			} else if("dup".equalsIgnoreCase(function)) {
				dup(stack);
			} else if("swap".equalsIgnoreCase(function)) {
				swap(stack);
			} else if("setMimeType".equalsIgnoreCase(function)) {
				setMimeType(stack);
			} else if("paramGet".equalsIgnoreCase(function)) {
				paramGet(stack);
			} else if("pparamGet".equalsIgnoreCase(function)) {
				pparamGet(stack);
			} else if("pparamSet".equalsIgnoreCase(function)) {
				pparamSet(stack);
			} else if("pparamDel".equalsIgnoreCase(function)) {
				pparamDel(stack);
			} else if("tparamGet".equalsIgnoreCase(function)) {
				tparamGet(stack);
			} else if("tparamSet".equalsIgnoreCase(function)) {
				tparamSet(stack);
			} else if("tparamDel".equalsIgnoreCase(function)) {
				tparamDel(stack);
			} else {
				throw new IllegalArgumentException("Nepoznata funkcija: " + function);
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			visitChildren(node);
		}
		
		private boolean isConstant(Token token) {
			return (token instanceof TokenConstantDouble) || (token instanceof TokenConstantInteger)
					|| (token instanceof TokenString);
		}
		
		private void visitChildren(Node node) {
			int size = node.numberOfChildren();
			for(int i = 0; i < size; ++i) {
				node.getChild(i).accept(this);
			}
		}
		

		private Object odradiOperaciju(Token token, Object drugi,
				ValueWrapper prvi) {
			if("+".equals(token.asText())) {
				prvi.increment(drugi);
			} else if("-".equals(token.asText())) {
				prvi.decrement(drugi);
			} else if("*".equals(token.asText())) {
				prvi.multiply(drugi);
			} else if("/".equals(token.asText())) {
				prvi.divide(drugi);
			} else {
				throw new IllegalArgumentException("Nepoznati operator: " + token.asText());
			}
			return prvi.getValue();
		}
		
		private void sinx(ObjectStack stack) {
			double x = Double.parseDouble(stack.pop().toString());
			stack.push(Math.sin(x * Math.PI / 180.));
		}
		
		private void decfmt(ObjectStack stack) {
			NumberFormat df = DecimalFormat.getInstance();
			if(df instanceof DecimalFormat) {
				((DecimalFormat) df).applyPattern((String)stack.pop());
			}
			stack.push(df.format(Double.parseDouble(stack.pop().toString())));
		}
		
		private void dup(ObjectStack stack) {
			stack.push(stack.peek());
		}
		
		private void swap(ObjectStack stack) {
			Object x = stack.pop();
			Object y = stack.pop();
			stack.push(x);
			stack.push(y);
		}
		
		private void setMimeType(ObjectStack stack) {
			requestContext.setMimeType(stack.pop().toString());
		}
		
		private void paramGet(ObjectStack stack) {
			Object dv = stack.pop();
			Object name = stack.pop();
			String value = requestContext.getParameter(name.toString());
			stack.push(value == null ? dv : value);
		}
		
		private void pparamGet(ObjectStack stack) {
			Object dv = stack.pop();
			Object name = stack.pop();
			String value = requestContext.getPersistentParameter(name.toString());
			stack.push(value == null ? dv : value);
		}
		
		private void pparamSet(ObjectStack stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			requestContext.setPersistentParameter(name, value);
		}
		
		private void pparamDel(ObjectStack stack) {
			String name = stack.pop().toString();
			requestContext.removePersistentParameter(name);
		}
		
		private void tparamGet(ObjectStack stack) {
			String dv = stack.pop().toString();
			String name = stack.pop().toString();
			String value = requestContext.getTemporaryParameter(name);
			stack.push(value == null ? dv : value);
		}
		
		private void tparamSet(ObjectStack stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			requestContext.setTemporaryParameter(name, value);
		}
		
		private void tparamDel(ObjectStack stack) {
			String name = stack.pop().toString();
			requestContext.removeTemporaryParameter(name);
		}
	};

	public SmartScriptEngine(DocumentNode documentNode,
			RequestContext requestContext) {
		super();
		if(documentNode == null || requestContext == null) {
			throw new IllegalArgumentException("documentNode and requestContext must not be null");
		}
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}
	
	public void execute() {
		documentNode.accept(visitor);
	}
	
}
