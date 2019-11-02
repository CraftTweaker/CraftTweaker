public class InverseLiveBool {
	val source as LiveBool;
	
	public this(source as LiveBool) {
		this.source = source;
	}
	
	public implements LiveBool {
		get value => !source.value;
		
		addListener(listener)
			=> source.addListener((oldVal, newVal) => listener(!oldVal, !newVal));
	}
}
