package com.blamejared.crafttweaker.impl.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.google.common.base.Preconditions;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Objects;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.MCEntityType")
@Document("vanilla/api/entities/MCEntityType")
@ZenWrapper(wrappedClass = "net.minecraft.entity.EntityType", displayStringFormat = "%s.getCommandString()")
public class MCEntityType implements CommandStringDisplayable {
    
    private final EntityType<?> internal;
    
    public MCEntityType(@Nonnull EntityType<?> internal) {
        this.internal = Objects.requireNonNull(internal);
    }
    
    @ZenCodeType.Getter("classification")
    public EntityClassification getClassification() {
        return internal.getClassification();
    }
    
    
    @ZenCodeType.Getter("serializable")
    public boolean isSerializable() {
        return getInternal().isSerializable();
    }
    
    @ZenCodeType.Getter("summonable")
    public boolean isSummonable() {
        return getInternal().isSummonable();
    }
    
    @ZenCodeType.Getter("immuneToFire")
    public boolean isImmuneToFire() {
        return getInternal().isImmuneToFire();
    }
    
    @ZenCodeType.Getter("translationKey")
    public String getTranslationKey() {
        return getInternal().getTranslationKey();
    }
    
    @ZenCodeType.Getter("name")
    public String getName() {
        return getInternal().getName().getString();
    }
    
    @ZenCodeType.Getter("lootTable")
    public String getLootTable() {
        return getInternal().getLootTable().toString();
    }
    
    @ZenCodeType.Getter("width")
    public float getWidth() {
        return getInternal().getWidth();
    }
    
    @ZenCodeType.Getter("height")
    public float getHeight() {
        return getInternal().getHeight();
    }
    
    @ZenCodeType.Getter("commandString")
    @Override
    public String getCommandString() {
        return "<entityType:" + internal.getRegistryName() + ">";
    }
    
    public EntityType<?> getInternal() {
        return internal;
    }
    
    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        MCEntityType that = (MCEntityType) o;
        
        return internal.equals(that.internal);
    }
    
    @Override
    public int hashCode() {
        return internal.hashCode();
    }
}
