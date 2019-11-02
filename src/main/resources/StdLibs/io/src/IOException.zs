[Native("io::IOException")]
public class IOException : Exception {
	[Native("constructor")]
	public this(message as string) {
		super(message);
	}
	
	/*public this(message as string, cause as Exception) {
		super(message, cause);
	}*/
}
