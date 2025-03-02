package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/BlockPredicate")
@NativeTypeRegistration(value = BlockPredicate.class, zenCodeName = "crafttweaker.api.predicate.BlockPredicate")
public final class ExpandBlockPredicate {
    
    @ZenCodeType.StaticExpansionMethod
    public static BlockPredicate.Builder create() {
        
        return BlockPredicate.Builder.block();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static BlockPredicate.Builder create(final Block... blocks) {
        
        return create().of(blocks);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static BlockPredicate.Builder create(final KnownTag<Block> tag) {
        
        //noinspection RedundantTypeArguments
        return create().of( tag.<TagKey<Block>>getTagKey());
    }
    
    @ZenCodeType.Method
    public static boolean matches(BlockPredicate internal, ServerLevel level, BlockPos pos) {
        
        return internal.matches(level, pos);
    }
    
}
