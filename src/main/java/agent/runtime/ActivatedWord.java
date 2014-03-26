package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public class ActivatedWord extends ActivatedCell
{
	public int nextCandidateIndex;
	private int nextConvexIndex;
	private int offset;

	private ActivatedWord next = null;
	private ActivatedWord previous = null;
	public final ActivatedWord getNext()
	{
		return next;
	}
	public final void setNext(ActivatedWord value)
	{
		next = value;
		if(value !=null)
		{
			value.previous = this;
		}
	}
	public final ActivatedWord getPrevious()
	{
		return previous;
	}

	public ActivatedWord(Cell cell, long signal, int indexFrom, int offset, int nextCandidateIndex)
	{
		super(cell, signal, indexFrom);
		this.offset = offset;
		this.nextConvexIndex = offset + 1;
		this.nextCandidateIndex = nextCandidateIndex;
	}

	public final void act(Context analyzer, List<ActivatedCell> buffer, Link l, int curIndex)
	{
		if (this.nextCandidateIndex == curIndex && l.offset == this.nextConvexIndex)
		{
			nextConvexIndex++;
			this.nextCandidateIndex += l.from.getLength();

			// succeed
			if (this.offset == 0 && nextConvexIndex == this.value.getChildren().size())
			{
				buffer.set(this.indexFrom, this);
				analyzer.setItem(this.value.valueIndex, null);
				this.activate(analyzer, buffer);
			}
		}
		if (this.getNext() != null)
		{
			this.getNext().act(analyzer, buffer, l, curIndex);
		}
	}

	public final void die(Context context)
	{
		if (nextConvexIndex - offset > 1)
		{
			context.reasign(this.value, offset, nextConvexIndex);
		}
	}

	@Override
	public String toString()
	{
		return this.value().toString().toString() + " : " + this.nextConvexIndex;
	}
	@Override
	public ActivatedWord sibling(Link l)
	{
		return new ActivatedWord(l.to, signal, indexFrom, l.offset, nextCandidateIndex);
	}
}