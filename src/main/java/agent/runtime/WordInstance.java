package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public class WordInstance extends CellInstance
{
	public int nextCandidateIndex;
	private int nextConvexIndex;
	private int convexStartIndex;

	private WordInstance next = null;
	private WordInstance previous = null;
	public final WordInstance getNext()
	{
		return next;
	}
	public final void setNext(WordInstance value)
	{
		next = value;
		if(value !=null)
		{
			value.previous = this;
		}
	}
	public final WordInstance getPrevious()
	{
		return previous;
	}

	public WordInstance(Cell cell, long signal, int startFrom, int convexIndex, int nextCandidateIndex)
	{
		super(cell, signal, startFrom);
		this.convexStartIndex = convexIndex;
		this.nextConvexIndex = convexIndex + 1;
		this.nextCandidateIndex = nextCandidateIndex;
	}

	public final void act(Analyzer analyzer, List<CellInstance> candidate, Link l, int srcIndex)
	{
		if (this.nextCandidateIndex == srcIndex && l.getOffset() == this.nextConvexIndex)
		{
			nextConvexIndex++;
			this.nextCandidateIndex += l.getFrom().getLength();

			// succeed
			if (this.convexStartIndex == 0 && nextConvexIndex == this.cell.getConvex().size())
			{
				candidate.set(this.startFrom, this);
				analyzer.setItem(this.cell.index, null);
				this.activate(analyzer, candidate);
			}
		}
		if (this.getNext() != null)
		{
			this.getNext().act(analyzer, candidate, l, srcIndex);
		}
	}

	public final void die(Analyzer analyzer)
	{
		if (nextConvexIndex - convexStartIndex > 1)
		{
			analyzer.reasign(this.cell, convexStartIndex, nextConvexIndex);
		}
	}

	@Override
	public String toString()
	{
		return this.getCell().getValue().toString() + " : " + this.nextConvexIndex;
	}
	@Override
	public WordInstance sibling(Link l)
	{
		return new WordInstance(l.getTo(), signal, startFrom, l.getOffset(), nextCandidateIndex);
	}
}