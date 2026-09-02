package com.blamejared.crafttweaker.api.item;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.component.ComponentAccess;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.IntData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.api.ingredient.transformer.IIngredientTransformer;
import com.blamejared.crafttweaker.api.ingredient.transformer.IngredientTransformers;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientIItemStack;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.api.util.random.Percentaged;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPatterns;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.item.IItemStack")
@Document("vanilla/api/item/IItemStack")
public interface IItemStack extends IIngredient, IIngredientWithAmount, DataComponentHolder, ComponentAccess<IItemStack> {
    
    ResourceLocation INGREDIENT_ID = CraftTweakerConstants.rl("iitemstack");
    
    Codec<IItemStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.CODEC.fieldOf("item").forGetter(IItemStack::getInternal),
            Codec.BOOL.fieldOf("mutable").forGetter(IItemStack::isMutable),
            IngredientConditions.CODEC.optionalFieldOf("conditions", IngredientConditions.EMPTY)
                    .forGetter(IItemStack::conditions),
            IngredientTransformers.CODEC.optionalFieldOf("transformers", IngredientTransformers.EMPTY)
                    .forGetter(IItemStack::transformers)
    ).apply(instance, IItemStack::of));
    
    StreamCodec<RegistryFriendlyByteBuf, IItemStack> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            IItemStack::getInternal,
            ByteBufCodecs.BOOL,
            IItemStack::isMutable,
            IngredientConditions.STREAM_CODEC,
            IItemStack::conditions,
            IngredientTransformers.STREAM_CODEC,
            IItemStack::transformers,
            IItemStack::of
    );
    
    Codec<IItemStack> OPTIONAL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ItemStack.OPTIONAL_CODEC.fieldOf("item").forGetter(IItemStack::getInternal),
            Codec.BOOL.fieldOf("mutable").forGetter(IItemStack::isMutable),
            IngredientConditions.CODEC.optionalFieldOf("conditions", IngredientConditions.EMPTY)
                    .forGetter(IItemStack::conditions),
            IngredientTransformers.CODEC.optionalFieldOf("transformers", IngredientTransformers.EMPTY)
                    .forGetter(IItemStack::transformers)
    ).apply(instance, IItemStack::of));
    
    StreamCodec<RegistryFriendlyByteBuf, IItemStack> OPTIONAL_STREAM_CODEC = StreamCodec.composite(
            ItemStack.OPTIONAL_STREAM_CODEC,
            IItemStack::getInternal,
            ByteBufCodecs.BOOL,
            IItemStack::isMutable,
            IngredientConditions.STREAM_CODEC,
            IItemStack::conditions,
            IngredientTransformers.STREAM_CODEC,
            IItemStack::transformers,
            IItemStack::of
    );
    
    @ZenCodeType.Field
    String CRAFTTWEAKER_DATA_KEY = "CraftTweakerData";
    
    @ZenCodeType.Field
    ResourceLocation BASE_ATTACK_DAMAGE_ID = Item.BASE_ATTACK_DAMAGE_ID;
    
    @ZenCodeType.Field
    ResourceLocation BASE_ATTACK_SPEED_ID = Item.BASE_ATTACK_SPEED_ID;
    
    static IItemStack empty() {
        
        return IItemStackConstants.EMPTY_STACK.get();
    }
    
    static IItemStack of(final ItemLike item) {
        
        return of(new ItemStack(item));
    }
    
    static IItemStack of(final ItemStack stack) {
        
        return Services.PLATFORM.createItemStack(stack, IngredientConditions.EMPTY, IngredientTransformers.EMPTY);
    }
    
    static IItemStack of(final ItemStack stack, final IngredientConditions conditions, final IngredientTransformers transformers) {
        
        return Services.PLATFORM.createItemStack(stack, conditions, transformers);
    }
    
    static IItemStack of(final ItemStack stack, final boolean mutable) {
        
        return mutable ? ofMutable(stack) : of(stack);
    }
    
    static IItemStack ofMutable(final ItemStack stack) {
        
        return Services.PLATFORM.createItemStackMutable(stack, IngredientConditions.EMPTY, IngredientTransformers.EMPTY);
    }
    
    static IItemStack ofMutable(final ItemStack stack, final IngredientConditions conditions, final IngredientTransformers transformers) {
        
        return Services.PLATFORM.createItemStackMutable(stack, conditions, transformers);
    }
    
    static IItemStack of(final ItemStack stack, final boolean mutable, final IngredientConditions conditions, final IngredientTransformers transformers) {
        
        return mutable ? ofMutable(stack, conditions, transformers) : of(stack, conditions, transformers);
    }
    
    /**
     * Creates a copy
     */
    @ZenCodeType.Method
    IItemStack copy();
    
    /**
     * Gets the registry name for the Item in this IItemStack
     *
     * @return registry name of the Item this IItemStack represents
     */
    @ZenCodeType.Getter("registryName")
    default ResourceLocation getRegistryName() {
        
        return BuiltInRegistries.ITEM.getKey(getInternal().getItem());
    }
    
    /**
     * Gets owning mod for the Item in this IItemStack
     *
     * @return owning mod of the Item this IItemStack represents
     */
    @ZenCodeType.Getter("owner")
    default String getOwner() {
        
        return getRegistryName().getNamespace();
    }
    
    /**
     * Returns if the ItemStack is empty
     *
     * @return true if empty, false if not
     */
    @Override
    default boolean isEmpty() {
        
        return getInternal().isEmpty();
    }
    
    @ZenCodeType.Getter("prototype")
    default DataComponentMap getPrototype() {
        
        return getInternal().getPrototype();
    }
    
    @ZenCodeType.Getter("componentsPatch")
    default DataComponentPatch getComponentsPatch() {
        
        return getInternal().getComponentsPatch();
    }
    
    @ZenCodeType.Method
    default <T> IItemStack with(DataComponentType<T> type, @ZenCodeType.Nullable T value) {
        
        return modify(itemStack -> itemStack.set(type, value));
    }
    
    // The same as remove but with a better name for chaining
    @ZenCodeType.Method
    default <T> IItemStack without(DataComponentType<T> type) {
        
        return remove(type);
    }
    
    @ZenCodeType.Method
    default IItemStack withoutComponents() {
        
        return modify(itemStack -> {
            
            for(TypedDataComponent<?> component : itemStack.getComponents()) {
                itemStack.remove(component.type());
            }
        });
    }
    
    @ZenCodeType.Method
    default IItemStack withJsonComponent(DataComponentType type, @ZenCodeType.Nullable IData value) {
        
        return modify(itemStack -> {
            if(value == null) {
                itemStack.remove(type);
            } else {
                Codec<?> codec = type.codecOrThrow();
                DataResult<? extends Pair<?, IData>> decode = codec.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
                itemStack.set(type, decode.getOrThrow().getFirst());
            }
        });
    }
    
    @ZenCodeType.Method
    default IItemStack withJsonComponents(IData value) {
        
        return modify(itemStack -> {
            DataResult<Pair<DataComponentPatch, IData>> decoded = DataComponentPatch.CODEC.decode(IDataOps.INSTANCE.withTagAddingRegistryAccess(), value);
            Pair<DataComponentPatch, IData> pair = decoded.getOrThrow();
            itemStack.applyComponents(pair.getFirst());
        });
    }
    
    @ZenCodeType.Method
    default <T, U> IItemStack update(DataComponentType<T> type, T defaultValue, U data, BiFunction<T, U, T> operator) {
        
        return modify(itemStack -> {
            itemStack.update(type, defaultValue, data, operator);
        });
    }
    
    @ZenCodeType.Method
    default <T> IItemStack update(DataComponentType<T> type, T defaultValue, UnaryOperator<T> operator) {
        
        return modify(itemStack -> itemStack.update(type, defaultValue, operator));
    }
    
    @ZenCodeType.Method
    default <T> IItemStack remove(DataComponentType<T> type) {
        
        return modify(itemStack -> itemStack.remove(type));
    }
    
    @ZenCodeType.Method
    default IItemStack applyComponents(DataComponentMap map) {
        
        return modify(itemStack -> itemStack.applyComponents(map));
    }
    
    @ZenCodeType.Method
    default IItemStack applyComponents(DataComponentPatch patch) {
        
        return modify(itemStack -> itemStack.applyComponents(patch));
    }
    
    @ZenCodeType.Method
    default IItemStack applyComponentsAndValidate(DataComponentPatch patch) {
        
        return modify(itemStack -> itemStack.applyComponentsAndValidate(patch));
    }
    
    /**
     * Gets the display name of the ItemStack
     *
     * @return formatted display name of the ItemStack.
     */
    @ZenCodeType.Getter("displayName")
    default Component getDisplayName() {
        
        return getInternal().getDisplayName();
    }
    
    /**
     * Gets the hover name of the ItemStack.
     *
     * <p>This will give the raw name without the formatting that 'displayName' applies. </p>
     *
     * @return The hover name of the ItemStack.
     */
    @ZenCodeType.Getter("hoverName")
    default Component getHoverName() {
        
        return getInternal().getHoverName();
    }
    
    /**
     * Returns true if this ItemStack has a foil effect.
     *
     * Foil is the glint / effect that is added to enchanted ItemStacks (and other items).
     *
     * @return true if this ItemStack has a foil effect.
     */
    @ZenCodeType.Getter("hasFoil")
    default boolean hasFoil() {
        
        return getInternal().hasFoil();
    }
    
    /**
     * Can this ItemStack be enchanted?
     *
     * @return true if this ItemStack can be enchanted.
     */
    @ZenCodeType.Getter("isEnchantable")
    default boolean isEnchantable() {
        
        return getInternal().isEnchantable();
    }
    
    /**
     * Gets the amount of Items in the ItemStack
     *
     * @return ItemStack's amount
     */
    @ZenCodeType.Getter("amount")
    default int amount() {
        
        return getInternal().getCount();
    }
    
    /**
     * Sets the amount of the ItemStack
     *
     * @param amount new amount
     *
     * @docParam amount 3
     */
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MUL)
    default IItemStack withAmount(int amount) {
        
        return modify(itemStack -> itemStack.setCount(amount));
    }
    
    /**
     * Grows this IItemStack's stack size by the given amount, or 1 if no amount is given.
     *
     * @param amount The amount to grow by.
     *
     * @return This IItemStack if mutable, a new one with the new amount otherwise.
     *
     * @docParam amount 2
     */
    @ZenCodeType.Method
    default IItemStack grow(@ZenCodeType.OptionalInt(1) int amount) {
        
        return withAmount(amount() + amount);
    }
    
    /**
     * Shrinks this IItemStack's stack size by the given amount, or 1 if no amount is given.
     *
     * @param amount The amount to shrink by.
     *
     * @return This IItemStack if mutable, a new one with the new amount otherwise.
     *
     * @docParam amount 2
     */
    @ZenCodeType.Method
    default IItemStack shrink(@ZenCodeType.OptionalInt(1) int amount) {
        
        return withAmount(amount() - amount);
    }
    
    /**
     * Returns if the ItemStack can have an amount greater than 1
     * I.E Swords and tools are not stackable, sticks are.
     *
     * @return true if this ItemStack can contain more than one item.
     */
    @ZenCodeType.Getter("stackable")
    default boolean isStackable() {
        
        return getInternal().isStackable();
    }
    
    /**
     * Gets the Attributes and the AttributeModifiers on this IItemStack for the given EquipmentSlot
     *
     * @param slotType The slot to get the Attributes for.
     *
     * @return A Map of Attribute to a List of AttributeModifier for the given EquipmentSlot.
     *
     * @docParam slotType <constant:minecraft:equipmentslot:chest>
     */
    @ZenCodeType.Method
    default Map<Attribute, List<AttributeModifier>> getAttributes(EquipmentSlot slotType) {
        
        // I don't think we expose Collection, so just convert it to a list.
        Map<Attribute, List<AttributeModifier>> ret = new HashMap<>();
        getInternal().forEachModifier(slotType, (attributeHolder, attributeModifier) -> ret.computeIfAbsent(attributeHolder.value(), attribute -> new ArrayList<>())
                .add(attributeModifier));
        return ret;
    }
    
    /**
     * Returns if the ItemStack is damageable
     * I.E Swords and tools are damageable, sticks are not.
     *
     * @return true if this ItemStack can take damage.
     */
    @ZenCodeType.Getter("damageableItem")
    default boolean isDamageableItem() {
        
        return getInternal().isDamageableItem();
    }
    
    /**
     * Returns if the ItemStack is damaged
     * I.E a Swords that is no at full durability is damaged.
     *
     * @return true if this ItemStack is damaged.
     */
    @ZenCodeType.Getter("damaged")
    default boolean isDamaged() {
        
        return getInternal().isDamaged();
    }
    
    /**
     * Returns the max damage of the ItemStack
     * This is the max durability of the ItemStack.
     *
     * @return The ItemStack's max durability.
     */
    @ZenCodeType.Getter("maxDamage")
    default int getMaxDamage() {
        
        return getInternal().getMaxDamage();
    }
    
    /**
     * Returns the unlocalized Name of the Item in the ItemStack
     *
     * @return the unlocalized name.
     */
    @ZenCodeType.Getter("descriptionId")
    default String getDescriptionId() {
        
        return getInternal().getDescriptionId();
    }
    
    @Override
    default boolean matches(IItemStack stack) {
        
        return ItemStackUtil.areStacksTheSame(this.getInternal(), stack.getInternal(), this.conditions()) && this.conditions()
                .test(stack);
    }
    
    @Override
    default String getCommandString() {
        
        return ItemStackUtil.getCommandString(this.getInternal(), this.isMutable());
    }
    
    /**
     * Gets the use duration of the ItemStack for the given entity
     *
     * @return use duration
     *
     * @docParam entity entity
     */
    @ZenCodeType.Method
    default int getUseDuration(LivingEntity entity) {
        
        return getInternal().getUseDuration(entity);
    }
    
    /**
     * Returns true if this stack is considered a crossbow item
     *
     * @return true if stack is a crossbow
     */
    @ZenCodeType.Getter("useOnRelease")
    default boolean useOnRelease() {
        
        return getInternal().useOnRelease();
    }
    
    @ZenCodeType.Getter("burnTime")
    default int getBurnTime() {
        
        return Services.EVENT.getBurnTime(this);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("potPatternId")
    @ZenCodeType.Nullable
    default ResourceKey<DecoratedPotPattern> getPotPatternId() {
        
        return DecoratedPotPatterns.getPatternFromItem(getInternal().getItem());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("potPattern")
    @ZenCodeType.Nullable
    default DecoratedPotPattern getPotPattern() {
        
        return BuiltInRegistries.DECORATED_POT_PATTERN.get(this.getPotPatternId());
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Operator(ZenCodeType.OperatorType.MOD)
    default Percentaged<IItemStack> percent(double percentage) {
        
        return new Percentaged<>(this, percentage / 100.0D, iItemStack -> iItemStack.getCommandString() + " % " + percentage);
    }
    
    @ZenCodeType.Caster(implicit = true)
    default Percentaged<IItemStack> asWeightedItemStack() {
        
        return percent(100.0D);
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("definition")
    @ZenCodeType.Caster(implicit = true)
    default Item getDefinition() {
        
        return getInternal().getItem();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    default ItemLike asItemLike() {
        
        return getInternal().getItem();
    }
    
    @ZenCodeType.Method
    IItemStack asMutable();
    
    @ZenCodeType.Method
    IItemStack asImmutable();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isImmutable")
    boolean isImmutable();
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isMutable")
    default boolean isMutable() {
        
        return !isImmutable();
    }
    
    /**
     * Gets the internal {@link ItemStack} for this IItemStack.
     *
     * @return internal ItemStack
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    ItemStack getInternal();
    
    @Override
    default Ingredient asVanillaIngredient() {
        
        if(getInternal().isEmpty()) {
            return Ingredient.EMPTY;
        }
        
        return IngredientIItemStack.ingredient(this);
    }
    
    @ZenCodeType.Method
    default ItemStack getImmutableInternal() {
        
        return getInternal().copy();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Caster(implicit = true)
    default IIngredientWithAmount asIIngredientWithAmount() {
        
        return this;
    }
    
    @Override
    default IItemStack ingredient() {
        
        return this;
    }
    
    @Override
    default IData asIData() {
        
        final IData data = IIngredient.super.asIData();
        assert data instanceof MapData;
        data.put("count", new IntData(this.amount()));
        return data;
    }
    
    @Override
    default IIngredient transform(IIngredientTransformer transformer) {
        
        return modifyThis(iItemStack -> iItemStack.transformers().add(transformer));
    }
    
    @Override
    default IIngredient condition(IIngredientCondition condition) {
        
        return modifyThis(iItemStack -> iItemStack.conditions().add(condition));
    }
    
    IItemStack modify(Consumer<ItemStack> stackModifier);
    
    IItemStack modifyThis(Consumer<IItemStack> modifier);
    
    @Override
    default @NotNull DataComponentMap getComponents() {
        
        return getInternal().getComponents();
    }
    
    // Does not need to be exposed
    @Override
    default <T> T get(@NotNull DataComponentType<? extends T> type) {
        
        return getInternal().get(type);
    }
    
    @Override
    default <T> @NotNull T getOrDefault(@NotNull DataComponentType<? extends T> type, @NotNull T value) {
        
        return getInternal().getOrDefault(type, value);
    }
    
    @Override
    default boolean has(@NotNull DataComponentType<?> type) {
        
        return getInternal().has(type);
    }
    
    @Override
    default <U> IItemStack _without(DataComponentType<U> componentType) {
        
        return without(componentType);
    }
    
    @Override
    default <U> IItemStack _with(DataComponentType<U> componentType, @Nullable U value) {
        
        return with(componentType, value);
    }
    
    @Override
    default <U> U _get(DataComponentType<? extends U> componentType) {
        
        return get(componentType);
    }
    
    @Override
    default <U> boolean _has(DataComponentType<U> componentType) {
        
        return has(componentType);
    }
    
}
