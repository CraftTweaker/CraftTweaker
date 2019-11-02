public interface Queue<T> {
	get empty as bool;
	
	poll() as T;
	peek() as T;
	push(value as T) as void;
}
