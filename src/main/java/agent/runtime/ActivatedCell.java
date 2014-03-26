package agent.runtime;

import java.util.List;

import agent.model.Cell;
import agent.model.Link;

public abstract class ActivatedCell
{
	protected Cell value = null;
	public final Cell value()
	{
		return value;
	}
	public long signal;
	public int indexFrom;

	public ActivatedCell(Cell value, long signal, int index)
	{
		this.value = value;
		this.signal = signal;
		this.indexFrom = index;
	}

	public final void activate(Context context, List<ActivatedCell> buffer)
	{
		for (Link link : value.getParents())
		{
			if (link.offset > 0)
			{
				ActivatedWord word = context.getItem(link.to.valueIndex);
				if (word != null) // 如果已经激活,确认是否匹配成功
				{
					word.tryMatch(context, buffer, link, this.indexFrom); // ?????
				}
				else
				{
					//context[link.To.index] = this.sibling(link);
				}
			}
		}

		for (Link link : value.getParents())
		{
			if (link.offset == 0)
			{
				if (link.to.getLength() > 1)
				{
					context.setItem(link.to.valueIndex, this.sibling(link));
				}
				else
				{
					ActivatedWord w = this.sibling(link);
					buffer.set(this.indexFrom, w);
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
		return this.value().toString().toString() + " : ";
	}
}