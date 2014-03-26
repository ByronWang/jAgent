package agent.model;

public class Link {
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

	public final int getOffset() {
		return offset;
	}

	public final short getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return this.to.toString() + " " + this.offset;
	}
}
