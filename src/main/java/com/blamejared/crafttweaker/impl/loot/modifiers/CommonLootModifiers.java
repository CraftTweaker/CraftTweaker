package com.blamejared.crafttweaker.impl.loot.modifiers;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.loot.modifiers.ILootModifier;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.util.Util;
import net.minecraftforge.common.util.Lazy;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Holds a set of implementations of {@link ILootModifier} of common usage.
 *
 * These can be used freely instead of rewriting the same code more than once. They are also guaranteed to behave
 * correctly.
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.loot.modifiers.CommonLootModifiers")
@Document("vanilla/api/loot/modifiers/CommonLootModifiers")
public final class CommonLootModifiers {
    private static final Lazy<ILootModifier> IDENTITY = Lazy.concurrentOf(() -> (loot, context) -> loot);
    private static final Lazy<ILootModifier> LOOT_CLEARING_MODIFIER = Lazy.concurrentOf(() -> (loot, context) -> new ArrayList<>());

    // Addition methods
    /**
     * Adds the given {@link IItemStack} to the drops.
     *
     * @param stack The stack to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier add(final IItemStack stack) {
        return stack.isEmpty()? IDENTITY.get() : (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.add(stack.copy()));
    }

    /**
     * Adds all the given {@link IItemStack} to the drops.
     *
     * @param stacks The stacks to add
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier addAll(final IItemStack... stacks) {
        final List<IItemStack> stacksToAdd = Arrays.stream(stacks).filter(it -> !it.isEmpty()).map(IItemStack::copy).collect(Collectors.toList());
        return (loot, context) -> Util.make(new ArrayList<>(loot), it -> it.addAll(stacksToAdd));
    }

    // Replacement methods
    /**
     * Replaces every instance of the targeted {@link IIngredient} with the replacement {@link IItemStack}.
     *
     * @param target The target to replace.
     * @param replacement The replacement to use.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceWith(final IIngredient target, final IItemStack replacement) {
        return replacement != null && replacement.isEmpty()? remove(target) : (loot, context) -> replacing(loot.stream(), target, replacement).collect(Collectors.toList());
    }

    /**
     * Replaces every instance of the targeted {@link IIngredient}s with their corresponding replacement
     * {@link IItemStack}.
     *
     * @param replacementMap A map of key-value pairs dictating the target to replace along with their replacement.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier replaceAllWith(final Map<IIngredient, IItemStack> replacementMap) {
        return chaining(replacementMap.entrySet().stream().map(it -> replaceWith(it.getKey(), it.getValue())));
    }

    // Removal methods
    /**
     * Removes every instance of the targeted {@link IIngredient} from the drops.
     *
     * @param target The {@link IIngredient} to remove.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier remove(final IIngredient target) {
        return replaceWith(target, null);
    }

    /**
     * Removes every instance of all the targeted {@link IIngredient}s from the drops.
     *
     * @param targets The {@link IIngredient}s to remove.
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier removeAll(final IIngredient... targets) {
        return chaining(Arrays.stream(targets).map(CommonLootModifiers::remove));
    }

    /**
     * Clears the entire drop list.
     *
     * @return An {@link ILootModifier} that carries out the operation.
     */
    @ZenCodeType.Method
    public static ILootModifier clearLoot() {
        return LOOT_CLEARING_MODIFIER.get();
    }

    private static ILootModifier chaining(final Stream<ILootModifier> chain) {
        return chain.reduce(IDENTITY.get(), (first, second) -> (loot, context) -> second.applyModifier(first.applyModifier(loot, context), context));
    }

    private static Stream<IItemStack> replacing(final Stream<IItemStack> stream, final IIngredient from, final IItemStack to) {
        final Stream<IItemStack> newStream = stream.map(it -> from.matches(it)? to.copy() : it);
        return to == null? newStream.filter(Objects::nonNull) : newStream;
    }
}
