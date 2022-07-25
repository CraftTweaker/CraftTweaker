package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

/**
 * @docParam this <tag:items:minecraft:wool>
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
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
    default boolean exists() {
        
        return manager().exists(id());
    }
    
    /**
     * Gets the id of this tag.
     *
     * @return The id of this tag.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    ResourceLocation id();
    
    /**
     * Adds the elements that correspond to the given {@link ResourceLocation} to this tag.
     *
     * @param elements The registry key of the elements to add.
     *
     * @docParam elements <resource:minecraft:diamond>
     */
    @ZenCodeType.Method
    default void addId(ResourceLocation... elements) {
        
        manager().addId(GenericUtil.uncheck(this), elements);
    }
    
    /**
     * Adds the given tags to this tag.
     *
     * @param tags The tags to add.
     *
     * @docParam tags <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    default void add(MCTag... tags) {
        
        addId(Arrays.stream(tags)
                .map(MCTag::idElements)
                .flatMap(List::stream)
                .toArray(ResourceLocation[]::new));
    }
    
    /**
     * Removes the elements that correspond to the given {@link ResourceLocation} from this tag.
     *
     * @param elements The registry key of the elements to remove.
     *
     * @docParam elements <resource:minecraft:diamond>
     */
    @ZenCodeType.Method
    default void removeId(ResourceLocation... elements) {
        
        manager().removeId(GenericUtil.uncheck(this), elements);
    }
    
    /**
     * Removes the given tags from this tag.
     *
     * @param tags The tags to remove.
     *
     * @docParam tags <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    default void remove(MCTag... tags) {
        
        removeId(Arrays.stream(tags)
                .map(MCTag::idElements)
                .flatMap(List::stream)
                .toArray(ResourceLocation[]::new));
    }
    
    /**
     * Removes all elements in this tag.
     */
    @ZenCodeType.Method
    default void clear() {
        
        manager().clear(GenericUtil.uncheck(this));
    }
    
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
    default boolean contains(ResourceLocation id) {
        
        return idElements().contains(id);
    }
    
    /**
     * Gets the id's of the elements in this tag.
     *
     * @return The id's elements in this tag.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("idElements")
    default List<ResourceLocation> idElements() {
        
        return manager().idElements(GenericUtil.uncheck(this));
    }
    
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
    default <T extends Tag<Holder<?>>> T getInternal() {
        
        return (T) manager().getInternalRaw(GenericUtil.uncheck(this));
    }
    
    /**
     * Gets the {@link TagKey} of this tag.
     *
     * @return The {@link TagKey} of this tag.
     */
    default <T extends TagKey<?>> T getTagKey() {
        
        return (T) TagKey.create(GenericUtil.uncheck(manager().resourceKey()), this.id());
    }
    
    @Override
    default int compareTo(@Nonnull MCTag o) {
        
        return this.id().compareTo(o.id());
    }
    
    default String getCommandString() {
        
        return "<tag:" + manager().tagFolder() + ":" + id() + ">";
    }
    
}
