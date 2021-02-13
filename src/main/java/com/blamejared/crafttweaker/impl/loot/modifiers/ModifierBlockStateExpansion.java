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

@Document("vanilla/api/blocks/MCBlockState")
@ZenCodeType.Expansion("crafttweaker.api.blocks.MCBlockState")
@ZenRegister
public final class ModifierBlockStateExpansion {
    @ZenCodeType.Method
    public static void addBlockDrop(final BlockState $this, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrop($this.getBlock(), uniqueId, stack);
    }
    
    @ZenCodeType.Method
    public static void addBlockDrops(final BlockState $this, final String uniqueId, final IItemStack stack) {
        ModifierBlockExpansion.addDrops($this.getBlock(), uniqueId, stack);
    }
    
    @ZenCodeType.Method
    public static void addBlockLootModifier(final BlockState $this, final String name, final ILootModifier modifier) {
        ModifierBlockExpansion.addLootModifier($this.getBlock(), name, modifier);
    }
    
    @ZenCodeType.Method
    public static void addTargetedDrop(final BlockState $this, final String uniqueId, final IItemStack stack) {
        addTargetedLootModifier($this, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addTargetedDrops(final BlockState $this, final String uniqueId, final IItemStack... stacks) {
        addTargetedLootModifier($this, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addTargetedLootModifier(final BlockState $this, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStateLootConditionTypeBuilder.class, blockState -> blockState.withState($this)),
                modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolDrop(final BlockState $this, final String uniqueId, final IItemStack tool, final IItemStack stack) {
        addToolLootModifier($this, uniqueId, tool, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addToolDrops(final BlockState $this, final String uniqueId, final IItemStack tool, final IItemStack... stacks) {
        addToolLootModifier($this, uniqueId, tool, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState $this, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier($this, name, tool, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState $this, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier($this, name, tool, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final BlockState $this, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStateLootConditionTypeBuilder.class, bs -> bs.withState($this))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
    
}
