package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.manager.ITagManager;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A reference to a Tag object.
 * Note that this tag may not exist in the game already, such as when you create new tags.
 * See the {@link MCTag#exists()} Method on whether this tag already exists.
 * <p>
 * A tag will be created as soon as you add
 *
 * @docParam this <tag:items:forge:gems>
 */
@ZenRegister
@Document("vanilla/api/tag/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public final class MCTag<T> implements CommandStringDisplayable, Iterable<T> {
    
    private final ResourceLocation id;
    private final ITagManager<T> manager;
    
    public MCTag(ResourceLocation id, ITagManager<T> manager) {
        
        this.id = id;
        this.manager = manager;
    }
    
    /**
     * Adds the given items to the tag. Creates the tag if it does not exist.
     *
     * @param items The items to add. Can be one or more items.
     *
     * @docParam items <item:minecraft:bedrock>
     * @docParam items <item:minecraft:iron_ingot>, <item:minecraft:gold_ingot>
     * @docParam items [<item:minecraft:iron_ingot>, <item:minecraft:gold_ingot>]
     */
    @SafeVarargs
    @ZenCodeType.Method
    public final void add(T... items) {
        
        add(Arrays.asList(items));
    }
    
    /**
     * Adds the given items to the tag. Creates the tag if it does not exist.
     *
     * @param items The items to add. Provided as list.
     */
    @ZenCodeType.Method
    public void add(List<T> items) {
        
        manager.addElements(this, items);
    }
    
    /**
     * Adds the given tag to this tag. Creates the tag if it does not exist.
     *
     * @param tag The tag to add.
     *
     * @docParam tag <tag:items:forge:rods>
     */
    @ZenCodeType.Method
    public void add(MCTag<T> tag) {
        
        add(tag.getElements());
    }
    
    /**
     * Adds the given tags to this tag. Creates the tag if it does not exist.
     *
     * @param tags The tags to add.
     *
     * @docParam tags <tag:items:forge:rods>
     */
    @ZenCodeType.Method
    public void addTags(List<MCTag<T>> tags) {
        
        add(tags.stream()
                .flatMap(tag -> tag.getElements().stream())
                .collect(Collectors.toList()));
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void remove(T... items) {
        
        remove(Arrays.asList(items));
    }
    
    @ZenCodeType.Method
    public void remove(List<T> items) {
        
        manager.removeElements(this, items);
    }
    
    @ZenCodeType.Method
    public void remove(MCTag<T> tag) {
        
        remove(tag.getElements());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    public boolean exists() {
        
        return manager.exists(id.toString());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("elements")
    public List<T> getElements() {
        
        return manager.getElementsInTag(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CONTAINS)
    public boolean contains(T element) {
        
        return getElements().contains(element);
    }
    
    @Override
    public String getCommandString() {
        
        return "<tag:" + manager.getTagFolder() + ":" + id + ">";
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public ResourceLocation id() {
        
        return id;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("manager")
    public ITagManager<T> manager() {
        
        return manager;
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(MCTag<T> other) {
        
        return id.equals(other.id) && manager.equals(other.manager);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public Many<MCTag<T>> withAmount(int amount) {
        
        return new Many<>(this, amount, MCTag::getCommandString);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public Many<MCTag<T>> asTagWithAmount() {
        
        return withAmount(1);
    }
    
    /**
     * Use the manager directly if possible, as then you can work typed.
     */
    public Tag<T> getInternal() {
        
        return manager.getInternal(this);
    }
    
    /**
     * Only used to make it easier if for some reason you cannot work typed, to not always have to cast to Raw.
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public Tag getInternalRaw() {
        
        return getInternal();
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        MCTag<?> mcTag = (MCTag<?>) o;
        
        if(!id.equals(mcTag.id)) {
            return false;
        }
        return manager.equals(mcTag.manager);
    }
    
    @Override
    public int hashCode() {
        
        int result = id.hashCode();
        result = 31 * result + manager.hashCode();
        return result;
    }
    
    @Nonnull
    @Override
    public Iterator<T> iterator() {
        
        return getElements().iterator();
    }
    
}
