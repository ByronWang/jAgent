package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public class WordInstance extends CellInstance
{
	public int nextCandidateIndex;
	private int nextConvexIndex;
	private int offset;

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

	public WordInstance(Cell cell, long signal, int indexFrom, int offset, int nextCandidateIndex)
	{
		super(cell, signal, indexFrom);
		this.offset = offset;
		this.nextConvexIndex = offset + 1;
		this.nextCandidateIndex = nextCandidateIndex;
	}

	public final void act(Analyzer analyzer, List<CellInstance> buffer, Link l, int curIndex)
	{
		if (this.nextCandidateIndex == curIndex && l.getOffset() == this.nextConvexIndex)
		{
			nextConvexIndex++;
			this.nextCandidateIndex += l.getFrom().getLength();

			// succeed
			if (this.offset == 0 && nextConvexIndex == this.cell.getConvex().size())
			{
				buffer.set(this.indexFrom, this);
				analyzer.setItem(this.cell.index, null);
				this.activate(analyzer, buffer);
			}
		}
		if (this.getNext() != null)
		{
			this.getNext().act(analyzer, buffer, l, curIndex);
		}
	}

	public final void die(Analyzer analyzer)
	{
		if (nextConvexIndex - offset > 1)
		{
			analyzer.reasign(this.cell, offset, nextConvexIndex);
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
		return new WordInstance(l.getTo(), signal, indexFrom, l.getOffset(), nextCandidateIndex);
	}
}