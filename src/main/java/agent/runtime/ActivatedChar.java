package agent.runtime;

import agent.model.Cell;
import agent.model.Link;

public class ActivatedChar extends ActivatedCell
{

	public ActivatedChar(Cell cell, long signal, int index)
	{
		super(cell, signal, index);
	}

	@Override
	public String toString()
	{
		return this.value().toString().toString();
	}

	@Override
	public ActivatedWord sibling(Link l)
	{
		return new ActivatedWord(l.to, signal, indexFrom, l.offset, indexFrom + 1);
	}

}