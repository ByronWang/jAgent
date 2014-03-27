package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public abstract class ActivatedCell
{
	public int indexInBuffer;
	public long signal;
	protected Cell cell = null;
	
	public ActivatedCell(Cell cell, long signal, int indexInBuffer)
	{
		this.cell = cell;
		this.signal = signal;
		this.indexInBuffer = indexInBuffer;
	}

	public final void activate(Context context, List<ActivatedCell> buffer)
	{
		for (Link link : cell.getParents())
		{
			if (link.indexInParent > 0)
			{
				ActivatedWord word = context.getItem(link.to.valueIndex);
				if (word != null) // 如果已经激活,确认是否匹配成功
				{
					word.tryMatch(context, buffer, link, this.indexInBuffer); // ?????
				}
				else
				{
					//context[link.To.index] = this.sibling(link);
				}
			}
		}

		for (Link link : cell.getParents())
		{
			if (link.indexInParent == 0)
			{
				if (link.to.getLength() > 1)
				{
					context.setItem(link.to.valueIndex, this.sibling(link));
				}
				else
				{
					ActivatedWord w = this.sibling(link);
					buffer.set(this.indexInBuffer, w);
					w.activate(context, buffer);
				}
			}
		}

	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	public abstract ActivatedWord sibling(Link l);

	@Override
	public String toString()
	{
		return this.cell().toString().toString() + " : ";
	}
	public final Cell cell()
	{
		return cell;
	}
}