[Native("io::Reader")]
public interface Reader {
	[Native("destruct")]
	~this;
	
	[Native("readCharacter")]
	read() as int throws IOException;
	
	[Native("readArray")]
	read(buffer as char[]) as usize throws IOException
		=> read(buffer, 0, buffer.length);
	
	[Native("readArraySlice")]
	read(buffer as char[], offset as usize, length as usize) as usize throws IOException;
}
