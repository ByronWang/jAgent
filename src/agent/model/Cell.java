package agent.model;

import java.util.List;

import util.Savable;

public abstract class Cell implements Savable {

	public static CharCell newCharCell(int valueIndex) {
		CharCell charCell = new CharCell(valueIndex);
		return charCell;
	}

	public static SentenceCell newSentence() {
		SentenceCell sentence = new SentenceCell();
		return sentence;
	}

	public static SentenceCell newSentence(int valueIndex) {
		SentenceCell sentence = new SentenceCell(valueIndex);
		return sentence;
	}

	public static WordCell newWord() {
		WordCell word = new WordCell();
		return word;
	}

	public static WordCell newWord(int valueIndex) {
		WordCell word = new WordCell(valueIndex);
		return word;
	}

	int length = 0;

	protected int valueIndex = 0;

	protected Cell() {
	}

	protected Cell(int valueIndex) {
		this.valueIndex = valueIndex;
	}

	abstract public List<Link> getChildren();

	public int getLength() {
		return length;
	}

	abstract public List<Link> getParents();

	public int getWeight() {
		return 0;
	}

	public void inc() {
	}

	abstract public void usedBy(Cell cell);

	abstract public String toFormatedString();

	public int getValueIndex() {
		return valueIndex;
	}
}
