package com.blamejared.crafttweaker.api.ingredient.vanilla;

import com.blamejared.crafttweaker.api.ingredient.vanilla.serializer.CraftTweakerVanillaIngredientSerializer;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.CraftTweakerVanillaIngredient;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.mojang.serialization.Codec;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Objects;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
class DelegatingCustomIngredient<T extends CraftTweakerVanillaIngredient> extends Ingredient {
    
    public static final Function<CraftTweakerVanillaIngredientSerializer<? extends CraftTweakerVanillaIngredient>, Codec<DelegatingCustomIngredient<? extends CraftTweakerVanillaIngredient>>> CODEC_CACHE = Util.memoize((internal) -> internal
            .codec()
            .xmap(CraftTweakerIngredients.Ingredients::of, GenericUtil.uncheckFunc(DelegatingCustomIngredient::internal)));
    
    private final T internal;
    
    DelegatingCustomIngredient(T internal) {
        
        super(internal.values(), () -> CraftTweakerIngredients.Types.of(internal.serializer()));
        this.internal = internal;
    }
    
    @Override
    public boolean test(ItemStack stack) {
        
        return internal.test(stack);
    }
    
    @Override
    public ItemStack[] getItems() {
        
        return internal.getMatchingStacks().toArray(ItemStack[]::new);
    }
    
    @Override
    public boolean isSimple() {
        
        return !internal.requiresTesting();
    }
    
    @Override
    public boolean isEmpty() {
        
        return internal.isEmpty();
    }
    
    public static <T extends CraftTweakerVanillaIngredient> IngredientType<DelegatingCustomIngredient<T>> ingredientType(CraftTweakerVanillaIngredientSerializer<T> serializer) {
        
        Codec<DelegatingCustomIngredient<T>> codec = GenericUtil.uncheck(CODEC_CACHE.apply(serializer));
        return new IngredientType<>(codec, codec);
    }
    
    public T internal() {
        
        return internal;
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(getClass() != o.getClass()) {
            return false;
        }
        if(!super.equals(o)) {
            return false;
        }
        DelegatingCustomIngredient<?> that = (DelegatingCustomIngredient<?>) o;
        return Objects.equals(internal, that.internal);
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(internal);
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("DelegatingCustomIngredient{");
        sb.append("internal=").append(internal);
        sb.append('}');
        return sb.toString();
    }
    
}
