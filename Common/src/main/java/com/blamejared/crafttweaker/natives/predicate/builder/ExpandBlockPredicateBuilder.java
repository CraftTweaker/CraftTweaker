package com.blamejared.crafttweaker.natives.predicate.builder;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.base.IData;
import com.blamejared.crafttweaker.api.tag.MCTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/builder/BlockPredicateBuilder")
@NativeTypeRegistration(value = BlockPredicate.Builder.class, zenCodeName = "crafttweaker.api.predicate.builder.BlockPredicateBuilder")
public class ExpandBlockPredicateBuilder {
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder blocks(final BlockPredicate.Builder internal, final Block... blocks) {
        
        return internal.of(blocks);
    }
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder tag(final BlockPredicate.Builder internal, final MCTag<Block> tag) {
        
        return internal.of(tag.getInternal());
    }
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder nbt(final BlockPredicate.Builder internal, final MapData tag) {
        
        return internal.hasNbt(tag.getInternal());
    }
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder nbt(final BlockPredicate.Builder internal, final IData tag) {
        
        return nbt(internal, new MapData(tag.asMap()));
    }
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder properties(final BlockPredicate.Builder internal, final StatePropertiesPredicate predicate) {
        
        return internal.setProperties(predicate);
    }
    
    @ZenCodeType.Method
    public static BlockPredicate.Builder properties(final BlockPredicate.Builder internal, final StatePropertiesPredicate.Builder predicate) {
        
        return properties(internal, predicate.build());
    }
    
    @ZenCodeType.Method
    public static BlockPredicate build(final BlockPredicate.Builder internal) {
        
        return internal.build();
    }
    
}
