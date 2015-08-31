/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

/**
 *
 * @author Stan
 */
public class CloseablePriorityList<T> implements Iterable<T> {
	private CloseableEntry first;

	/**
	 * Adds a new item to the list. The new item will be inserted at the head of
	 * the list.
	 * 
	 * @param value
	 * @return
	 */
	public Closeable add(T value) {
		if (first == null) {
			first = new CloseableEntry(value);
		} else {
			CloseableEntry entry = new CloseableEntry(value);
			entry.next = first;
			first.prev = entry;
			first = entry;
		}

		return first;
	}

	@Override
	public Iterator<T> iterator() {
		return new MyIterator();
	}

	private class MyIterator implements Iterator<T> {
		private CloseableEntry current = null;

		@Override
		public boolean hasNext() {
			return current == null || current.next != null;
		}

		@Override
		public T next() {
			current = current == null ? first : current.next;
			return current.value;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Not supported yet."); // To
																			// change
																			// body
																			// of
																			// generated
																			// methods,
																			// choose
																			// Tools
																			// |
																			// Templates.
		}
	}

	private class CloseableEntry implements Closeable {
		private final T value;
		private CloseableEntry next;
		private CloseableEntry prev;

		public CloseableEntry(T value) {
			this.value = value;
		}

		@Override
		public void close() throws IOException {
			if (next != null) {
				next.prev = prev;
			}
			if (prev == null) {
				first = next;
			} else {
				prev.next = next;
			}
		}
	}
}
