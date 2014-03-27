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
		v.readString(); 
		
		int count = v.readInt();
		for (int i = 0; i < count; i++) {
			Cell from = engine.getCells().get(v.readInt());
			children.add(from);
		}
		v.clearReader();
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(this.toString());
		
		v.save(children.size());
		for (Cell child : children) {
			v.save(child.valueIndex);
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
}
