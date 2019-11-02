public class CompactBytesDataOutput {
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
	
	var data = new byte[](16);
	var length as usize = 0;
	
	private reserve(bytes as usize) as void {
		while length + bytes > data.length
			data = data.copy(2 * data.length);
	}
	
	public asByteArray() as byte[] => data.copy(length);
	
	~this {}
	
	public implements CompactDataOutput {
		writeBool(value) => writeByte(value ? 1 : 0);
		
		writeByte(value) {
			reserve(1);
			data[length++] = value;
		}
		
		writeSByte(value) => writeByte(value);
		
		writeShort(value) => writeUShort(value);
		
		writeUShort(value) {
			reserve(2);
			data[length++] = (value >> 8) as byte;
			data[length++] = value as byte;
		}
		
		writeInt(value) => writeUInt(value);
		
		writeUInt(value) {
			reserve(4);
			data[length++] = (value >> 24) as byte;
			data[length++] = (value >> 16) as byte;
			data[length++] = (value >> 8) as byte;
			data[length++] = value as byte;
		}
		
		writeLong(value) => writeULong(value);
		
		writeULong(value) {
			reserve(8);
			data[length++] = (value >> 56) as byte;
			data[length++] = (value >> 48) as byte;
			data[length++] = (value >> 40) as byte;
			data[length++] = (value >> 32) as byte;
			data[length++] = (value >> 24) as byte;
			data[length++] = (value >> 16) as byte;
			data[length++] = (value >> 8) as byte;
			data[length++] = value as byte;
		}
		
		writeVarInt(value) => writeVarUInt(value < 0 ? (((1 - (value as uint)) << 1) + 1) : (value << 1));
		
		writeVarUInt(value) {
			reserve(5);
			
			if value < P7 {
				data[length++] = (value & 0x7F) as byte;
			} else if value < P14 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 7) & 0x7F) as byte;
			} else if value < P21 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 14) & 0x7F) as byte;
			} else if value < P28 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 21) & 0x7F) as byte;
			} else {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 28) & 0x7F) as byte;
			}
		}
		
		writeVarLong(value) => writeVarULong(value < 0 ? (((1 - (value as ulong)) << 1) + 1) : (value << 1));
		
		writeVarULong(value) {
			reserve(9);
			
			if value < P7 {
				data[length++] = (value & 0x7F) as byte;
			} else if value < P14 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 7) & 0x7F) as byte;
			} else if value < P21 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 14) & 0x7F) as byte;
			} else if value < P28 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 21) & 0x7F) as byte;
			} else if value < P35 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 28) & 0x7F) as byte;
			} else if value < P42 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 28) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 35) & 0x7F) as byte;
			} else if value < P49 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 28) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 35) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 42) & 0x7F) as byte;
			} else if value < P56 {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 28) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 35) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 42) & 0x7F) | 0x80) as byte;
				data[length++] = ((value >> 49) & 0x7F) as byte;
			} else {
				data[length++] = ((value & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 7) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 14) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 21) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 28) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 35) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 42) & 0x7F) | 0x80) as byte;
				data[length++] = (((value >> 49) & 0x7F) | 0x80) as byte;
				data[length++] = (value >> 56) as byte;
			}
		}
		
		writeFloat(value) => writeUInt(value.bits);
		
		writeDouble(value) => writeULong(value.bits);
		
		writeChar(value) => writeVarUInt(value);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeBytes(data) {
			writeVarUInt(data.length as uint);
			writeRawBytes(data);
		}
		
		[Precondition(ENFORCE, length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeBytes(data, offset, length) {
			writeVarUInt(length as uint);
			writeRawBytes(data, offset, length);
		}
		
		writeString(str) => writeBytes(str.toUTF8Bytes());
		
		writeRawBytes(value) {
			reserve(value.length);
			value.copyTo(data, 0, length, value.length);
			length += value.length;
		}
		
		writeRawBytes(value, offset, length) {
			reserve(value.length);
			value.copyTo(data, offset, this.length, value.length);
			this.length += length;
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeBoolArray(data) {
			writeVarUInt(data.length as uint);
			
			var i = 0 as usize;
			while i < data.length - 7 {
				var bvalue as byte = 0;
				if data[i + 0]
					bvalue |= 1;
				if data[i + 1]
					bvalue |= 2;
				if data[i + 2]
					bvalue += 4;
				if data[i + 3]
					bvalue += 8;
				if data[i + 4]
					bvalue += 16;
				if data[i + 5]
					bvalue += 32;
				if data[i + 6]
					bvalue += 64;
				if data[i + 7]
					bvalue += 128;
				writeByte(bvalue);
				i += 8;
			}
			
			if i < data.length {
				var bvalue as byte = 0;
				for offset in 0 .. (data.length % 7)
					if data[i + offset]
						bvalue += (1 << i) as byte;
				
				writeByte(bvalue);
			}
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeByteArray(data) => writeBytes(data);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeSByteArray(data) => writeBytes(data);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeShortArray(data) {
			writeVarUInt(data.length as uint);
			writeShortArrayRaw(data);
		}
		
		writeShortArrayRaw(data) {
			for element in data
				writeShort(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeUShortArray(data) => writeShortArray(data);
		
		writeUShortArrayRaw(data) => writeShortArrayRaw(data);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeVarIntArray(data) {
			writeVarUInt(data.length as uint);
			writeVarIntArrayRaw(data);
		}
		
		writeVarIntArrayRaw(data) {
			for element in data
				writeVarInt(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeVarUIntArray(data) {
			writeVarUInt(data.length as uint);
			writeVarUIntArrayRaw(data);
		}
		
		writeVarUIntArrayRaw(data) {
			for element in data
				writeVarUInt(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeIntArray(data) {
			writeVarUInt(data.length as uint);
			writeIntArrayRaw(data);
		}
		
		writeIntArrayRaw(data) {
			for element in data
				writeInt(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeUIntArray(data) => writeIntArray(data);
		
		writeUIntArrayRaw(data) => writeIntArrayRaw(data);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeVarLongArray(data) {
			writeVarUInt(data.length as uint);
			writeVarLongArrayRaw(data);
		}
		
		writeVarLongArrayRaw(data) {
			for element in data
				writeVarLong(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeVarULongArray(data) {
			writeVarUInt(data.length as uint);
			writeVarULongArrayRaw(data);
		}
		
		writeVarULongArrayRaw(data) {
			for element in data
				writeVarULong(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeLongArray(data) {
			writeVarUInt(data.length as uint);
			writeLongArrayRaw(data);
		}
		
		writeLongArrayRaw(data) {
			for element in data
				writeLong(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeULongArray(data) => writeLongArray(data);
		
		writeULongArrayRaw(data) => writeLongArrayRaw(data);
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeFloatArray(data) {
			writeVarUInt(data.length as uint);
			writeFloatArrayRaw(data);
		}
		
		writeFloatArrayRaw(data) {
			for element in data
				writeFloat(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeDoubleArray(data) {
			writeVarUInt(data.length as uint);
			writeDoubleArrayRaw(data);
		}
		
		writeDoubleArrayRaw(data) {
			for element in data
				writeDouble(element);
		}
		
		[Precondition(ENFORCE, data.length < uint.MAX_VALUE, "Array length cannot exceed uint limit")]
		writeStringArray(data) {
			writeVarUInt(data.length as uint);
			writeStringArrayRaw(data);
		}
		
		writeStringArrayRaw(data) {
			for element in data
				writeString(element);
		}
		
		flush() {
			// nothing to do
		}
	}
}
