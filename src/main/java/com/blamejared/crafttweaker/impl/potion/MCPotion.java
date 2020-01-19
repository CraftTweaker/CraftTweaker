package com.blamejared.crafttweaker.impl.potion;


import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.potion.Potion;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.potion.MCPotion")
@Document("vanilla/api/potions/MCPotion")
@ZenWrapper(wrappedClass = "net.minecraft.potion.Potion", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
public class MCPotion implements CommandStringDisplayable {
    
    private final Potion internal;
    
    public MCPotion(Potion internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public String getNamePrefixed(String name) {
        return internal.getNamePrefixed(name);
    }
    
    @ZenCodeType.Getter("effects")
    public List<MCEffectInstance> getEffects() {
        return internal.getEffects().stream().map(MCEffectInstance::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Getter("hasInstantEffect")
    public boolean hasInstantEffect() {
        return internal.hasInstantEffect();
    }
    
    public Potion getInternal() {
        return internal;
    }

    @Override
    public String getCommandString() {
        return "<potion:" + internal.getRegistryName() + ">";
    }
}
