/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.util;

import minetweaker.api.event.IEventHandle;

/**
 *
 * @author Stan
 */
public class EventList<T> {
	private EventNode first = null;
	private EventNode last = null;

	public void clear() {
		first = last = null;
	}

	public IEventHandle add(IEventHandler<T> handler) {
		EventNode node = new EventNode(handler, last, null);

		synchronized (this) {
			if (first == null) {
				first = node;
			}
			if (last != null) {
				last.next = node;
			}
			last = node;
		}

		return node;
	}

	public boolean hasHandlers() {
		return first != null;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public void publish(T event) {
		EventNode current = null;

		synchronized (this) {
			current = first;
		}

		while (current != null) {
			current.handler.handle(event);

			synchronized (this) {
				current = current.next;
			}
		}
	}

	private class EventNode implements IEventHandle {
		private final IEventHandler<T> handler;
		private EventNode next;
		private EventNode prev;

		public EventNode(IEventHandler<T> handler, EventNode prev, EventNode next) {
			this.handler = handler;
			this.prev = prev;
			this.next = next;
		}

		@Override
		public void close() {
			synchronized (EventList.this) {
				if (prev == null) {
					first = next;
				} else {
					prev.next = next;
				}

				if (next == null) {
					last = prev;
				} else {
					next.prev = prev;
				}
			}
		}
	}
}
