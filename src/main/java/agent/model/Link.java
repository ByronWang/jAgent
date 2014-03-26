package agent.model;

import java.io.IOException;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;

public class Link implements Savable
{
	private Cell to = null;
	private Cell from = null;
	private int offset = 0;
	private short weight = 0;

	public final Cell getTo()
	{
		return to;
	}
	public final void setTo(Cell value)
	{
		to = value;
	}

	public final Cell getFrom()
	{
		return from;
	}
	public final void setFrom(Cell value)
	{
		from = value;
	}
	public final int getOffset()
	{
		return offset;
	}
	public final void setOffset(int value)
	{
		offset = value;
	}

	public final short getWeight()
	{
		return weight;
	}
	public final void setWeight(short value)
	{
		weight = value;
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Savable Members

	public void save(Engine engine, TypeWriter v) throws IOException
	{
		//v.save(strength);
		v.save(from.index);
	}

	public void load(Engine engine, TypeReader v) throws IOException
	{
		//strength = v.readShort();
		from = engine.getCells().get(v.readInt());
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	@Override
	public String toString()
	{
		return this.to.toString() + " " + this.offset;
	}
}