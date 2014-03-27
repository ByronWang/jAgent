package agent.model;


public class Link {
	public Cell from = null;
	public int indexInParent = 0;
	public Cell to = null;
	public int weight = 0;

	@Override
	public String toString() {
		return this.to.toString() + " " + this.indexInParent;
	}
}
