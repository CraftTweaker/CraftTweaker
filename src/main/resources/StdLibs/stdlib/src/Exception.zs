[Native("stdlib::Exception")]
public virtual class Exception {
	[Native("constructor")]
	public this(message as string) {}
	
	[Native("constructorWithCause")]
	public this(message as string, cause as Exception) {}
}
