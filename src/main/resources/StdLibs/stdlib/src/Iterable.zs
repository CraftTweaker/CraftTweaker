[Native("stdlib::Iterable")]
public interface Iterable<T> {
	[Native("iterate")]
	iterate() as Iterator<T>;
}
