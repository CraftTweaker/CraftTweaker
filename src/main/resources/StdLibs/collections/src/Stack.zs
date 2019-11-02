public class Stack<T> {
	var values as List<T> = new List<T>();
	
	public push(value as T) as void
		=> values.add(value);
	
	public get size as usize
		=> values.length;
	
	[Precondition(ENFORCE, size > 0, "Cannot pop an empty stack")]
	public pop() as T
		=> values.remove(values.length - 1);
	
	public get isEmpty as bool
		=> values.isEmpty;
}
