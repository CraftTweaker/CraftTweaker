[Native("stdlib::Comparable")]
public interface Comparable<T> {
	[Native("compareTo")]
	compareTo(other as T) as int;
}
