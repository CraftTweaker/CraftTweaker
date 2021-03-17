package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.crafttweaker.BlockStateLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/loot/modifiers/BlockStateExpansion")
@ZenCodeType.Expansion("crafttweaker.api.blocks.MCBlockState")
@ZenRegister
public final class ModifierBlockStateExpansion {
    @ZenCodeType.Method
    public static void addBlockDrop(final BlockState internal, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrop(internal.getBlock(), uniqueId, stack);
    }
    
    @ZenCodeType.Method
    public static void addBlockDrops(final BlockState internal, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrops(internal.getBlock(), uniqueId, stack);
    }
    
    @ZenCodeType.Method
    public static void addBlockLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        ModifierBlockExpansion.addLootModifier(internal.getBlock(), name, modifier);
    }
    
    @ZenCodeType.Method
    public static void addTargetedDrop(final BlockState internal, final String uniqueId, final IItemStack stack) {
        addTargetedLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addTargetedDrops(final BlockState internal, final String uniqueId, final IItemStack... stacks) {
        addTargetedLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addTargetedLootModifier(final BlockState internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStateLootConditionTypeBuilder.class, blockState -> blockState.withState(internal)),
                modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolDrop(final BlockState internal, final String uniqueId, final IItemStack tool, final IItemStack stack) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addToolDrops(final BlockState internal, final String uniqueId, final IItemStack tool, final IItemStack... stacks) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStateLootConditionTypeBuilder.class, bs -> bs.withState(internal))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
    
}
