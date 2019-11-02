public class DummyListenerHandle<T> {
	val listener as T : get;
	
	public this(listener as T) {
		this.listener = listener;
	}
	
	public implements ListenerHandle<T> {}
}
