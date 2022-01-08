package com.blamejared.crafttweaker.api.data.base.converter.tag;


import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.IData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import javax.annotation.Nullable;

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
