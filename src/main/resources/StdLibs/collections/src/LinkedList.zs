public class LinkedList<T> {
	var first as Node?;
	var last as Node?;
	var size as usize : get;
	
	public get isEmpty as bool
		=> first == null;
	
	public add(value as T) as void {
		if first == null {
			first = last = new Node(value);
		} else {
			val node = new Node(value);
			last.next = node;
			node.prev = last;
			last = node;
		}
		size++;
	}
	
	public clear() as void {
		first = last = null;
		size = 0;
	}
	
	[Precondition(ENFORCE, index < size, "Index out of bounds")]
	public [](index as usize) as T {
		var node = first;
		while index > 0 && node != null
			node = node.next;
		
		if node == null
			panic "index out of bounds";
		
		return node.value;
	}
	
	public implements Queue<T> {
		[Precondition(ENFORCE, first != null, "Cannot poll an empty queue")]
		poll() as T {
			val result = first.value;
			first = first.next;
			if first == null
				last = null;
			else
				first.prev = null;
				
			size--;
		}
		
		peek() as T? {
			return first == null ? null : first.value;
		}
		
		offer(value as T) as void
			=> add(value);
	}
	
	private struct Node {
		var next as Node?;
		var prev as Node?;
		val value as T;
		
		this(value as T) {
			this.value = value;
		}
	}
}
