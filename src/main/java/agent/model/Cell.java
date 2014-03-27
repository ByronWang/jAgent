package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;

public class Cell implements Savable {
	enum CellType{
		Char,
		Word,
		Sentence		
	}
	CellType type = CellType.Char;
	
	public static Cell newCharCell(int valueIndex) {
		Cell charCell = new CharCell(valueIndex);
		charCell.parents = new ArrayList<>();
		charCell.type = CellType.Char;
		return charCell;
	}

	public static Cell newSentence() {
		Cell sentence = new Cell();
		sentence.children = new java.util.ArrayList<Link>();
		sentence.parents = new ArrayList<>();
		sentence.type = CellType.Sentence;
		return sentence;
	}

	public static Cell newWord() {
		Cell word = new Cell();
		word.children = new ArrayList<>();
		word.parents = new ArrayList<>();
		word.type = CellType.Word;
		return word;
	}

	public static Cell newWord(int valueIndex) {
		Cell word = new Cell(valueIndex);
		word.children = new ArrayList<>();
		word.parents = new ArrayList<>();
		word.type = CellType.Word;
		return word;
	}

	protected List<Link> children = null;
	protected List<Link> parents = null;

	int length = 0;

	public int valueIndex = 0;

	protected Cell() {
	}

	protected Cell(int valueIndex) {
		this.valueIndex = valueIndex;
	}

	public final Cell comeFrom(Cell child) {
		Link newLink = new Link();
		newLink.from = child;
		newLink.to = this;
		newLink.indexInParent = this.children.size();
		newLink.weight = 1;

		this.children.add(newLink);
		child.getParents().add(newLink);
		// cell.reinforce();
		length += child.getLength();
		return this;
	}

	public List<Link> getChildren() {
		return this.children;
	}

	public int getLength() {
		return length;
	}

	public List<Link> getParents() {
		return this.parents;
	}

	public void load(Engine engine, TypeReader v) throws IOException {
		v.readString(); // value
		// convex
		int count = v.readInt();

		for (int i = 0; i < count; i++) {
			Link l = new Link();
			l.to = this;
			l.indexInParent = i;
//			((Savable) l).load(engine, v);
			l.from = engine.getCells().get(v.readInt());
			children.add(l);
		}
		v.clearReader();
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(this.toString());

		// convex
		v.save(children.size());

		for (Link l : children) {		
//			((Savable) l).save(engine, v);
			v.save(l.from.valueIndex);
		}
		v.clearWrite();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (this.type== CellType.Word) {
			sb.append('[');
			for (Link l : this.children) {
				sb.append(l.from.toString());
			}
			sb.append(']');
		} else {
			sb.append('{');
			for (Link l : this.children) {
				sb.append(l.from.toString());
			}
			sb.append('}');
		}
		return sb.toString();
	}
	// public final Cell getChild(int index) {
	// if (this.children.size() > 0) {
	// int i = index;
	// for (Link l : this.children) {
	// Cell cell = l.from;
	// if (cell.getLength() > i) {
	// return cell.getChild(i);
	// } else {
	// i -= cell.getLength();
	// }
	//
	// }
	// } else if (index == 0) {
	// return this;
	// }
	// return null;
	// }
}
