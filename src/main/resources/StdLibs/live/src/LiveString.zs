public interface LiveString {
	get value as string;
	
	addListener(listener as Listener) as ListenerHandle`unique;
	
	alias Listener as function(oldValue as string, newValue as string) as void;
	alias ListenerHandle as listeners.ListenerHandle<Listener>;
}
