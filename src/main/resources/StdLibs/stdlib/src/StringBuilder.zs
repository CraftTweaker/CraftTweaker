[Native("stdlib::StringBuilder")]
public class StringBuilder {
	[Native("constructor")]
	public extern this();
	[Native("constructorWithCapacity")]
	public extern this(capacity as usize);
	[Native("constructorWithValue")]
	public extern this(value as string);
	
	[Native("isEmpty")]
	public extern get isEmpty as bool;
	[Native("length")]
	public extern get length as usize;
	
	[Native("appendBool")]
	public extern <<(value as bool) as StringBuilder;
	[Native("appendByte")]
	public extern <<(value as byte) as StringBuilder;
	[Native("appendSByte")]
	public extern <<(value as sbyte) as StringBuilder;
	[Native("appendShort")]
	public extern <<(value as short) as StringBuilder;
	[Native("appendUShort")]
	public extern <<(value as ushort) as StringBuilder;
	[Native("appendInt")]
	public extern <<(value as int) as StringBuilder;
	[Native("appendUInt")]
	public extern <<(value as uint) as StringBuilder;
	[Native("appendLong")]
	public extern <<(value as long) as StringBuilder;
	[Native("appendULong")]
	public extern <<(value as ulong) as StringBuilder;
	[Native("appendUSize")]
	public extern <<(value as usize) as StringBuilder;
	[Native("appendFloat")]
	public extern <<(value as float) as StringBuilder;
	[Native("appendDouble")]
	public extern <<(value as double) as StringBuilder;
	[Native("appendChar")]
	public extern <<(value as char) as StringBuilder;
	[Native("appendString")]
	public extern <<(value as string) as StringBuilder;
	
	public <<(value as StringBuildable`borrow) as StringBuilder {
		value.toString(this);
		return this;
	}
	
	public append<T : StringBuildable>(values as T[]`borrow, separator as string) as StringBuilder {
		for i, value in values {
			if i > 0
				this << separator;
			value.toString(this);
		}
		return this;
	}
	
	public append<T>(values as T[]`borrow, stringer as function(value as T) as string, separator as string) as StringBuilder {
		for i, value in values {
			if i > 0
				this << separator;
			this << stringer(value);
		}
		return this;
	}
	
	[Native("asString")]
	public extern implicit as string;
}
