public interface LiveList<T> : Iterable<T> {
	~this;
	
	get length as usize;
	
	indexOf(value as T) as usize?;
	
	//[IndexIterator]
	//for (index as int, value as T);
	
	[](index as usize) as T;
	
	addListener(listener as Listener<T>) as ListenerHandle<T>`unique;
	
	public interface Listener<T> {
		onInserted(index as usize, value as T) as void;
		onChanged(index as usize, oldValue as T, newValue as T) as void;
		onRemoved(index as usize, oldValue as T) as void;
	}
	alias ListenerHandle<T> as listeners.ListenerHandle<Listener<T>>;
}
