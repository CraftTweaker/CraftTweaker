package com.blamejared.crafttweaker.api.tag.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker.api.tag.manager.type.UnknownTagManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

@ZenRegister
@Document("vanilla/api/tag/type/UnknownTag")
@ZenCodeType.Name("crafttweaker.api.tag.type.UnknownTag")
@SuppressWarnings("ClassCanBeRecord")
public class UnknownTag implements MCTag, Iterable<ResourceLocation> {
    
    @Nonnull
    private final ResourceLocation id;
    @Nonnull
    private final UnknownTagManager manager;
    
    public UnknownTag(@Nonnull ResourceLocation id, @Nonnull UnknownTagManager manager) {
        
        this.id = id;
        this.manager = manager;
    }
    
    @ZenCodeType.Method
    public final void add(ResourceLocation... elements) {
        
        manager().addElements(this, elements);
    }
    
    @ZenCodeType.Method
    public final void remove(ResourceLocation... elements) {
        
        manager().removeElements(this, elements);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("exists")
    public boolean exists() {
        
        return manager().exists(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("elements")
    public List<ResourceLocation> elements() {
        
        return manager().elements(this);
    }
    
    public boolean contains(ResourceLocation id) {
        
        return elements().contains(id);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("id")
    public ResourceLocation id() {
        
        return id;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("manager")
    public UnknownTagManager manager() {
        
        return manager;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    public Many<UnknownTag> withAmount(int amount) {
        
        return new Many<>(this, amount, ts -> ts.getCommandString() + " * " + amount);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    public Many<UnknownTag> asTagWithAmount() {
        
        return withAmount(1);
    }
    
    @Override
    @ZenCodeType.Caster(implicit = true)
    public String toString() {
        
        return getCommandString();
    }
    
    @Override
    public String getCommandString() {
        
        return "<tag:" + manager().tagFolder() + ":" + id() + ">";
    }
    
    public Tag<Holder<?>> getInternal() {
        
        return manager.getInternal(this);
    }
    
    public TagKey<?> getTagKey() {
        
        return TagKey.create(GenericUtil.uncheck(manager().resourceKey()), this.id());
    }
    
    @Nonnull
    @Override
    public Iterator<ResourceLocation> iterator() {
        
        return elements().iterator();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        UnknownTag mcTag = (UnknownTag) o;
        
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
    
}
