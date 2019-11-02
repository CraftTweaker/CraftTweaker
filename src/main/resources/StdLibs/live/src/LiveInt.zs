public interface LiveInt {
	get value as int;
	set value as int;
	
	addListener(listener as Listener) as ListenerHandle`unique;
	
	alias Listener as function(oldValue as int, newValue as int) as void;
	alias ListenerHandle as listeners.ListenerHandle<Listener>;
}
