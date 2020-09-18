package tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T data = null;
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private Node<T> parent = null;
	private int depth, degree;

	public Node(T data) {
		this.data = data;
		this.depth = calculateDepth();
	}

	@Override
	public String toString() {
		return data.toString() + "|Depth:" + depth + "|Degree:" + degree;
	}

	public String getString() {
		String text = "";
		for (int i = 0; i < depth; i++)
			text += "   ";
		if(getRoot() == this)
			text += "Root";
		else
			text += "> " + data;
		
		text += "|Degree:" + degree;

		return text;
	}

	public Node<T> addChild(Node<T> child) {
		child.setParent(this);
		children.add(child);
		degree = children.size();
		return child;
	}

	public void addChildren(List<Node<T>> children) {
		for (Node<T> child : children)
			child.setParent(this);
		children.addAll(children);
		degree = children.size();
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setParent(Node<T> parent) {
		this.parent = parent;
		updateDepth();
	}

	public Node<T> getParent() {
		return parent;
	}

	public int getDepth() {
		return depth;
	}

	public Node<T> getRoot() {
		if (parent == null)
			return this;
		return parent.getRoot();
	}

	public void deleteChildren() {
		for (Node<T> child : children)
			child.deleteChildren();
		children.clear();
	}

	private int calculateDepth() {
		Node<T> root = getRoot();
		Node<T> currentParent = this;
		int d = 0;
		while (root != currentParent) {
			d++;
			currentParent = currentParent.getParent();
		}
		return d;
	}

	public void updateDepth() {
		for (Node<T> child : children)
			child.updateDepth();
		this.depth = calculateDepth();
	}
}