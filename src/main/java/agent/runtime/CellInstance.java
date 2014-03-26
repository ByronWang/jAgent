package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public abstract class CellInstance
{
	protected Cell cell = null;
	public final Cell getCell()
	{
		return cell;
	}
	public long signal;
	public int startFrom;

	public CellInstance(Cell cell, long signal, int index)
	{
		this.cell = cell;
		this.signal = signal;
		this.startFrom = index;
	}

	public final void activate(Analyzer analyzer, List<CellInstance> candidate)
	{
		for (Link link : cell.getParents())
		{
			if (link.getOffset() > 0)
			{
				WordInstance w = analyzer.getItem(link.getTo().index);
				if (w != null) // 如果已经激活
				{
					w.act(analyzer, candidate, link, this.startFrom); // ?????
				}
				else
				{
					//analyzer[link.To.index] = this.sibling(link);
				}
			}
		}

		for (Link link : cell.getParents())
		{
			if (link.getOffset() == 0)
			{
				if (link.getTo().getLength() > 1)
				{
					analyzer.setItem(link.getTo().index, this.sibling(link));
				}
				else
				{
					WordInstance w = this.sibling(link);
					candidate.set(this.startFrom, w);
					w.activate(analyzer, candidate);
				}
			}
		}

	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public abstract WordInstance sibling(Link l);
	@Override
	public String toString()
	{
		return this.getCell().getValue().toString() + " : ";
	}
}