[Native("io::StringReader")]
public class StringReader {
	val data as char[];
	var offset as usize;
	
	[Native("constructor")]
	public this(value as string) {
		data = value.characters;
	}
	
	public implements Reader {
		[Native("destructor")]
		~this {}
		
		[Native("readCharacter")]
		read()
			=> offset == data.length ? -1 : data[offset++];
		
		[Native("readArray")]
		read(buffer)
			=> read(buffer, 0, buffer.length);
		
		[Native("readSlice")]
		read(buffer, offset, length) {
			length = usize.min(data.length - this.offset, length);
			data.copyTo(buffer, this.offset, offset, length);
			this.offset += length;
			return length;
		}
	}
}
