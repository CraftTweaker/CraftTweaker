package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.loot.CTLootManager;
import com.blamejared.crafttweaker.impl.loot.conditions.CTLootConditionBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.BlockStatePropertyLootConditionTypeBuilder;
import com.blamejared.crafttweaker.impl.loot.conditions.vanilla.MatchToolLootConditionBuilder;
import com.blamejared.crafttweaker.impl.predicate.StatePropertiesPredicate;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.block.Block;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Consumer;

@Document("vanilla/api/blocks/MCBlock")
@ZenCodeType.Expansion("crafttweaker.api.blocks.MCBlock")
@ZenRegister
public final class ModifierBlockExpansion {
    @ZenCodeType.Method
    public static void addDrop(final Block internal, final String uniqueId, final IItemStack stack) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addDrops(final Block internal, final String uniqueId, final IItemStack... stacks) {
        addLootModifier(internal, uniqueId, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addLootModifier(final Block internal, final String name, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStatePropertyLootConditionTypeBuilder.class, blockStateProps -> blockStateProps.withBlock(internal)),
                modifier);
    }
    
    @ZenCodeType.Method
    public static void addStateDrop(final Block internal, final String uniqueId, final Consumer<StatePropertiesPredicate> statePredicate, final IItemStack stack) {
        addStateLootModifier(internal, uniqueId, statePredicate, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addStateLootModifier(final Block internal, final String name, final Consumer<StatePropertiesPredicate> statePredicate, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create().add(BlockStatePropertyLootConditionTypeBuilder.class, bs -> bs.withBlock(internal).withStatePropertiesPredicate(statePredicate)),
                modifier
        );
    }
    
    @ZenCodeType.Method
    public static void addToolDrop(final Block internal, final String uniqueId, final IItemStack tool, final IItemStack stack) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.add(stack));
    }
    
    @ZenCodeType.Method
    public static void addToolDrops(final Block internal, final String uniqueId, final IItemStack tool, final IItemStack... stacks) {
        addToolLootModifier(internal, uniqueId, tool, CommonLootModifiers.addAll(stacks));
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final ILootModifier modifier) {
        addToolLootModifier(internal, name, tool, matchDamage, false, modifier);
    }
    
    @ZenCodeType.Method
    public static void addToolLootModifier(final Block internal, final String name, final IItemStack tool, final boolean matchDamage, final boolean matchNbt, final ILootModifier modifier) {
        CTLootManager.LOOT_MANAGER.getModifierManager().register(
                name,
                CTLootConditionBuilder.create()
                        .add(BlockStatePropertyLootConditionTypeBuilder.class, bs -> bs.withBlock(internal))
                        .add(MatchToolLootConditionBuilder.class, mt -> mt.withPredicate(item -> item.matching(tool, matchDamage, false, matchNbt))),
                modifier
        );
    }
}
