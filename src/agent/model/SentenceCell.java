package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TypeReader;
import util.TypeWriter;

public class SentenceCell extends Cell {
	protected List<Cell> words = new ArrayList<>();
	public SentenceCell() {
	}

	public SentenceCell(int index) {
		super(index);
	}

	public Cell comeFrom(Cell child) {
		this.words.add(child);
		length += child.getLength();
		child.inc();
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
				index = (index >> 1) + Engine.BASE_LENGTH;
			} else {
				index = index >> 1;
			}
			Cell from = engine.getCells().get(index);
			words.add(from);
		}
		v.clearReader();
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		int index;
		v.save(this.toString());

		v.save(words.size());
		for (Cell child : words) {
			index = child.valueIndex;
			if (index >= Engine.BASE_LENGTH) {
				index =( (index - Engine.BASE_LENGTH) << 1) + 1;
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
		for (Cell child : this.words) {
			sb.append(child.toString());
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public void usedBy(Cell cell) {
	}

	public List<Cell> getWords() {
		return words;
	}

	@Override
	public String toFormatedString() {
		StringBuilder sb = new StringBuilder();
		for (Cell child : this.words) {
			sb.append(child.toFormatedString());
		}
		return sb.toString();
	}
}
