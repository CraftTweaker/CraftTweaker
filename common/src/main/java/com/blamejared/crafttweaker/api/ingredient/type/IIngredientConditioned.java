package com.blamejared.crafttweaker.api.ingredient.type;


import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.ingredient.condition.IIngredientCondition;
import com.blamejared.crafttweaker.api.ingredient.vanilla.type.IngredientConditioned;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.ingredient.type.IngredientConditioned")
@Document("vanilla/api/ingredient/type/IngredientConditioned")
public class IIngredientConditioned<T extends IIngredient> implements IIngredient {
    
    public static final ResourceLocation ID = CraftTweakerConstants.rl("conditioned");
    
    public static final Codec<IIngredientConditioned<?>> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            IIngredient.CODEC.fieldOf("base").forGetter(IIngredientConditioned::getBaseIngredient),
            IIngredientCondition.CODEC.fieldOf("condition").forGetter(IIngredientConditioned::getCondition)
    ).apply(instance, (base, condition) -> new IIngredientConditioned<>(base, GenericUtil.uncheck(condition))));
    private final T base;
    private final IIngredientCondition<T> condition;
    
    public IIngredientConditioned(T base, IIngredientCondition<T> condition) {
        
        this.base = base;
        this.condition = condition;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        return IngredientConditioned.ingredient(this);
    }
    
    @ZenCodeType.Getter("condition")
    public IIngredientCondition<T> getCondition() {
        
        return condition;
    }
    
    @Override
    public String getCommandString() {
        
        return condition.getCommandString(base);
    }
    
    @ZenCodeType.Getter("baseIngredient")
    public T getBaseIngredient() {
        
        return base;
    }
    
    @Override
    @ZenCodeType.Method
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        return base.matches(stack, condition.ignoresDamage()) && condition.matches(stack);
    }
    
    @Override
    @ZenCodeType.Getter("items")
    public IItemStack[] getItems() {
        
        return base.getItems();
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        
        IIngredientConditioned<?> that = (IIngredientConditioned<?>) o;
        
        if(!base.equals(that.base)) {
            return false;
        }
        return condition.equals(that.condition);
    }
    
    @Override
    public int hashCode() {
        
        int result = base.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("IIngredientConditioned{");
        sb.append("base=").append(base);
        sb.append(", condition=").append(condition);
        sb.append('}');
        return sb.toString();
    }
    
}
