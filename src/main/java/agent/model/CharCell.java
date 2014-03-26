package agent.model;

import java.io.IOException;

import util.TypeReader;
import util.TypeWriter;

public class CharCell extends Cell {
	public CharCell(int index) {
		this.valueIndex = index;
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public String toString() {
		return Character.toString((char) valueIndex);
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
	}

	public void load(Engine engine, TypeReader v) throws IOException {
	}

	public Cell comeFrom(Cell child) {
		throw new RuntimeException();
	}
}
