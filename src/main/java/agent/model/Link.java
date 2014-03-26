package agent.model;

import java.io.IOException;

public class Link implements Savable
{
	private short strength = 0;
	private Cell to = null;
	private Cell from = null;
	private int offset = 0;

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
	public final int getConvexIndex()
	{
		return offset;
	}
	public final void setConvexIndex(int value)
	{
		offset = value;
	}

	public final short getStrength()
	{
		return strength;
	}
	public final void setStrength(short value)
	{
		strength = value;
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