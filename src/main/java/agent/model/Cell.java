package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Savable;

public class Cell implements Savable {
	private List<Link> concave = new ArrayList<Link>();
	private List<Link> convex = new ArrayList<Link>();
	public int index = 0;

	public Cell() {
	}

	public Cell(int index) {
		this.index = index;
	}
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
		newLink.setWeight((short) 1);
		cell.addTos(newLink);
		// cell.reinforce();

		return this;
	}

	// 凹
	public final List<Link> getConcave() {
		return concave;
	}

	// 凸
	public final List<Link> getConvex() {
		return convex;
	}

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

	public String getValue() {
		String s = "";
		for (Link l : this.convex) {
			s += l.getFrom().getValue();
		}
		return s;
	}
	
	public void load(Engine engine, DateReader v) throws IOException {
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

	public final Cell reinforce() {
		for (Link l : this.convex) {
			l.setWeight((short) (l.getWeight() + 1));
		}
		return this;
	}

	public void save(Engine engine, DataWriter v) throws IOException {
		v.save(this.getValue());

		// convex
		v.save(convex.size());

		for (Link l : convex) {
			((Savable) l).save(engine, v);
		}
		v.clearWrite();
	}

	@Override
	public String toString() {
		return this.getValue() + " . " + this.concave.size();
	}
}
