package agent.model;

import java.util.ArrayList;

//AnalyzerSelectedChangedHandler
//C# TO JAVA CONVERTER TODO TASK: Delegates are not available in Java:
//public delegate void AnalyzerListenHandler(String src, Candidator<CellInstance> candidate);


public class Candidator<T> extends ArrayList<T>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6195178025734185692L;
	
	public final T getItem(int index)
	{
		return this.get(index);
	}
	public final void setItem(int index, T value)
	{
		this.set(index, value);
	}
	public final void Add(T item)
	{
		this.add(item);
	}
	public final void Clear()
	{
		this.clear();
	}
	public final java.util.Iterator<T> GetEnumerator()
	{
		return this.iterator();
	}

	public final int getCount()
	{
		return this.size();
	}
}