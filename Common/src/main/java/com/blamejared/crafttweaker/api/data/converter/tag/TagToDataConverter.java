package com.blamejared.crafttweaker.api.data.converter.tag;

import com.blamejared.crafttweaker.api.data.IData;
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
        return visitor.visit(tag);
    }
    
}
