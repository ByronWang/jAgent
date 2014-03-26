package agent.model;

import java.io.IOException;

import util.Savable;
import util.TypeReader;
import util.TypeWriter;

public class Link implements Savable
{
	public Cell to = null;
	public Cell from = null;
	public int offset = 0;
	public short weight = 0;

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Savable Members

	public void save(Engine engine, TypeWriter v) throws IOException
	{
		//v.save(strength);
		v.save(from.valueIndex);
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