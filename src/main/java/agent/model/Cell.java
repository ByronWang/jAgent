package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.DataWriter;
import util.DateReader;
import util.Savable;

public class Cell implements Savable {
	private List<Link> parents = new ArrayList<Link>();
	private List<Link> children = new ArrayList<Link>();
	public int index = 0;

	public Cell() {
	}

	public Cell(int index) {
		this.index = index;
	}

//	public final void addTos(Link link) {
//		// for (int i = 0; i < concave.Count; i++)
//		// {
//		// if(link.ConvexIndex < concave[i].ConvexIndex || link.ConvexIndex
//		// }
//		this.toCells.add(link);
//	}

	public final Cell comeFrom(Cell from) {
		Link newLink = new Link(from, this, this.children.size() - 1, (short) 1);
		from.parents.add(newLink);
		this.children.add(newLink);
		return this;
	}

	// 凹
	public final List<Link> getParents() {
		return parents;
	}

	// 凸
	public final List<Link> getChildren() {
		return children;
	}

	public final Cell getChild(int index) {
		if (this.children.size() > 0) {
			int i = index;
			for (Link l : this.children) {
				Cell from = l.getFrom();
				if (from.getLength() > i) {
					return from.getChild(i);
				} else {
					i -= from.getLength();
				}

			}
		} else if (index == 0) {
			return this;
		}
		return null;
	}

	public int getLength() {
		int length = 0;
		for (Link from : this.children) {
			length += from.getFrom().getLength();
		}
		return length;
	}

	public void load(Engine engine, DateReader v) throws IOException {
		v.readString(); // value

		// convex
		int count = v.readInt();

		for (int i = 0; i < count; i++) {
			Cell from = engine.getCells().get(v.readInt());
			Link newLink = new Link(from, this, i, (short) 1);
			from.parents.add(newLink);
			children.add(newLink);
		}
		v.clearReader();
	}

	public void save(Engine engine, DataWriter v) throws IOException {
		v.save(this.toString());
		// convex
		v.save(children.size());
		for (Link l : children) {
			v.save(l.getFrom().index);
		}
		v.clearWrite();
	}

	// public final Cell reinforce() {
	// for (Link l : this.fromCells) {
	// l.setWeight((short) (l.getWeight() + 1));
	// }
	// return this;
	// }
	//

	@Override
	public String toString() {
		String s = "";
		for (Link l : this.children) {
			s += l.getFrom().toString();
		}
		return s;
	}
}
