package agent.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.TypeReader;
import util.TypeWriter;

public class WordCell extends Cell {

	protected List<Link> children = new ArrayList<>();
	private int length = 0;
	private int weight = 0;

	protected List<Link> parents = new ArrayList<>();

	public int valueIndex = 0;

	protected WordCell() {
	}

	protected WordCell(int valueIndex) {
		this.valueIndex = valueIndex;
	}

	public WordCell comeFrom(Cell child) {
		Link newLink = new Link();
		newLink.from = child;
		newLink.to = this;
		newLink.indexInParent = this.children.size();

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
		if(length<1){
			relink();
		}
		return length;
	}

	@Override
	public List<Link> getParents() {
		return this.parents;
	}

	public void load(Engine engine, TypeReader v) throws IOException {
		v.readString(); // value
		weight = v.readInt();
		// convex
		int count = v.readInt();

		for (int i = 0; i < count; i++) {
			Link l = new Link();
			l.to = this;
			l.indexInParent = i;
			// ((Savable) l).load(engine, v);
			l.from = engine.getCells().get(v.readInt());
			children.add(l);
		}
		v.clearReader();
	}

	public void relink(){				
		for (Link link : children) {
			link.from.getParents().add(link);
			length+=link.from.getLength();
		}
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(this.toString());
		v.save(weight);
		// convex
		v.save(children.size());

		for (Link l : children) {
			// ((Savable) l).save(engine, v);
			v.save(l.from.valueIndex);
		}
		v.clearWrite();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		for (Link l : this.children) {
			sb.append(l.from.toString());
		}
		sb.append(']');
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

	@Override
	public void usedBy(Cell cell) {
		this.weight++;
		
		if(this.weight % 100 == 0){
			for (Link l : this.children) {
				List<Link> parents = l.from.getParents();
				for (int i = 0; i < parents.size(); i++) {
					if(parents.get(i).to == this){
						if(i>0 && parents.get(i-1).to.getWeight() < weight){
							Link me = parents.get(i);
							Link him = parents.get(i-1);
							parents.set(i-1, me);
							parents.set(i, him);
						}
						break;
					}
				}
			}
		}
	}

	@Override
	public int getWeight() {
		return this.weight;
	}
}
