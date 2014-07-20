package hr.fer.zemris.java.filechecking.syntax.nodes;

import hr.fer.zemris.java.filechecking.syntax.nodes.visitors.NodeVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji služi kao čvor sintaksnog stabla
 * Sadrži listu argumenata i listu čvorova. Obje mogu biti prazne.
 * Sadrži i boolean varijablu koja treba biti postavljena ako je 
 * to čvor koji radi neku provjeru i želi se dobiti obrnuta provjera.
 * @author Ivan Krpelnik
 *
 */
public abstract class Node {

	private List<Node> children;
	private boolean reversed;
	
	public Node() {
		this.children = new ArrayList<>();
	}
	
	public Node(List<Node> children) {
		this.children = children;
	}
	
	public Node(List<Node> children, boolean reversed) {
		this(children);
		this.reversed = reversed;
	}
	
	public void pushNode(Node e) {
		children.add(e);
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void setReverse(boolean reversed) {
		this.reversed = reversed;
	}
	
	public boolean isReversed() {
		return reversed;
	}
	
	@Override
	public String toString() {
		return "::Reversed: " + reversed;
	}
	
	public abstract boolean accept(NodeVisitor visitor);
	
}
