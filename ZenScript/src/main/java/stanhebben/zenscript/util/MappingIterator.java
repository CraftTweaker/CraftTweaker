package stanhebben.zenscript.util;

import java.util.Iterator;

/**
 *
 * @author Stan Hebben
 */
public abstract class MappingIterator<T, U> implements Iterator<U> {
	private final Iterator<T> iterator;

	public MappingIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	public abstract U map(T value);

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public U next() {
		return map(iterator.next());
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}
