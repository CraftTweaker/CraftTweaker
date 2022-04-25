package com.blamejared.crafttweaker.natives.loot.table;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.Container;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.function.Consumer;

/**
 * A loot table is used to determine what is dropped when the game needs to drop loot.
 *
 * @docParam this lootTables.getTable(<resource:minecraft:gameplay/cat_morning_gift>)
 */
@ZenRegister
@Document("vanilla/api/loot/LootTable")
@NativeTypeRegistration(value = LootTable.class, zenCodeName = "crafttweaker.api.loot.LootTable")
public final class ExpandLootTable {
    
    /**
     * Rolls this table and passes all the rolled items to the given {@code Consumer<{@link IItemStack}>}
     *
     * <p>NOTE: This method does not respect max stack sizes!</p>
     * <p>NOTE: The provided {@link LootContext} should not be reused from a loot modifier, if you want to reuse a context, look at {@link com.blamejared.crafttweaker.natives.loot.ExpandLootContextBuilder#copy(LootContext)}.</p>
     *
     * @param context       The context that will generate the loot.
     * @param stackConsumer A consumer to act on the rolled stacks.
     *
     * @docParam context new LootContextBuilder(level).withParameter<Vec3>(LootContextParams.origin(), player.position).withParameter<Entity>(LootContextParams.thisEntity(), player).create(LootContextParamSets.gift())
     * @docParam stackConsumer (stack) => {
     *
     * println(stack.commandString);
     * }
     */
    @ZenCodeType.Method
    public static void getRandomItemsRaw(LootTable internal, LootContext context, Consumer<IItemStack> stackConsumer) {
        
        internal.getRandomItemsRaw(context, itemStack -> stackConsumer.accept(Services.PLATFORM.createMCItemStack(itemStack)));
    }
    
    /**
     * Rolls this table and passes all the rolled items to the given {@code Consumer<{@link IItemStack}>}
     *
     * <p>NOTE: This method does respect max stack sizes</p>
     * <p>NOTE: The provided {@link LootContext} should not be reused from a loot modifier, if you want to reuse a context, look at {@link com.blamejared.crafttweaker.natives.loot.ExpandLootContextBuilder#copy(LootContext)}.</p>
     *
     * @param context       The context that will generate the loot.
     * @param stackConsumer A consumer to act on the rolled stacks.
     *
     * @docParam context new LootContextBuilder(level).withParameter<Vec3>(LootContextParams.origin(), player.position).withParameter<Entity>(LootContextParams.thisEntity(), player).create(LootContextParamSets.gift())
     * @docParam stackConsumer (stack) => {
     *
     * println(stack.commandString);
     * }
     */
    @ZenCodeType.Method
    public static void getRandomItems(LootTable internal, LootContext context, Consumer<IItemStack> stackConsumer) {
        
        internal.getRandomItems(context, itemStack -> stackConsumer.accept(Services.PLATFORM.createMCItemStack(itemStack)));
    }
    
    /**
     * Rolls this table and returns the rolled items in a list.
     *
     * <p>NOTE: The provided {@link LootContext} should not be reused from a loot modifier, if you want to reuse a context, look at {@link com.blamejared.crafttweaker.natives.loot.ExpandLootContextBuilder#copy(LootContext)}.</p>
     *
     * @param context The context that this loot was generated.
     *
     * @return A list containing all the rolled items.
     *
     * @docParam context new LootContextBuilder(level).withParameter<Vec3>(LootContextParams.origin(), player.position).withParameter<Entity>(LootContextParams.thisEntity(), player).create(LootContextParamSets.gift())
     */
    @ZenCodeType.Method
    public static List<IItemStack> getRandomItems(LootTable internal, LootContext context) {
        
        return internal.getRandomItems(context).stream().map(Services.PLATFORM::createMCItemStack).toList();
    }
    
    /**
     * Gets the param set that this table uses.
     *
     * @return The param set that this table uses.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("paramSet")
    public static LootContextParamSet getParamSet(LootTable internal) {
        
        return internal.getParamSet();
    }
    
    /**
     * Fills the given container with loot rolled by this table.
     *
     * @param container The container to fill.
     * @param context   The context that will generate the loot.
     *
     * @docParam container container
     */
    @ZenCodeType.Method
    public static void fill(LootTable internal, Container container, LootContext context) {
        
        internal.fill(container, context);
    }
    
}
