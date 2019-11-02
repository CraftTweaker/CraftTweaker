public class CompactBytesDataInput {
	private const P6 as uint = 1 << 6;
	private const P7 as uint = 1 << 7;
	private const P13 as uint = 1 << 13;
	private const P14 as uint = 1 << 14;
	private const P20 as uint = 1 << 20;
	private const P21 as uint = 1 << 21;
	private const P27 as uint = 1 << 27;
	private const P28 as uint = 1 << 28;
	private const P34 as ulong = 1UL << 34;
	private const P35 as ulong = 1UL << 35;
	private const P41 as ulong = 1UL << 41;
	private const P42 as ulong = 1UL << 42;
	private const P48 as ulong = 1UL << 48;
	private const P49 as ulong = 1UL << 49;
	private const P55 as ulong = 1UL << 55;
	private const P56 as ulong = 1UL << 56;
	
	val data as byte[]`borrow:this;
	var offset as usize : get;
	
	public this(data as byte[]`borrow:this) {
		this.data = data;
		this.offset = 0;
	}
	
	public this(data as byte[]`borrow:this, offset as usize) {
		this.data = data;
		this.offset = offset;
	}
	
	~this {}
	
	public implements CompactDataInput {
		readBool() => readByte() != 0;
		
		readByte() => data[offset++];
		
		readSByte() => data[offset++];
		
		readShort() {
			val b0 = data[offset++] as uint;
			val b1 = data[offset++] as uint;
			return ((b0 << 8) | b1) as short;
		}
		
		readUShort() => readShort();
		
		readInt() {
			val b0 = data[offset++] as int;
			val b1 = data[offset++] as int;
			val b2 = data[offset++] as int;
			val b3 = data[offset++] as int;
			return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    	}
    	
    	readUInt() => readInt();
    	
    	readLong() {
    		val i0 = readUInt() as long;
    		val i1 = readUInt() as long;
    		return (i0 << 32) | i1;
    	}
    	
    	readULong() => readLong();
    	
    	readVarInt() {
    		val value = readVarUInt();
    		return (value & 1) == 0 ? (value >> 1) : -((value >> 1) as int + 1);
		}
		
		readVarUInt() {
			var value = data[offset++] as uint;
			if (value & P7) == 0
				return value;
			
			value = (value & (P7 - 1)) | (data[offset++] as uint) << 7;
			if (value & P14) == 0
				return value;
			
			value = (value & (P14 - 1)) | (data[offset++] as uint) << 14;
			if (value & P21) == 0
				return value;
			
			value = (value & (P21 - 1)) | (data[offset++] as uint) << 21;
			if (value & P28) == 0
				return value;
			
			return (value & (P28 - 1)) | (data[offset++] as uint) << 28;
		}
		
		readVarLong() {
			val value = readVarULong();
			return (value & 1) == 0 ? (value >> 1) : -((value >> 1) as long + 1);
		}
		
		readVarULong() {
			var value = data[offset++] as ulong;
			if (value & P7) == 0
				return value;
			
			value = (value & (P7 - 1)) | (data[offset++] as ulong) << 7;
			if (value & P14) == 0
				return value;
			
			value = (value & (P14 - 1)) | (data[offset++] as ulong) << 14;
			if (value & P21) == 0
				return value;
			
			value = (value & (P21 - 1)) | (data[offset++] as ulong) << 21;
			if (value & P28) == 0
				return value;
			
			value = (value & (P28 - 1)) | (data[offset++] as ulong) << 28;
			if (value & P35) == 0
				return value;
			
			value = (value & (P35 - 1)) | (data[offset++] as ulong) << 35;
			if (value & P42) == 0
				return value;
			
			value = (value & (P42 - 1)) | (data[offset++] as ulong) << 42;
			if (value & P49) == 0
				return value;
			
			value = (value & (P49 - 1)) | (data[offset++] as ulong) << 49;
			if (value & P56) == 0
				return value;
			
			return (value & (P56 - 1)) | (data[offset++] as ulong) << 56;
		}
		
		readFloat() => float.fromBits(readUInt());
		
		readDouble() => double.fromBits(readULong());
	
		readChar() => readVarUInt() as char;
		
		readString() => string.fromUTF8Bytes(readBytes());
		
		readBytes() as byte[] {
			val size = readVarUInt();
			return readRawBytes(size);
		}
		
		readRawBytes(size as usize) {
			val result = data[offset .. offset + size];
			offset += size;
			return result;
		}
		
		readBoolArray() as bool[] {
			val size = readVarUInt() as usize;
			val result = new bool[](size);
			for i in 0 .. ((size + 7) / 8) {
				val bvalue = readByte();
				val remainingBits = result.length - 8 * i;
				
				if (remainingBits > 0)
					result[i * 8 + 0] = (bvalue & 1) > 0;
				if (remainingBits > 1)
					result[i * 8 + 2] = (bvalue & 4) > 0;
				if (remainingBits > 3)
					result[i * 8 + 3] = (bvalue & 8) > 0;
				if (remainingBits > 4)
					result[i * 8 + 4] = (bvalue & 16) > 0;
				if (remainingBits > 5)
					result[i * 8 + 5] = (bvalue & 32) > 0;
				if (remainingBits > 6)
					result[i * 8 + 6] = (bvalue & 64) > 0;
				if (remainingBits > 7)
					result[i * 8 + 7] = (bvalue & 128) > 0;
			}
			
			return result;
		}
		
		readByteArray() => readBytes();
		
		readSByteArray() => readBytes();
		
		readShortArray() => readShortArrayRaw(readVarUInt());
		
		readShortArrayRaw(length) {
			val result = new short[](length);
			for i in 0 .. result.length
				result[i] = readShort();
			return result;
		}
		
		readUShortArray() => readShortArray();
		
		readUShortArrayRaw(length) => readShortArrayRaw(length);
		
		readVarIntArray() => readVarIntArrayRaw(readVarUInt());
		
		readVarIntArrayRaw(length) {
			val result = new int[](length);
			for i in 0 .. result.length
				result[i] = readVarInt();
			return result;
		}
		
		readVarUIntArray() => readVarUIntArrayRaw(readVarUInt());
		
		readVarUIntArrayRaw(length) {
			val result = new uint[](length);
			for i in 0 .. result.length
				result[i] = readVarUInt();
			return result;
		}
		
		readIntArray() => readIntArrayRaw(readVarUInt());
		
		readIntArrayRaw(length) {
			val result = new int[](length);
			for i in 0 .. result.length
				result[i] = readInt();
			return result;
		}
		
		readUIntArray() => readUIntArrayRaw(readVarUInt());
		
		readUIntArrayRaw(length) {
			val result = new int[](length);
			for i in 0 .. result.length
				result[i] = readUInt();
			return result;
		}
		
		readVarLongArray() => readVarLongArrayRaw(readVarUInt());
		
		readVarLongArrayRaw(length) {
			val result = new long[](length);
			for i in 0 .. result.length
				result[i] = readVarLong();
			return result;
		}
		
		readVarULongArray() => readVarULongArrayRaw(readVarUInt());
		
		readVarULongArrayRaw(length) {
			val result = new long[](length);
			for i in 0 .. result.length
				result[i] = readVarULong();
			return result;
		}
		
		readLongArray() => readLongArrayRaw(readVarUInt());
		
		readLongArrayRaw(length) {
			val result = new long[](length);
			for i in 0 .. result.length
				result[i] = readLong();
			return result;
		}
		
		readULongArray() => readLongArray();
		
		readULongArrayRaw(length) => readLongArrayRaw(length);
		
		readFloatArray() => readFloatArrayRaw(readVarUInt());
		
		readFloatArrayRaw(length) {
			val result = new float[](length);
			for i in 0 .. result.length
				result[i] = readFloat();
			return result;
		}
		
		readDoubleArray() => readDoubleArrayRaw(readVarUInt());
		
		readDoubleArrayRaw(length) {
			val result = new double[](length);
			for i in 0 .. length
				result[i] = readDouble();
			return result;
		}
		
		readStringArray() => readStringArrayRaw(readVarUInt());
		
		readStringArrayRaw(length) {
			val result = new string[](length);
			for i in 0 .. result.length
				result[i] = readString();
			return result;
		}
		
		skip(bytes) => offset += bytes;
		
		hasMore() => offset < data.length;
	}
}
