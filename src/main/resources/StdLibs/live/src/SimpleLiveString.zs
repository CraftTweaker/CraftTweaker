import listeners.ListenerList;

public class SimpleLiveString {
	val listeners = new ListenerList<LiveString.Listener>;
	
	var value as string : get;
	
	public this(value as string) {
		this.value = value;
	}
	
	public implements MutableLiveString {
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
