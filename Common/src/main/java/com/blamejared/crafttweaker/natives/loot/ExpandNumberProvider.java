package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.ScoreboardValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/loot/NumberProvider")
@NativeTypeRegistration(value = NumberProvider.class, zenCodeName = "crafttweaker.api.loot.NumberProvider")
public final class ExpandNumberProvider {
    
    @ZenCodeType.StaticExpansionMethod
    public static NumberProvider between(final float min, final float max) {
        
        return UniformGenerator.between(min, max);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static NumberProvider binomial(final int n, final int p) {
        
        return BinomialDistributionGenerator.binomial(n, p);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static NumberProvider exactly(final float value) {
        
        return ConstantValue.exactly(value);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static NumberProvider scoreboard(final LootContext.EntityTarget target, final String score, @ZenCodeType.OptionalFloat(1.0F) final float scale) {
        
        return ScoreboardValue.fromScoreboard(target, score, scale);
    }
    
}
