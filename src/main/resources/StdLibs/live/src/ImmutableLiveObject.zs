import listeners.DummyListenerHandle;

public class ImmutableLiveObject<T> {
	val value as T : get;
	
	public this(value as T) {
		this.value = value;
	}
	
	public implements LiveObject<T> {
		addListener(listener) => new DummyListenerHandle<LiveObject<T>.Listener<T>>`unique(listener);
	}
}
