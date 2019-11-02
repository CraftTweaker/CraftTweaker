import listeners.DummyListenerHandle;

public class ImmutableLiveString {
	val value as string : get;
	
	public this(value as string) {
		this.value = value;
	}
	
	public implements LiveString {
		addListener(listener) => new DummyListenerHandle<LiveString.Listener>`unique(listener);
	}
}
