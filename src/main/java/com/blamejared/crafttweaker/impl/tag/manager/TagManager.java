package com.blamejared.crafttweaker.impl.tag.manager;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.impl.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TagManagers are used to handle the different types of Tags within the game.
 * They can be retrieved directly with the TagManager BEP, and are also used indirectly when creating a tag with the Tag BEP.
 *
 * @param <T> The element types of this tag.
 */
@ZenRegister
@Document("vanilla/api/tags/TagManager")
@ZenCodeType.Name("crafttweaker.api.tag.TagManager")
public interface TagManager<T> extends CommandStringDisplayable {
    
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
     *
     * @return A Tag object.
     */
    @ZenCodeType.Method
    default MCTag<T> getTag(String name) {
        
        return getTag(new ResourceLocation(name));
    }
    
    /**
     * Retrieves a tag by its name.
     * Will also be called by the BEP.
     * <p>
     * Note that this method does _not_ yet create the tag if it does not exist.
     * Adding something to the object created by this tag will create it for the game.
     *
     * @param location The Resource location of the tag
     *
     * @return A Tag object.
     */
    @ZenCodeType.Method
    default MCTag<T> getTag(ResourceLocation location) {
        
        return new MCTag<>(location, this);
    }
    
    /**
     * Checks if a tag already exists. Does the same as calling `.exists` on a tag directly
     *
     * @param name The resource location to check for
     *
     * @return Whether or not this tag already exists
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(String name) {
        
        return exists(new ResourceLocation(name));
    }
    
    /**
     * Checks if a tag already exists. Does the same as calling `.exists` on a tag directly
     *
     * @param location The resource location to check for
     *
     * @return Whether or not this tag already exists
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(ResourceLocation location) {
        
        return getTagCollection().getIDTagMap().containsKey(location);
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
                .map(tagLocation -> new MCTag<>(tagLocation, this))
                .collect(Collectors.toList());
    }
    
    /**
     * Retrieves all tags contain the provided element
     *
     * @param element The element whose tags should be returned
     */
    @ZenCodeType.Method
    default List<MCTag<T>> getAllTagsFor(T element) {
        //While this is a working implementation, using TagCollection#getOwningTags would probably be more efficient ^^
        //Therefore the CrT TagManagers override this impl
        return getAllTags().stream()
                .filter(tag -> tag.contains(element))
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
     *
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
        
        return getTagCollection().get(theTag.getIdInternal());
    }
    
    /**
     * Returns the tag collection for this manager
     * It is also recommended to replace the ? with the actual type in implementations ^^
     */
    ITagCollection<?> getTagCollection();
    
}
