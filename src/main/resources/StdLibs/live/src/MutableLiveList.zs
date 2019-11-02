public interface MutableLiveList<T> : LiveList<T> {
	add(value as T) as void;
	
	insert(index as usize, value as T) as void;
	
	[]=(index as usize, value as T) as void;
	
	remove(index as usize) as void;
	
	remove(value as T) as void;
	
	clear() as void;
}
