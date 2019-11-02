[Native("stdlib::String")]
public expand string {
	[Native("fromAsciiBytes")]
	public static fromAsciiBytes(data as byte[]) as string;
	
	[Native("fromUTF8Bytes")]
	public static fromUTF8Bytes(data as byte[]) as string;
	
	[Native("contains")]
	public const in(c as char) as bool
		=> indexOf(c) != null;
	
	[Native("indexOf")]
	public const indexOf(c as char) as usize? {
		for i in 0 .. length {
			if this[i] == c
				return i;
		}
		
		return null;
	}
	
	[Native("indexOfFrom")]
	public const indexOf(c as char, from as usize) as usize? {
		for i in from .. length {
			if this[i] == c
				return i;
		}
		
		return null;
	}
	
	[Native("lastIndexOf")]
	public const lastIndexOf(c as char) as usize? {
		var i = length;
		while i > 0 {
			i--;
			if this[i] == c
				return i;
		}
		
		return null;
	}
	
	[Native("lastIndexOfFrom")]
	public const lastIndexOf(c as char, until as usize) as usize? {
		var i = until;
		while i > 0 {
			i--;
			if this[i] == c
				return i;
		}
		
		return null;
	}
	
	[Native("split")]
	public const split(delimiter as char) as string[] {
		val result = new List<string>();
		var start = 0 as usize;
		for i in 0 .. this.length {
			if this[i] == delimiter {
				result.add(this[start .. i]);
				start = i + 1;
			}
		}
		result.add(this[start .. $]);
		return result as string[];
	}
	
	[Native("trim")]
	public const trim() as string {
		var from = 0 as usize;
		while from < this.length && this[from] in [' ', '\t', '\r', '\n']
			from++;
		var to = this.length;
		while to > 0 && this[to - 1] in [' ', '\t', '\r', '\n']
			to--;
		
		return to < from ? "" : this[from .. to];
	}
	
	[Native("lpad")]
	public const lpad(length as usize, c as char) as string
		=> this.length >= length ? this : c.times(length - this.length) + this;
	
	[Native("rpad")]
	public const rpad(length as usize, c as char) as string
		=> this.length >= length ? this : this + c.times(length - this.length);
		
	[Native("toAsciiBytes")]
	public const toAsciiBytes() as byte[]`unique;
	
	[Native("toUTF8Bytes")]
	public const toUTF8Bytes() as byte[]`unique;
}
