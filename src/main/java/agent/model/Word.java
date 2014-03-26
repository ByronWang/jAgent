package agent.model;

import java.io.IOException;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;

public class Word  extends Cell{
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
		child.getParents().add(newLink);
		// cell.reinforce();

		return this;
	}
	

	public int getLength() {
		int length = 0;
		for (Link from : this.children) {
			length += from.from.getLength();
		}
		return length;
	}
	public String toString() {
		String s = "";
		for (Link l : this.children) {
			s += l.from.toString();
		}
		return s;
	}
}
