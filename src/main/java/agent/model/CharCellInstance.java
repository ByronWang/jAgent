package agent.model;

public class CharCellInstance extends CellInstance
{

	public CharCellInstance(Cell cell, long signal, int index)
	{
		super(cell, signal, index);
	}

	@Override
	public WordInstance sibling(Link l)
	{
		return new WordInstance(l.getTo(), signal, startFrom, l.getConvexIndex(), startFrom + 1);
	}

	@Override
	public String toString()
	{
		return this.cell.getValue().toString();
	}

}