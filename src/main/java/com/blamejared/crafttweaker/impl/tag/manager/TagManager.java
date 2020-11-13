package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker.api.brackets.*;
import com.blamejared.crafttweaker.impl.tag.*;
import com.blamejared.crafttweaker.impl.util.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.tags.*;
import net.minecraft.util.*;
import org.openzen.zencode.java.*;

import javax.annotation.*;
import java.util.*;
import java.util.stream.*;

/**
 * TagManagers are used to handle the different types of Tags within the game.
 * They can be retrieved directly with the TagManager BEP, and are also used indirectly when creating a tag with the Tag BEP.
 *
 * @param <T> The element types of this tag.
 */
@ZenRegister
@Document("vanilla/api/tags/TagManager")
@ZenCodeType.Name("crafttweaker.api.tag.TagManager")
public interface TagManager<T extends CommandStringDisplayable> extends CommandStringDisplayable {
    
    @Nonnull
    Class<T> getElementClass();
    
    /**
     * Retrieves a tag by its name.
     * Will also be called by the BEP.
     * <p>
     * Note that this method does _not_ yet create the tag if it does not exist.
     * Adding something to the object created by this tag will create it for the game.
     *
     * @param name The Resource location of the tag
     * @return A Tag object.
     */
    @ZenCodeType.Method
    default MCTag<T> getTag(String name) {
        return getTag(new MCResourceLocation(new ResourceLocation(name)));
    }
    
    /**
     * Retrieves a tag by its name.
     * Will also be called by the BEP.
     * <p>
     * Note that this method does _not_ yet create the tag if it does not exist.
     * Adding something to the object created by this tag will create it for the game.
     *
     * @param location The Resource location of the tag
     * @return A Tag object.
     */
    @ZenCodeType.Method
    default MCTag<T> getTag(MCResourceLocation location) {
        return new MCTag<>(location.getInternal(), this);
    }
    
    /**
     * Checks if a tag already exists. Does the same as calling `.exists` on a tag directly
     *
     * @param name The resource location to check for
     * @return Whether or not this tag already exists
     */
    @ZenCodeType.Method
    default boolean exists(String name) {
        return exists(new MCResourceLocation(new ResourceLocation(name)));
    }
    
    /**
     * Checks if a tag already exists. Does the same as calling `.exists` on a tag directly
     *
     * @param location The resource location to check for
     * @return Whether or not this tag already exists
     */
    default boolean exists(MCResourceLocation location) {
        return getTagCollection().getIDTagMap().containsKey(location.getInternal());
    }
    
    /**
     * Retrieves a list of all tags currently registered.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("all")
    default List<MCTag<T>> getAllTags() {
        return getTagCollection().getIDTagMap()
                .keySet()
                .stream()
                .map(itemITag -> new MCTag<>(itemITag, this))
                .collect(Collectors.toList());
    }
    
    /**
     * Get the tag type. In a Bracket call, this will used to determine which TagManager to use.
     * <p>
     * {@code <tag:{tag_type}:tag_location:tag_name>} <br>
     * {@code <tagManager:{tag_type}>}
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagType")
    String getTagFolder();
    
    /**
     * Just so you won't have to implement that yourself ^^
     */
    @Override
    default String getCommandString() {
        return "<tagManager:" + getTagFolder() + ">";
    }
    
    /**
     * Use this method to add elements to your tag.
     * Create a new tag if it does not already exist!
     * <p>
     * An easy way to do this is to create and apply a new {@link com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd}
     *
     * @param to    The tag to add these elements to
     * @param toAdd The items to add
     */
    void addElements(MCTag<T> to, List<T> toAdd);
    
    /**
     * Use this method to remove elements from your tag
     * Will be called by MCTag.
     *
     * @param from     The tag from which to remove
     * @param toRemove The elements to remove
     */
    void removeElements(MCTag<T> from, List<T> toRemove);
    
    /**
     * Use this method to get all elements within the given Tag.
     * Will log a warning and return an empty list, if the tag does not exist.
     *
     * @param theTag The tag whose elements should be retrieved
     * @return The Elements in this tag, or an empty list
     */
    List<T> getElementsInTag(MCTag<T> theTag);
    
    /**
     * Return the tag object of this element.
     * It is also recommended to replace the ? with the actual type in implementations ^^
     *
     * @return The MC tag type, or null if it does not exist
     */
    @Nullable
    default ITag<?> getInternal(MCTag<T> theTag) {
        return getTagCollection().getIDTagMap().get(theTag.getIdInternal());
    }
    
    /**
     * Returns the tag collection for this manager
     * It is also recommended to replace the ? with the actual type in implementations ^^
     */
    ITagCollection<?> getTagCollection();
}
