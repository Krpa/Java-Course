package hr.fer.zemris.java.custom.scripting.visitors;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public interface INodeVisitor {

	public void visitTextNode(TextNode node);
	public void visitForLoopNode(ForLoopNode node);
	public void visitEchoNode(EchoNode node);
	public void visitDocumentNode(DocumentNode node);
	
}
