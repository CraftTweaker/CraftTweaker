import listeners.ListenerList;

public class SimpleLiveInt {
	val listeners = new ListenerList<LiveInt.Listener>;
	
	var value as int : get;
	
	public this(value as int) {
		this.value = value;
	}
	
	public implements MutableLiveInt {
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
