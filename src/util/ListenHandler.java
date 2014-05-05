package util;

import java.util.ArrayList;
import java.util.List;

public abstract class ListenHandler<T> {
	protected List<T> list = new ArrayList<>();
	
	public void add(T t){
		list.add(t);
	}
}
