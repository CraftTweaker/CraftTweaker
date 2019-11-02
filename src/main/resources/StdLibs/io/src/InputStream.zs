[Native("io::InputStream")]
public interface InputStream {
	[Native("read")]
	read() as int throws IOException;
	
	[Native("readArray")]
	read(array as byte[]) as usize throws IOException;
	
	[Native("readSlice")]
	read(array as byte[], offset as usize, length as usize) as usize throws IOException;
	
	[Native("destruct")]
	~this;
}
