package agent.model;

import java.util.List;

public abstract class CellInstance {
	public Cell cell = null;


	public long signal;
	public int startFrom;

	public CellInstance(Cell cell, long signal, int index) {
		this.cell = cell;
		this.signal = signal;
		this.startFrom = index;
	}

	public final void succeed(Analyzer analyzer, List<CellInstance> candidate) {
		for (Link link : cell.getParents()) {
			if (link.getConvexIndex() > 0) {
				WordInstance w = analyzer.getItem(link.getTo().index);
				if (w != null) {
					w.act(analyzer, candidate, link, this.startFrom); // ?????
				} else {
					// analyzer[link.To.index] = this.sibling(link);
				}
			}
		}

		for (Link link : cell.getParents()) {
			if (link.getConvexIndex() == 0) {
				if (link.getTo().getLength() > 1) {
					analyzer.setItem(link.getTo().index, this.sibling(link));
				} else {
					WordInstance w = this.sibling(link);
					candidate.set(this.startFrom, w);
					w.succeed(analyzer, candidate);
				}
			}
		}

	}

	public abstract WordInstance sibling(Link l);

	@Override
	public String toString() {
		return this.cell.toString().toString() + " : ";
	}
}
