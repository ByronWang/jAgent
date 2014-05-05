package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TypeReader;
import util.TypeWriter;

public class CharCell extends Cell {
	final protected List<Link> parents;

	public CharCell(int index) {
		super(index);
		parents = new ArrayList<>();
		this.length = 1;
	}

	@Override
	public List<Link> getChildren() {
		return null;
	}

	@Override
	public List<Link> getParents() {
		return this.parents;
	}

	public void load(Engine engine, TypeReader v) throws IOException {
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
	}

	@Override
	public String toString() {
		return String.valueOf((char) valueIndex);
	}

	@Override
	public void usedBy(Cell cell) {
	}

	@Override
	public String toFormatedString() {
		return String.valueOf((char) valueIndex);
	}
}
