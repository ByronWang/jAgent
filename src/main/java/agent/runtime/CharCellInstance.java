package agent.runtime;

import agent.model.Cell;
import agent.model.Link;

public class CharCellInstance extends CellInstance
{

	public CharCellInstance(Cell cell, long signal, int index)
	{
		super(cell, signal, index);
	}

	@Override
	public String toString()
	{
		return this.getCell().getValue().toString();
	}

	@Override
	public WordInstance sibling(Link l)
	{
		return new WordInstance(l.to, signal, indexFrom, l.offset, indexFrom + 1);
	}

}