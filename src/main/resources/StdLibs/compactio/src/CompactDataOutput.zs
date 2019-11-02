public interface CompactDataOutput {
	writeBool(value as bool) as void;
	
	writeByte(value as byte) as void;
	
	writeSByte(value as sbyte) as void;
	
	writeShort(value as short) as void;
	
	writeUShort(value as ushort) as void;
	
	writeInt(value as int) as void;
	
	writeUInt(value as uint) as void;
	
	writeLong(value as long) as void;
	
	writeULong(value as ulong) as void;
	
	writeVarInt(value as int) as void;
	
	writeVarUInt(value as uint) as void;
	
	writeVarLong(value as long) as void;
	
	writeVarULong(value as ulong) as void;
	
    writeFloat(value as float) as void;
	
    writeDouble(value as double) as void;
	
	writeChar(value as char) as void;
	
	writeString(value as string) as void;
	
    writeBytes(data as byte[]) as void;
	
	writeBytes(data as byte[], offset as usize, length as usize) as void;
	
	writeRawBytes(value as byte[]) as void;
	
	writeRawBytes(value as byte[], offset as usize, length as usize) as void;
	
	writeBoolArray(data as bool[]) as void;
	
	writeByteArray(data as byte[]) as void;
	
	writeSByteArray(data as sbyte[]) as void;
	
	writeShortArray(data as short[]) as void;
	
	writeShortArrayRaw(data as short[]) as void;
	
	writeUShortArray(data as short[]) as void;
	
	writeUShortArrayRaw(data as short[]) as void;
	
	writeVarIntArray(data as int[]) as void;
	
	writeVarIntArrayRaw(data as int[]) as void;
	
	writeVarUIntArray(data as uint[]) as void;
	
	writeVarUIntArrayRaw(data as uint[]) as void;
	
	writeIntArray(data as int[]) as void;
	
	writeIntArrayRaw(data as int[]) as void;
	
	writeUIntArray(data as uint[]) as void;
	
	writeUIntArrayRaw(data as uint[]) as void;
	
	writeVarLongArray(data as long[]) as void;
	
	writeVarLongArrayRaw(data as long[]) as void;
	
	writeVarULongArray(data as ulong[]) as void;
	
	writeVarULongArrayRaw(data as ulong[]) as void;
	
	writeLongArray(data as long[]) as void;
	
	writeLongArrayRaw(data as long[]) as void;
	
	writeULongArray(data as long[]) as void;
	
	writeULongArrayRaw(data as long[]) as void;
	
	writeFloatArray(data as float[]) as void;
	
	writeFloatArrayRaw(data as float[]) as void;
	
	writeDoubleArray(data as double[]) as void;
	
	writeDoubleArrayRaw(data as double[]) as void;
	
	writeStringArray(data as string[]) as void;
	
	writeStringArrayRaw(data as string[]) as void;
	
	flush() as void;
	
	~this;
}
