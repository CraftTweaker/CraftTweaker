import listeners.DummyListenerHandle;

public class ImmutableLiveBool {
	public const TRUE = new ImmutableLiveBool(true);
	public const FALSE = new ImmutableLiveBool(false);
	
	val value as bool : get;
	
	private this(value as bool) {
		this.value = value;
	}
	
	public implements LiveBool {
		addListener(listener) => new DummyListenerHandle<LiveBool.Listener>`unique(listener);
	}
}
