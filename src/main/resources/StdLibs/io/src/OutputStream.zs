[Native("io::OutputStream")]
public interface OutputStream {
	[Native("write")]
	write(value as byte) as void throws IOException;
	
	[Native("writeArray")]
	write(value as byte[]) as void throws IOException;
	
	[Native("writeSlice")]
	write(value as byte[], offset as usize, length as usize) as void throws IOException;
	
	[Native("destruct")]
	~this;
}
