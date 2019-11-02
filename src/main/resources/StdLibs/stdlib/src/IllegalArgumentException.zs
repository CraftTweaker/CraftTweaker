[Native("stdlib::IllegalArgumentException")]
public class IllegalArgumentException : Exception {
	[Native("constructor")]
	public this(message as string) {
		super(message);
	}
}
