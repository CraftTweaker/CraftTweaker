import listeners.ListenerList;

public class SimpleLiveBool {
	val listeners = new ListenerList<LiveBool.Listener>;
	
	var value as bool : get;
	
	public this(value as bool) {
		this.value = value;
	}
	
	public implements MutableLiveBool {
		addListener(listener) => listeners.add(listener);
		
		set value {
			if $ == this.value
				return;
			
			val oldValue = $value;
			$value = $;
			listeners.accept(listener => listener(oldValue, $value));
		}
	}
}
