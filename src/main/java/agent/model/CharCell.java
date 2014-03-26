package agent.model;

public class CharCell extends Cell {
	public int valueIndex = 0;
	
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
}
