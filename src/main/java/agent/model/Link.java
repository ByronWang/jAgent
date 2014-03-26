package agent.model;

import java.io.IOException;

import util.DataWriter;
import util.DateReader;
import util.Savable;

public class Link implements Savable {
	private short weight = 0;
	private Cell from = null;
	private Cell to = null;
	private int offset = 0;

	public Link(Cell from, Cell to, int offset, short weight) {
		super();
		this.from = from;
		this.to = to;
		this.offset = offset;
		this.weight = weight;
	}

	public final Cell getTo() {
		return to;
	}

	public final void setTo(Cell value) {
		to = value;
	}

	public final Cell getFrom() {
		return from;
	}

	public final void setFrom(Cell value) {
		from = value;
	}

	public final int getConvexIndex() {
		return offset;
	}

	public final void setConvexIndex(int value) {
		offset = value;
	}

	public final short getWeight() {
		return weight;
	}

	public final void setWeight(short value) {
		weight = value;
	}

	public void save(Engine engine, DataWriter v) throws IOException {
		// v.save(strength);
//		v.save(from.index);
	}

	public void load(Engine engine, DateReader v) throws IOException {
		// strength = v.readShort();
//		from = engine.getCells().get(v.readInt());
	}

	@Override
	public String toString() {
		return this.to.toString() + " " + this.offset;
	}
}
