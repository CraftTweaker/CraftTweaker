package com.blamejared.crafttweaker.api.tag.type;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.ingredient.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.openzen.zencode.java.ZenCodeType;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ItemTag extends KnownTag<Item> implements IIngredient {
    
    private Ingredient ingredient;
    
    public ItemTag(@Nonnull ResourceLocation id, @Nonnull KnownTagManager<Item> manager) {
        
        super(id, manager);
    }
    
    @ZenCodeType.Method
    public void add(List<IItemStack> items) {
        
        this.add(items.stream()
                .map(IItemStack::getDefinition)
                .toArray(Item[]::new));
    }
    
    @Override
    public boolean matches(IItemStack stack, boolean ignoreDamage) {
        
        if(!ignoreDamage) {
            return this.ingredient.test(stack.getInternal());
        }
        return Arrays.stream(getItems()).anyMatch(item -> item.matches(stack, true));
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        
        if(this.ingredient == null || this.ingredient.isEmpty()) {
            final TagKey<Item> internalTag = getTagKey();
            if(internalTag == null) {
                CraftTweakerAPI.LOGGER.warn("Tag '{}' does not exist, replacing with empty IIngredient", getCommandString());
                this.ingredient = Ingredient.EMPTY;
            } else {
                this.ingredient = Ingredient.of(internalTag);
            }
        }
        return this.ingredient;
    }
    
    @Override
    public IItemStack[] getItems() {
        
        return Arrays.stream(asVanillaIngredient().getItems())
                .map(Services.PLATFORM::createMCItemStack)
                .toArray(IItemStack[]::new);
    }
    
    
}
