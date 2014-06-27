/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc164.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import minetweaker.mc164.util.MineTweakerHacks;
import minetweaker.api.data.DataByte;
import minetweaker.api.data.DataByteArray;
import minetweaker.api.data.DataDouble;
import minetweaker.api.data.DataFloat;
import minetweaker.api.data.DataInt;
import minetweaker.api.data.DataIntArray;
import minetweaker.api.data.DataList;
import minetweaker.api.data.DataLong;
import minetweaker.api.data.DataMap;
import minetweaker.api.data.DataShort;
import minetweaker.api.data.DataString;
import minetweaker.api.data.IData;
import minetweaker.api.data.IDataConverter;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

/**
 *
 * @author Stan
 */
public class NBTConverter implements IDataConverter<NBTBase> {
	private static final NBTConverter INSTANCE = new NBTConverter();
	
	public static NBTBase from(IData data) {
		return data.convert(INSTANCE);
	}
	
	public static IData from(NBTBase nbt, boolean immutable) {
		if (nbt == null) return null;
		
		switch (nbt.getId()) {
			case 1: // byte
				return new DataByte(((NBTTagByte) nbt).data);
			case 2: // short
				return new DataShort(((NBTTagShort) nbt).data);
			case 3: // int
				return new DataInt(((NBTTagInt) nbt).data);
			case 4: // long
				return new DataLong(((NBTTagLong) nbt).data);
			case 5: // float
				return new DataFloat(((NBTTagFloat) nbt).data);
			case 6: // double
				return new DataDouble(((NBTTagDouble) nbt).data);
			case 7: // byte[]
				return new DataByteArray(((NBTTagByteArray) nbt).byteArray, immutable);
			case 8: // string
				return new DataString(((NBTTagString) nbt).data);
			case 9: { // list
				NBTTagList listTag = (NBTTagList) nbt;
				List<IData> values = new ArrayList<IData>();
				for (int i = 0; i < listTag.tagCount(); i++) {
					values.add(from(listTag.tagAt(i), immutable));
				}
				return new DataList(values, immutable);
			}
			case 10: { // compound
				Map<String, IData> values = new HashMap<String, IData>();
				NBTTagCompound original = (NBTTagCompound) nbt;
				Map<String, NBTBase> tagMap = MineTweakerHacks.getNBTCompoundMap(original);
				for (String key : tagMap.keySet()) {
					values.put(key, from(original.getTag(key), immutable));
				}
				return new DataMap(values, immutable);
			}
			case 11: // int[]
				return new DataIntArray(((NBTTagIntArray) nbt).intArray, immutable);
			default:
				throw new RuntimeException("Unknown tag type: " + nbt.getId());
		}
	}

	public static void updateMap(NBTTagCompound nbt, IData data) {
		NBTUpdater updater = new NBTUpdater(nbt);
		data.convert(updater);
	}
	
	public NBTConverter() {}
	
	@Override
	public NBTBase fromBool(boolean value) {
		return new NBTTagInt(null, value ? 1 : 0);
	}

	@Override
	public NBTBase fromByte(byte value) {
		return new NBTTagByte(null, value);
	}

	@Override
	public NBTBase fromShort(short value) {
		return new NBTTagShort(null, value);
	}

	@Override
	public NBTBase fromInt(int value) {
		return new NBTTagInt(null, value);
	}

	@Override
	public NBTBase fromLong(long value) {
		return new NBTTagLong(null, value);
	}

	@Override
	public NBTBase fromFloat(float value) {
		return new NBTTagFloat(null, value);
	}

	@Override
	public NBTBase fromDouble(double value) {
		return new NBTTagDouble(null, value);
	}

	@Override
	public NBTBase fromString(String value) {
		return new NBTTagString(null, value);
	}

	@Override
	public NBTBase fromList(List<IData> values) {
		NBTTagList result = new NBTTagList();
		for (IData value : values) {
			result.appendTag(from(value));
		}
		return result;
	}

	@Override
	public NBTBase fromMap(Map<String, IData> values) {
		NBTTagCompound result = new NBTTagCompound();
		for (Map.Entry<String, IData> entry : values.entrySet()) {
			result.setTag(entry.getKey(), from(entry.getValue()));
		}
		return result;
	}

	@Override
	public NBTBase fromByteArray(byte[] value) {
		return new NBTTagByteArray(null, value);
	}

	@Override
	public NBTBase fromIntArray(int[] value) {
		return new NBTTagIntArray(null, value);
	}
}
