package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.MCLootModifier")
@Document("vanilla/api/loot/modifiers/MCLootModifier")
public class MCLootModifier implements ILootModifier {
    private static class DummyGlobalLootModifier implements IGlobalLootModifier {
        private static final DummyGlobalLootModifier INSTANCE = new DummyGlobalLootModifier();

        private DummyGlobalLootModifier() {}

        @Override
        public List<ItemStack> apply(List<ItemStack> generatedLoot, LootContext context) {
            return generatedLoot;
        }
    }

    private final IGlobalLootModifier internal;

    public MCLootModifier(final IGlobalLootModifier internal) {
        this.internal = internal == null? DummyGlobalLootModifier.INSTANCE : internal;
    }

    public IGlobalLootModifier getInternal() {
        return this.internal;
    }

    @Override
    public List<IItemStack> applyModifier(List<IItemStack> loot, LootContext currentContext) {
        final List<ItemStack> unwrappedLoot = CraftTweakerHelper.getItemStacks(loot);
        return CraftTweakerHelper.getIItemStacks(this.internal.apply(unwrappedLoot, currentContext));
    }
}
