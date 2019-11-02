[Native("stdlib::Iterator")]
public interface Iterator<T> {
	[Native("hasNext")]
	get hasNext as bool;
	
	[Native("next")]
	next() as T;
}
