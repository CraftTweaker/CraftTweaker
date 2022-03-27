package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @docParam this <tag:items:minecraft:wool>
 */
@ZenRegister
@Document("vanilla/api/tag/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public interface MCTag extends CommandStringDisplayable, Comparable<MCTag> {
    
    /**
     * Checks if this tag exists.
     *
     * @return true if this tag exists, false otherwise.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    boolean exists();
    
    /**
     * Gets the id of this tag.
     *
     * @return The id of this tag.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    ResourceLocation id();
    
    /**
     * Checks if this tag contains an element with the given id
     *
     * @param id The ID of the element to check.
     *
     * @return true if it contains the element, false otherwise.
     *
     * @docParam id <resource:minecraft:white_wool>
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    boolean contains(ResourceLocation id);
    
    /**
     * Gets the elements in this tag.
     *
     * <p>If this is a {@link KnownTag}, then the elements will be of that tag's type (e.g. Item, Block).</p>
     *
     * <p>If this is a {@link UnknownTag}, then the elements will be the {@link ResourceLocation} of the elements.</p>
     *
     * @return The elements in this tag.
     *
     * @implNote This method needs to be registered to ZC in the implemented class,
     * with the return type of this method narrowed through the use of covariant return types.
     *
     * If you aren't sure how to implement this, look at {@link KnownTag#elements()} and {@link UnknownTag#elements()} for implementations.
     */
    List<?> elements();
    
    /**
     * Gets the {@link ITagManager} for this tag.
     *
     * @return The {@link ITagManager} for this tag.
     *
     * @implNote This method needs to be registered to ZC in the implemented class,
     * with the return type of this method narrowed through the use of covariant return types.
     *
     * If you aren't sure how to implement this, look at {@link KnownTag#manager()} and {@link UnknownTag#manager()} for implementations.
     */
    ITagManager<?> manager();
    
    /**
     * Checks if this tag equals the other tag.
     *
     * @param other The tag to check against.
     *
     * @return true if the tags are equal, false otherwise.
     *
     * @docParam other <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    default boolean equals(MCTag other) {
        
        return id().equals(other.id()) && manager().equals(other.manager());
    }
    
    /**
     * Gets the internal {@link Tag} of this tag.
     *
     * <p>This should only be used if the values of the tag is needed, other usecases should use {@link #getTagKey()} instead.</p>
     *
     * @return The internal {@link Tag} of this tag.
     */
    <T extends Tag<Holder<?>>> T getInternal();
    
    /**
     * Gets the {@link TagKey} of this tag.
     *
     * @return The {@link TagKey} of this tag.
     */
    <T extends TagKey<?>> T getTagKey();
    
    @Override
    default int compareTo(@Nonnull MCTag o) {
        
        return this.id().compareTo(o.id());
    }
    
}
