package agent.model;

import java.util.ArrayList;

import util.Savable;

public abstract class Cell implements Savable {
	public int valueIndex = 0;
	private java.util.ArrayList<Link> parents = new java.util.ArrayList<Link>();
	protected java.util.ArrayList<Link> children = new java.util.ArrayList<Link>();

	abstract public int getLength() ;

	abstract public  Cell comeFrom(Cell child) ;

	public ArrayList<Link> getChildren() {
		return this.children;
	}

	public ArrayList<Link> getParents() {
		return this.parents;
	}


	// public final Cell getChild(int index) {
	// if (this.children.size() > 0) {
	// int i = index;
	// for (Link l : this.children) {
	// Cell cell = l.from;
	// if (cell.getLength() > i) {
	// return cell.getChild(i);
	// } else {
	// i -= cell.getLength();
	// }
	//
	// }
	// } else if (index == 0) {
	// return this;
	// }
	// return null;
	// }
}
