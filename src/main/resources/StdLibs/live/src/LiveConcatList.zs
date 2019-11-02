import listeners.ListenerList;
import listeners.ListenerHandle;

public class LiveConcatList<T> {
	val listeners = new ListenerList<LiveList<T>.Listener<T>>;
	val a as LiveList<T>;
	val b as LiveList<T>;
	
	val aListener as ListenerHandle<LiveList<T>.Listener<T>>`unique;
	val bListener as ListenerHandle<LiveList<T>.Listener<T>>`unique;
	
	public this(a as LiveList<T>, b as LiveList<T>) {
		this.a = a;
		this.b = b;
		
		aListener = a.addListener(new FirstListListener());
		bListener = b.addListener(new SecondListListener());
	}
	
	public implements LiveList<T> {
		indexOf(value) {
			var result = a.indexOf(value);
			if result != null
				return result;
			
			result = b.indexOf(value);
			return result == null ? null : result + a.length;
		}
		
		iterate() => new ConcatIterator;
		
		get length => a.length + b.length;
		
		[](index) => index < a.length ? a[index] : b[index - a.length];
		
		addListener(listener) => listeners.add(listener);
	}
	
	private class ConcatIterator {
		var firstList = true;
		var iterator as Iterator<T>;
		
		public this() {
			iterator = a.iterate();
			if !iterator.hasNext {
				firstList = false;
				iterator = b.iterate();
			}
		}
		
		public implements Iterator<T> {
			get hasNext => iterator.hasNext;
			
			next() {
				val result = iterator.next();
				if firstList && !iterator.hasNext {
					firstList = false;
					iterator = b.iterator();
				}
				return result;
			}
		}
	}
	
	private class FirstListListener {
		public implements LiveList<T>.Listener<T> {
			onInserted(index, value)
				=> listeners.accept(listener => listener.onInserted(index, value));
			onChanged(index, oldValue, newValue)
				=> listeners.accept(listener => listener.onChanged(index, oldValue, newValue));
			onRemoved(index, oldValue)
				=> listeners.accept(listener => listener.onRemoved(index, oldValue));
		}
	}
	
	private class SecondListListener {
		public implements LiveList<T>.Listener<T> {
			onInserted(index, value)
				=> listeners.accept(listener => listener.onInserted(a.length + index, value));
			onChanged(index, oldValue, newValue)
				=> listeners.accept(listener => listener.onChanged(a.length + index, oldValue, newValue));
			onRemoved(index, oldValue)
				=> listeners.accept(listener => listener.onRemoved(a.length + index, oldValue));
		}
	}
}
