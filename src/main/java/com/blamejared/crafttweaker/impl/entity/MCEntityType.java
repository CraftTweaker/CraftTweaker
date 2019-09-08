package com.blamejared.crafttweaker.impl.entity;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import net.minecraft.entity.EntityType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.entity.MCEntityType")
public class MCEntityType {
    
    private final EntityType internal;
    
    public MCEntityType(EntityType internal) {
        this.internal = internal;
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
        return getInternal().getName().getFormattedText();
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
    
    public EntityType getInternal() {
        return internal;
    }
}
