package com.blamejared.crafttweaker.api.tag.manager;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @docParam this <tagmanager:items>
 */
@ZenRegister
@Document("vanilla/api/tag/manager/ITagManager")
@ZenCodeType.Name("crafttweaker.api.tag.manager.ITagManager")
public interface ITagManager<T extends MCTag> extends CommandStringDisplayable, Iterable<T> {
    
    /**
     * Gets the tagFolder of this manager.
     *
     * <p>The tag folder is usually the folder on disk without the `tags/` prefix.</p>
     *
     * <p>Examples:</p>
     * <ul>
     * <li>`tags/items` turns into `items`</li>
     * <li>`tags/potion` turns into `potion`</li>
     * <li>`tags/worldgen/biome` turns into `worldgen/biome`</li>
     * </ul>
     * @return The tag folder of this manager.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagFolder")
    default String tagFolder() {
        
        return CraftTweakerTagRegistry.INSTANCE.makeTagFolder(resourceKey());
    }
    
    /**
     * Gets a tag with the given id.
     *
     * @param id The id of the tag.
     *
     * @return A tag with the given id.
     *
     * @docParam id "minecraft:wool"
     */
    @ZenCodeType.Method
    T tag(String id);
    
    /**
     * Gets a tag with the given id.
     *
     * @param id The id of the tag.
     *
     * @return A tag with the given id.
     *
     * @docParam id <resource:minecraft:wool>
     */
    @ZenCodeType.Method
    T tag(ResourceLocation id);
    
    /**
     * Gets a map of id to tag that this manager knows about.
     *
     * @return a map of id to tag.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagMap")
    Map<ResourceLocation, T> tagMap();
    
    /**
     * Checks if a tag with the given id exists and is registered.
     *
     * @param id The id of the tag to check.
     *
     * @return true if it exists, false otherwise.
     *
     * @docParam id "minecraft:wool"
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(String id) {
        
        return exists(new ResourceLocation(id));
    }
    
    /**
     * Checks if a tag with the given id exists and is registered.
     *
     * @param id The id of the tag to check.
     *
     * @return true if it exists, false otherwise.
     *
     * @docParam id <resource:minecraft:wool>
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(ResourceLocation id) {
        
        return tagKeys().contains(id);
    }
    
    /**
     * Checks if the given tag exists and is registered.
     *
     * @param tag The tag to check.
     *
     * @return true if it exists, false otherwise.
     *
     * @docParam tag <tag:items:minecraft:wool>
     */
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    default boolean exists(T tag) {
        
        return exists(tag.id());
    }
    
    /**
     * Gets the keys of the tags that this manager knows about.
     *
     * @return A List of keys of the tags that this manager knows about.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("tagKeys")
    List<ResourceLocation> tagKeys();
    
    /**
     * Gets the command string of this manager.
     *
     * @return The command string of this manager.
     */
    @Override
    default String getCommandString() {
        
        return "<tagmanager:" + tagFolder() + ">";
    }
    
    /**
     * Adds a tag to this manager with the given id.
     *
     * @param id  The id of the tag to add.
     * @param tag The tag to add
     */
    <U> void addTag(ResourceLocation id, Tag<Holder<U>> tag);
    
    /**
     * Gets the internal tags of this manager.
     *
     * @return a map of id to tag.
     */
    Map<ResourceLocation, Tag<Holder<?>>> internalTags();
    
    /**
     * Binds this manager to the given load result.
     *
     * <p>This is usually storing the given {@link net.minecraft.tags.TagManager.LoadResult} into a {@link com.blamejared.crafttweaker.api.tag.MutableLoadResult}
     * which allows for easy mutation.</p>
     *
     * @param result The result to bind to.
     */
    void bind(TagManager.LoadResult<?> result);
    
    /**
     * Gets the element type that this tag manager handles.
     *
     * <p>This is only used to fill in type parameters, if your custom ITagManager does not have a type paremeter, you can return an empty optional.</p>
     *
     * @return An optional class of the type of elements that this manager deals with.
     */
    default Optional<Class<?>> elementClass() {
        
        return Optional.empty();
    }
    
    /**
     * Gets the elements of the given tag.
     *
     * @param of The tag to get the elements of.
     *
     * @return The list of elements in the tag.
     *
     * @implNote This method needs to be overriden with a covariant return type and exposed to ZenCode.
     * @docParam of <tag:items:minecraft:dirt>
     */
    List<?> elements(T of);
    
    /**
     * Ges the tags that this manager knows about.
     *
     * @return The tags that this manager knows about.
     *
     * @implNote This method needs to be overriden with a covariant return type and exposed to ZenCode.
     */
    List<T> tags();
    
    /**
     * Ges the tags that contain the given element.
     *
     * @return The tags that contain the given elements.
     *
     * @implNote This method needs to be overriden with a covariant return type and exposed to ZenCode.
     */
    @ZenCodeType.Method
    List<T> getTagsFor(ResourceLocation element);
    
    /**
     * Gets the resource key of the registry that this manager deals with.
     *
     * @return The resource key of the registry that this manager deals with.
     */
    ResourceKey<? extends Registry<?>> resourceKey();
    
    /**
     * Recalculates the cached tag map.
     */
    void recalculate();
    
    T tag(TagKey<?> key);
    
    @Nonnull
    @Override
    default Iterator<T> iterator() {
        
        return tags().iterator();
    }
    
}
