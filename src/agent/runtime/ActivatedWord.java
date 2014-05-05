package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public class ActivatedWord extends ActivatedCell
{
	private int indexInParent;
	public int indexOfNextInBuffer;
	private int indexOfNextInParent;

	private ActivatedWord next = null;
	private ActivatedWord previous = null;
	public ActivatedWord(Cell cell, long signal, int indexInBuffer, int indexInParent, int indexOfNextCellInBuffer)
	{
		super(cell, signal, indexInBuffer);
		this.indexInParent = indexInParent;
		this.indexOfNextInParent = indexInParent + 1;
		this.indexOfNextInBuffer = indexOfNextCellInBuffer;
	}
	public final void die(Context context)
	{
		if (indexOfNextInParent - indexInParent > 1)
		{
			context.reasign(this.cell, indexInParent, indexOfNextInParent);
		}
	}
	public final ActivatedWord getNext()
	{
		return next;
	}

	public final ActivatedWord getPrevious()
	{
		return previous;
	}

	public final void setNext(ActivatedWord value)
	{
		next = value;
		if(value !=null)
		{
			value.previous = this;
		}
	}

	@Override
	public ActivatedWord sibling(Link l)
	{
		return new ActivatedWord(l.to, signal, indexInBuffer, l.indexInParent, indexOfNextInBuffer);
	}

	@Override
	public String toString()
	{
		return this.cell().toString().toString() + " : " + this.indexOfNextInParent;
	}
	public final void tryMatch(Context context, List<ActivatedCell> buffer, Link l, int indexInBuffer)
	{
		if (this.indexOfNextInBuffer == indexInBuffer && l.indexInParent == this.indexOfNextInParent)
		{
			indexOfNextInParent++;
			this.indexOfNextInBuffer += l.from.getLength();

			// succeed
			if (this.indexInParent == 0 && indexOfNextInParent == this.cell.getChildren().size())
			{
				buffer.set(this.indexInBuffer, this);
				context.setItem(this.cell.getValueIndex(), null);
				this.activate(context, buffer);
			}
		}
		if (this.getNext() != null)
		{
			this.getNext().tryMatch(context, buffer, l, indexInBuffer);
		}
	}
}