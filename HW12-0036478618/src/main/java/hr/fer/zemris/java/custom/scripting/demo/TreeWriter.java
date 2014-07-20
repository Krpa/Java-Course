package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.tokens.Token;
import hr.fer.zemris.java.custom.scripting.visitors.INodeVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TreeWriter {

	public static void main(String[] args) throws IOException {
	
		if(args.length != 1) {
			System.err.println("Očekujem 1 argument, putanju do smart skripte.");
			return;
		}
		
		String docBody = new String (Files.readAllBytes(Paths.get(args[0])), "UTF-8");

		SmartScriptParser p = new SmartScriptParser(docBody);
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
		
	}
	
	private static class WriterVisitor implements INodeVisitor {

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.getText());
			visitChildren(node);
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			StringBuilder sb = new StringBuilder();
			sb.append("{$FOR ");
			sb.append(node.getVariable() + " ");
			sb.append(node.getStartExpression() + " ");
			sb.append(node.getEndExpression() + " ");
			if(node.getStepExpression() != null) {
				sb.append(node.getStepExpression());
			}
			sb.append("$}");
			System.out.print(sb.toString());
			visitChildren(node);
			System.out.println("{$END}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			StringBuilder sb = new StringBuilder();
			sb.append("{$=");
			Token[] tokens = node.getTokens();
			for(int i = 0; i < tokens.length; ++i) {
				sb.append(" " + tokens[i]);
			}
			sb.append("$}");
			System.out.print(sb.toString());
			visitChildren(node);
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			System.out.println("Document:");
			visitChildren(node);
		}
		
		private void visitChildren(Node node) {
			int size = node.numberOfChildren();
			for(int i = 0; i < size; ++i) {
				node.getChild(i).accept(this);
			}
		}
	}
}
