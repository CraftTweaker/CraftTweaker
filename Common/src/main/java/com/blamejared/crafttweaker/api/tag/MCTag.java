package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.List;

@ZenRegister
@Document("vanilla/api/tag/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public interface MCTag extends CommandStringDisplayable, Comparable<MCTag> {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    boolean exists();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    ResourceLocation id();
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    boolean contains(ResourceLocation element);
    
    // This needs to be registered to ZC yourself with the return type specified, see KnownTag and UnknownTag
    List<?> elements();
    
    // This needs to be registered to ZC yourself with the return type specified, see KnownTag and UnknownTag
    ITagManager<?> manager();
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    default boolean equals(MCTag other) {
        
        return id().equals(other.id()) && manager().equals(other.manager());
    }
    
    <T extends Tag<Holder<?>>> T getInternal();
    
    <T extends TagKey<?>> T getTagKey();
    
    @Override
    default int compareTo(@Nonnull MCTag o) {
        
        return this.id().compareTo(o.id());
    }
    
}
