package agent.model;

public class CharCell extends Cell {
	public CharCell(int index) {
		super(index);
	}

	@Override
	public int getLength() {
		return 1;
	}

	@Override
	public String toString() {
		return Character.toString((char) index);
	}
}
