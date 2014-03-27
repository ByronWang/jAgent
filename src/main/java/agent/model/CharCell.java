package agent.model;

import java.io.IOException;

import util.TypeReader;
import util.TypeWriter;

public class CharCell extends Cell {
	public CharCell(int index) {
		super(index);
		this.length = 1;
	}

	@Override
	public String toString() {
		return String.valueOf((char) valueIndex);
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
	}

	public void load(Engine engine, TypeReader v) throws IOException {
	}
}
