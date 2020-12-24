package com.blamejared.crafttweaker.impl.tag;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker.impl.tag.manager.TagManager;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Arrays;
import java.util.List;

/**
 * A reference to a Tag object.
 * Note that this tag may not exist in the game already, such as when you create new tags.
 * See the {@link MCTag#exists()} Method on whether or not this tag already exists.
 * <p>
 * A tag will be created as soon as you add
 *
 * @param <T> The elements within this tag.
 */
@ZenRegister
@Document("vanilla/api/tags/MCTag")
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public final class MCTag<T> implements CommandStringDisplayable {
    
    private final ResourceLocation id;
    private final TagManager<T> manager;
    
    public MCTag(ResourceLocation id, TagManager<T> manager) {
        this.id = id;
        this.manager = manager;
    }
    
    @SafeVarargs
    @ZenCodeType.Method
    public final void add(T... items) {
        add(Arrays.asList(items));
    }
    
    @ZenCodeType.Method
    public void add(List<T> items) {
        manager.addElements(this, items);
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
    @ZenCodeType.Getter("manager")
    public TagManager<T> getManager() {
        return manager;
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
    public ResourceLocation getId() {
        return id;
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean equals(MCTag<T> other) {
        return id.equals(other.id) && manager.equals(other.manager);
    }
    
    public ResourceLocation getIdInternal() {
        return id;
    }
    
    /**
     * Use the manager directly if possible, as then you can work typed.
     */
    public ITag<?> getInternal() {
        return manager.getInternal(this);
    }
    
    /**
     * Only used to make it easier if for some reason you cannot work typed, to not always have to cast to Raw.
     */
    @SuppressWarnings({"rawtypes", "unused"})
    public final ITag getInternalRaw() {
        return getInternal();
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        return getCommandString();
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        MCTag<?> mcTag = (MCTag<?>) o;
        
        if(!id.equals(mcTag.id))
            return false;
        return manager.equals(mcTag.manager);
    }
    
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + manager.hashCode();
        return result;
    }
}
