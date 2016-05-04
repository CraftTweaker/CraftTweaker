/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import minetweaker.mc1710.util.MineTweakerHacks;
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
		if (nbt == null)
			return null;

		switch (nbt.getId()) {
			case 1: // byte
				return new DataByte(((NBTBase.NBTPrimitive) nbt).func_150290_f());
			case 2: // short
				return new DataShort(((NBTBase.NBTPrimitive) nbt).func_150289_e());
			case 3: // int
				return new DataInt(((NBTBase.NBTPrimitive) nbt).func_150287_d());
			case 4: // long
				return new DataLong(((NBTBase.NBTPrimitive) nbt).func_150291_c());
			case 5: // float
				return new DataFloat(((NBTBase.NBTPrimitive) nbt).func_150288_h());
			case 6: // double
				return new DataDouble(((NBTBase.NBTPrimitive) nbt).func_150286_g());
			case 7: // byte[]
				return new DataByteArray(((NBTTagByteArray) nbt).func_150292_c(), immutable);
			case 8: // string
				return new DataString(((NBTTagString) nbt).func_150285_a_());
			case 9: { // list
				List<IData> values = new ArrayList<IData>();
				List<NBTBase> original = MineTweakerHacks.getTagList((NBTTagList) nbt);
				for (NBTBase value : original) {
					values.add(from(value, immutable));
				}
				return new DataList(values, immutable);
			}
			case 10: { // compound
				Map<String, IData> values = new HashMap<String, IData>();
				NBTTagCompound original = (NBTTagCompound) nbt;
				for (String key : (Set<String>) original.func_150296_c()) {
					values.put(key, from(original.getTag(key), immutable));
				}
				return new DataMap(values, immutable);
			}
			case 11: // int[]
				return new DataIntArray(((NBTTagIntArray) nbt).func_150302_c(), immutable);
			default:
				throw new RuntimeException("Unknown tag type: " + nbt.getId());
		}
	}

	public static void updateMap(NBTTagCompound nbt, IData data) {
		NBTUpdater updater = new NBTUpdater(nbt);
		data.convert(updater);
	}

	public NBTConverter() {
	}

	@Override
	public NBTBase fromBool(boolean value) {
		return new NBTTagInt(value ? 1 : 0);
	}

	@Override
	public NBTBase fromByte(byte value) {
		return new NBTTagByte(value);
	}

	@Override
	public NBTBase fromShort(short value) {
		return new NBTTagShort(value);
	}

	@Override
	public NBTBase fromInt(int value) {
		return new NBTTagInt(value);
	}

	@Override
	public NBTBase fromLong(long value) {
		return new NBTTagLong(value);
	}

	@Override
	public NBTBase fromFloat(float value) {
		return new NBTTagFloat(value);
	}

	@Override
	public NBTBase fromDouble(double value) {
		return new NBTTagDouble(value);
	}

	@Override
	public NBTBase fromString(String value) {
		return new NBTTagString(value);
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
		return new NBTTagByteArray(value);
	}

	@Override
	public NBTBase fromIntArray(int[] value) {
		return new NBTTagIntArray(value);
	}
}
