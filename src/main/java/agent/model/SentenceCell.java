package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TypeReader;
import util.TypeWriter;

public class SentenceCell extends Cell {
	protected List<Cell> children = new ArrayList<>();

	int length = 0;

	public int valueIndex = 0;

	public SentenceCell() {
	}

	public SentenceCell(int index) {
		super(index);
	}

	public Cell comeFrom(Cell child) {
		this.children.add(child);
		length += child.getLength();
		return this;
	}

	@Override
	public List<Link> getChildren() {
		throw new RuntimeException();
	}

	public int getLength() {
		return length;
	}

	@Override
	public List<Link> getParents() {
		throw new RuntimeException();
	}

	public void load(Engine engine, TypeReader v) throws IOException {
		int index;
		v.readString();

		int count = v.readInt();
		for (int i = 0; i < count; i++) {
			index = v.readInt();
			if ((index & 1) > 0) {
				index = index >> 1 + Engine.BASE_LENGTH;
			} else {
				index = index >> 1;
			}
			Cell from = engine.getCells().get(index);
			children.add(from);
		}
		v.clearReader();
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		int index;
		v.save(this.toString());

		v.save(children.size());
		for (Cell child : children) {
			index = child.valueIndex;
			if (index >= Engine.BASE_LENGTH) {
				index = (index - Engine.BASE_LENGTH) << 1 + 1;
			} else {
				index = index << 1;
			}
			v.save(index);
		}
		v.clearWrite();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (Cell child : this.children) {
			sb.append(child.toString());
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void usedBy(Cell cell) {
	}
}
