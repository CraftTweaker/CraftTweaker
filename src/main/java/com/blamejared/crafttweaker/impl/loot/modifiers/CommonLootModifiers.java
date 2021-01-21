package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.Util;
import net.minecraftforge.common.util.Lazy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.CommonLootModifiers")
@Document("vanilla/api/loot/modifiers/CommonLootModifiers")
public final class CommonLootModifiers {
    private static final Lazy<ILootModifier> IDENTITY = Lazy.concurrentOf(() -> (loot, context) -> loot);
    private static final Lazy<ILootModifier> LOOT_CLEARING_MODIFIER = Lazy.concurrentOf(() -> (loot, context) -> new ArrayList<>());

    // Addition methods
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack) {
        return (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(stack));
    }

    @ZenCodeType.Method
    public static ILootModifier addAll(final IItemStack... stacks) {
        return (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.addAll(Arrays.asList(stacks)));
    }

    // Replacement methods
    @ZenCodeType.Method
    public static ILootModifier replaceWith(final IItemStack target, final IItemStack replacement) {
        return (loot, context) -> replacing(loot.stream(), target, replacement).collect(Collectors.toList());
    }

    @ZenCodeType.Method
    public static ILootModifier replaceAllWith(final Map<IItemStack, IItemStack> replacementMap) {
        return chaining(replacementMap.entrySet().stream().map(it -> replaceWith(it.getKey(), it.getValue())));
    }

    // Removal methods
    @ZenCodeType.Method
    public static ILootModifier remove(final IItemStack target) {
        return replaceWith(target, null);
    }

    @ZenCodeType.Method
    public static ILootModifier removeAll(final IItemStack... targets) {
        return chaining(Arrays.stream(targets).map(CommonLootModifiers::remove));
    }

    @ZenCodeType.Method
    public static ILootModifier clearLoot() {
        return LOOT_CLEARING_MODIFIER.get();
    }

    private static ILootModifier chaining(final Stream<ILootModifier> chain) {
        return chain.reduce(IDENTITY.get(), (first, second) -> (loot, context) -> second.applyModifier(first.applyModifier(loot, context), context));
    }

    private static Stream<IItemStack> replacing(final Stream<IItemStack> stream, final IItemStack from, final IItemStack to) {
        final Stream<IItemStack> newStream = stream.map(it -> from.matches(it)? to : it);
        return to == null? newStream.filter(Objects::nonNull) : newStream;
    }
}
