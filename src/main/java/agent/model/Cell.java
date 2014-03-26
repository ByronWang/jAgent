package agent.model;

import java.io.IOException;


public class Cell implements Savable {
	public Cell() {
	}

	public Cell(int index) {
		this.index = index;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Access

	public final Cell getItem(int index) {
		if (this.convex.size() > 0) {
			int i = index;
			for (Link l : this.convex) {
				Cell cell = l.getFrom();
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
		for (Link from : this.convex) {
			length += from.getFrom().getLength();
		}
		return length;
	}

	public int index = 0;
	private java.util.ArrayList<Link> convex = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getConvex() {
		return convex;
	}

	private java.util.ArrayList<Link> concave = new java.util.ArrayList<Link>();

	public final java.util.ArrayList<Link> getConcave() {
		return concave;
	}

	public String getValue() {
		String s = "";
		for (Link l : this.convex) {
			s += l.getFrom().getValue();
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
		v.save(convex.size());

		for (Link l : convex) {
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
			l.setTo(this);
			l.setConvexIndex(i);
			((Savable) l).load(engine, v);
			convex.add(l);
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
		this.concave.add(link);
	}

	public final Cell comeFrom(Cell cell) {
		Link newLink = new Link();
		this.convex.add(newLink);
		newLink.setFrom(cell);
		newLink.setTo(this);
		newLink.setConvexIndex(this.convex.size() - 1);
		newLink.setStrength((short) 1);
		cell.addTos(newLink);
		// cell.reinforce();

		return this;
	}

	public final Cell reinforce() {
		for (Link l : this.convex) {
			l.setStrength((short) (l.getStrength() + 1));
		}
		return this;
	}

	@Override
	public String toString() {
		return this.getValue() + " . " + this.concave.size();
	}
	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#endregion

}
