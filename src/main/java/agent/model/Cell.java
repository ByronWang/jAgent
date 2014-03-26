package agent.model;

import java.io.IOException;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;

public class Cell implements Savable {
	public int valueIndex = -1;

	public Cell() {
	}

	public int getLength() {
		int length = 0;
		for (Link from : this.children) {
			length += from.from.getLength();
		}
		return length;
	}

	private java.util.ArrayList<Link> children = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getChildren() {
		return children;
	}

	private java.util.ArrayList<Link> parents = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getParents() {
		return parents;
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(this.toString());

		// convex
		v.save(children.size());

		for (Link l : children) {
			((Savable) l).save(engine, v);
		}
		v.clearWrite();
	}

	public void load(Engine engine, TypeReader v) throws IOException {
		v.readString(); // value
		// convex
		int count = v.readInt();

		for (int i = 0; i < count; i++) {
			Link l = new Link();
			l.to = this;
			l.offset = i;
			((Savable) l).load(engine, v);
			children.add(l);
		}
		v.clearReader();
	}

	public final Cell comeFrom(Cell child) {
		Link newLink = new Link();
		newLink.from = child;
		newLink.to = this;
		newLink.offset = this.children.size();
		newLink.weight = (short) 1;

		this.children.add(newLink);
		child.parents.add(newLink);
		// cell.reinforce();

		return this;
	}

	public final Cell reinforce() {
		for (Link l : this.children) {
			l.weight++;
		}
		return this;
	}

	public String toString() {
		String s = "";
		for (Link l : this.children) {
			s += l.from.toString();
		}
		return s;
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
