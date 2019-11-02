import listeners.ListenerList;

public class LiveArrayList<T> {
	val values = new List<T>();
	val listeners = new ListenerList<LiveList<T>.Listener<T>>();
	
	public implements MutableLiveList<T> {
		add(value) {
			val index = values.length;
			values.add(value);
			listeners.accept(listener => listener.onInserted(index, value));
		}
		
		insert(index, value) {
			values.insert(index, value);
			listeners.accept(listener => listener.onInserted(index, value));
		}
		
		[]=(index, value) {
			val oldValue = values[index];
			values[index] = value;
			listeners.accept(listener => listener.onChanged(index, oldValue, value));
		}
		
		remove(index as usize) {
			val oldValue = values.remove(index);
			listeners.accept(listener => listener.onRemoved(index, oldValue));
		}
		
		remove(value as T) {
			val index = indexOf(value);
			if index == null
				return;
			
			remove(index);
		}
		
		clear() {
			var i = length;
			while i > 0 {
				i--;
				remove(i);
			}
		}
		
		iterate() => values.iterate();
		indexOf(value) => values.indexOf(value);
		get length => values.length;
		[](index) => values[index];
		addListener(listener) => listeners.add(listener);
	}
}
