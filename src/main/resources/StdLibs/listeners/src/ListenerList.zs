public class ListenerList<T> {
	public const PRIORITY_HIGH = 100;
	public const PRIORITY_DEFAULT = 0;
	public const PRIORITY_LOW = -100;
	
	var first as EventListenerNode`borrow? = null;
	var last as EventListenerNode`borrow? = null;
	
	public add(listener as T) as ListenerHandle<T>`unique
		=> add(listener, PRIORITY_DEFAULT);
	
	public add(listener as T, priority as int) as ListenerHandle<T>`unique {
		val node = new EventListenerNode`unique(listener, priority);
		
		if first == null {
			first = last = node;
		} else {
			// prioritized list: where to insert?
			var previousNode = last;
			while previousNode != null && priority > previousNode.priority
				previousNode = previousNode.prev;
			
			if previousNode == null {
				node.next = first;
				first.prev = previousNode;
				first = node;
			} else {
				if previousNode.next == null
					last = node;
				else
					previousNode.next.prev = node;
				
				previousNode.next = node;
				node.prev = previousNode;
			}
		}
		
		return node;
	}
	
	public clear() as void {
		first = last = null;
	}
	
	public accept(consumer as function(listener as T) as void) as void {
		var current = first;
		while current != null {
			consumer(current.listener);
			current = current.next;
		}
	}
	
	public get isEmpty => first == null;
	
	private class EventListenerNode {
		val listener as T : get;
		val priority as int;
		var next as EventListenerNode`borrow? = null;
		var prev as EventListenerNode`borrow? = null;
		
		public this(listener as T, priority as int) {
			this.listener = listener;
			this.priority = priority;
		}
		
		~this {
			if prev == null
				first = next;
			else
				prev.next = next;
			
			if next == null
				last = prev;
			else
				next.prev = prev;
		}
		
		public implements ListenerHandle<T> {}
	}
}