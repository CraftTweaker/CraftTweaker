[Native("stdlib::USize")]
public expand usize {
	[Native("toHexString")]
	public extern toHexString() as string;
	
	[Native("min")]
	public static min(a as usize, b as usize) as usize
		=> a < b ? a : b;
	
	[Native("max")]
	public static max(a as usize, b as usize) as usize
		=> a > b ? a : b;
}
