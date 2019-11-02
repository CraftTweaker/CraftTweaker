[Native("stdlib::Integer")]
public expand int {
	[Native("toHexString")]
	public extern toHexString() as string;
	
	[Native("min")]
	public static min(a as int, b as int) as int
		=> a < b ? a : b;
	
	[Native("max")]
	public static max(a as int, b as int) as int
		=> a > b ? a : b;
}
