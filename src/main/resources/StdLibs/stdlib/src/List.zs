[Native("stdlib::List")]
public class List<T> {
	[Native("constructor")]
	public this() {}
	
	[Native("add")]
	public add(value as T) as void;
	
	[Native("insert")]
	public insert(index as usize, value as T) as void;
	
	[Native("remove")]
	public remove(value as usize) as T;
	
	[Native("indexOf")]
	public indexOf(value as T) as usize;
	
	[Native("lastIndexOf")]
	public lastIndexOf(value as T) as usize;
	
	[Native("getAtIndex")]
	public [](index as usize) as T;
	
	[Native("setAtIndex")]
	public []=(index as usize, value as T) as T;
	
	[Native("contains")]
	public in(value as T) as bool;
	
	[Native("toArray")]
	public as T[];
	
	[Native("length")]
	public get length as usize;
	
	[Native("isEmpty")]
	public get isEmpty as bool;
	
	public implements Iterable<T> {
		[Native("iterate")]
		iterate();
	}
}
