/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.data;

import minetweaker.api.data.*;
import minetweaker.mc1102.util.MineTweakerHacks;
import net.minecraft.nbt.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Stan
 */
public class NBTConverter implements IDataConverter<NBTBase>{
    private static final NBTConverter INSTANCE = new NBTConverter();

    public NBTConverter(){
    }

    public static NBTBase from(IData data){
        return data.convert(INSTANCE);
    }

    public static IData from(NBTBase nbt, boolean immutable){
        if(nbt == null)
            return null;

        switch(nbt.getId()){
            case 1: // byte
                return new DataByte(((NBTPrimitive) nbt).getByte());
            case 2: // short
                return new DataShort(((NBTPrimitive) nbt).getShort());
            case 3: // int
                return new DataInt(((NBTPrimitive) nbt).getInt());
            case 4: // long
                return new DataLong(((NBTPrimitive) nbt).getLong());
            case 5: // float
                return new DataFloat(((NBTPrimitive) nbt).getFloat());
            case 6: // double
                return new DataDouble(((NBTPrimitive) nbt).getDouble());
            case 7: // byte[]
                return new DataByteArray(((NBTTagByteArray) nbt).getByteArray(), immutable);
            case 8: // string
                return new DataString(((NBTTagString) nbt).getString());
            case 9:{ // list
                List<IData> values = new ArrayList<>();
                List<NBTBase> original = MineTweakerHacks.getTagList((NBTTagList) nbt);
                values.addAll(original.stream().map(value -> from(value, immutable)).collect(Collectors.toList()));
                return new DataList(values, immutable);
            }
            case 10:{ // compound
                Map<String, IData> values = new HashMap<>();
                NBTTagCompound original = (NBTTagCompound) nbt;
                for(String key : original.getKeySet()){
                    values.put(key, from(original.getTag(key), immutable));
                }
                return new DataMap(values, immutable);
            }
            case 11: // int[]
                return new DataIntArray(((NBTTagIntArray) nbt).getIntArray(), immutable);
            default:
                throw new RuntimeException("Unknown tag type: " + nbt.getId());
        }
    }

    public static void updateMap(NBTTagCompound nbt, IData data){
        NBTUpdater updater = new NBTUpdater(nbt);
        data.convert(updater);
    }

    @Override
    public NBTBase fromBool(boolean value){
        return new NBTTagInt(value ? 1 : 0);
    }

    @Override
    public NBTBase fromByte(byte value){
        return new NBTTagByte(value);
    }

    @Override
    public NBTBase fromShort(short value){
        return new NBTTagShort(value);
    }

    @Override
    public NBTBase fromInt(int value){
        return new NBTTagInt(value);
    }

    @Override
    public NBTBase fromLong(long value){
        return new NBTTagLong(value);
    }

    @Override
    public NBTBase fromFloat(float value){
        return new NBTTagFloat(value);
    }

    @Override
    public NBTBase fromDouble(double value){
        return new NBTTagDouble(value);
    }

    @Override
    public NBTBase fromString(String value){
        return new NBTTagString(value);
    }

    @Override
    public NBTBase fromList(List<IData> values){
        NBTTagList result = new NBTTagList();
        for(IData value : values){
            result.appendTag(from(value));
        }
        return result;
    }

    @Override
    public NBTBase fromMap(Map<String, IData> values){
        NBTTagCompound result = new NBTTagCompound();
        for(Map.Entry<String, IData> entry : values.entrySet()){
            result.setTag(entry.getKey(), from(entry.getValue()));
        }
        return result;
    }

    @Override
    public NBTBase fromByteArray(byte[] value){
        return new NBTTagByteArray(value);
    }

    @Override
    public NBTBase fromIntArray(int[] value){
        return new NBTTagIntArray(value);
    }
}
