public interface LiveObject<T> {
	get value as T;
	
	addListener(listener as Listener<T>) as ListenerHandle<T>`unique;
	
	alias Listener<T> as function(oldValue as T, newValue as T) as void;
	alias ListenerHandle<T> as listeners.ListenerHandle<Listener<T>>;
}
