public interface CompactDataInput {
	readBool`mutable() as bool;
	
	readByte`mutable() as byte;
	
	readSByte`mutable() as sbyte;
	
	readShort`mutable() as short;
	
	readUShort`mutable() as ushort;
	
	readInt`mutable() as int;
	
	readUInt`mutable() as uint;
	
	readLong`mutable() as long;
	
	readULong`mutable() as ulong;
	
	readVarInt`mutable() as int;
	
	readVarUInt`mutable() as uint;
	
	readVarLong`mutable() as long;
	
	readVarULong`mutable() as ulong;
	
	readFloat`mutable() as float;
	
	readDouble`mutable() as double;
	
	readChar`mutable() as char;
	
	readString`mutable() as string;
	
	readBytes`mutable() as byte[];
	
	readRawBytes`mutable(length as usize) as byte[];
	
	readBoolArray`mutable() as bool[];
	
	readByteArray`mutable() as byte[];
	
	readSByteArray`mutable() as sbyte[];
	
	readShortArray`mutable() as short[];
	
	readShortArrayRaw`mutable(length as usize) as short[];
	
	readUShortArray`mutable() as ushort[];
	
	readUShortArrayRaw`mutable(length as usize) as ushort[];
	
	readVarIntArray`mutable() as int[];
	
	readVarIntArrayRaw`mutable(length as usize) as int[];
	
	readVarUIntArray`mutable() as uint[];
	
	readVarUIntArrayRaw`mutable(length as usize) as uint[];
	
	readIntArray`mutable() as int[];
	
	readIntArrayRaw`mutable(length as usize) as int[];
	
	readUIntArray`mutable() as uint[];
	
	readUIntArrayRaw`mutable(length as usize) as uint[];
	
	readVarLongArray`mutable() as long[];
	
	readVarLongArrayRaw`mutable(length as usize) as long[];
	
	readVarULongArray`mutable() as ulong[];
	
	readVarULongArrayRaw`mutable(length as usize) as ulong[];
	
	readLongArray`mutable() as long[];
	
	readLongArrayRaw`mutable(length as usize) as long[];
	
	readULongArray`mutable() as ulong[];
	
	readULongArrayRaw`mutable(length as usize) as ulong[];
	
	readFloatArray`mutable() as float[];
	
	readFloatArrayRaw`mutable(length as usize) as float[];
	
	readDoubleArray`mutable() as double[];
	
	readDoubleArrayRaw`mutable(length as usize) as double[];
	
	readStringArray`mutable() as string[];
	
	readStringArrayRaw`mutable(length as usize) as string[];
	
	skip`mutable(bytes as usize) as void;
	
	hasMore`mutable() as bool;
	
	~this;
}
