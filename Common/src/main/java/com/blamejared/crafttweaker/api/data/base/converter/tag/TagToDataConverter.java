package com.blamejared.crafttweaker.api.data.base.converter.tag;


import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.IData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;

public class TagToDataConverter {
    
    /**
     * Converts the given Tag to it's IData representation.
     *
     * @param tag The tag to convert.
     *
     * @return The IData representation of the tag.
     */
    @Nullable
    public static IData convert(Tag tag) {
        
        if(tag == null) {
            return null;
        }
        TagToDataVisitor visitor = new TagToDataVisitor();
        tag.accept(visitor);
        return visitor.getValue();
    }
    
    /**
     * Converst the given Tag to it's {@link IData} representation.
     *
     * @param tag   The tag to convert.
     * @param clazz The reified type of T IData to convert to.
     * @param <T>   The type of data to convert to.
     *
     * @return The IData representation of the tag.
     */
    @Nullable
    public static <T extends IData> T convertTo(Tag tag, Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        
        if(tag == null) {
            return null;
        }
        
        if(clazz == null) {
            return (T) convert(tag);
        }
        if(clazz.isInterface()) {
            IData converted = convert(tag);
            if(converted == null) {
                return null;
            }
            if(clazz.isInstance(converted)) {
                return (T) converted;
            }
            throw new IllegalArgumentException("Given tag '" + converted.getClass() + "' is not of expected type '" + clazz + "'");
        }
        
        return clazz.getConstructor(tag.getClass()).newInstance(tag);
    }
    
    /**
     * Converts the given CompoundTag to it's MapData representation.
     *
     * @param tag The tag to convert.
     *
     * @return The MapData representation of the CompoundTag.
     */
    @Nullable
    public static MapData convertCompound(CompoundTag tag) {
        
        if(tag == null) {
            return null;
        }
        TagToDataVisitor visitor = new TagToDataVisitor();
        tag.accept(visitor);
        if(visitor.getValue() instanceof MapData mapData) {
            return mapData;
        }
        throw new RuntimeException("Error while converting provided CompoundTag: '%s' to MapData!".formatted(tag));
    }
    
}
