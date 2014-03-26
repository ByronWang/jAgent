package agent.model;

import java.io.IOException;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;


public class Cell implements Savable {
	public Cell() {
	}

	public Cell(int index) {
		this.index = index;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Access

	public final Cell getItem(int index) {
		if (this.children.size() > 0) {
			int i = index;
			for (Link l : this.children) {
				Cell cell = l.from;
				if (cell.getLength() > i) {
					return cell.getItem(i);
				} else {
					i -= cell.getLength();
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
			length += from.from.getLength();
		}
		return length;
	}

	public int index = 0;
	private java.util.ArrayList<Link> children = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getConvex() {
		return children;
	}

	private java.util.ArrayList<Link> parents = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getParents() {
		return parents;
	}

	public String getValue() {
		String s = "";
		for (Link l : this.children) {
			s += l.from.getValue();
		}
		return s;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Savable Members

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(this.getValue());

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
			l.to=this;
			l.offset=i;
			((Savable) l).load(engine, v);
			children.add(l);
		}
		v.clearReader();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Logic

	public final void addTos(Link link) {
		// for (int i = 0; i < concave.Count; i++)
		// {
		// if(link.ConvexIndex < concave[i].ConvexIndex || link.ConvexIndex
		// }
		this.parents.add(link);
	}

	public final Cell comeFrom(Cell cell) {
		Link newLink = new Link();
		this.children.add(newLink);
		newLink.from=cell;
		newLink.to=this;
		newLink.offset=this.children.size() - 1;
		newLink.weight=(short) 1;
		cell.addTos(newLink);
		// cell.reinforce();

		return this;
	}

	public final Cell reinforce() {
		for (Link l : this.children) {
			l.weight++;
		}
		return this;
	}

	@Override
	public String toString() {
		return this.getValue() + " . " + this.parents.size();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

}
