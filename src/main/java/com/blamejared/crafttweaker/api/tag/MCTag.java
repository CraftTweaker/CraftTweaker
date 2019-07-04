package com.blamejared.crafttweaker.api.tag;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;


@ZenRegister
@ZenCodeType.Name("crafttweaker.api.MCTag")
public class MCTag implements IIngredient {
    
    private final ResourceLocation id;
    private Tag<Item> itemTag;
    private Tag<Block> blockTag;
    private Tag<EntityType<?>> entityTypeTag;
    private Tag<Fluid> fluidTag;
    
    public MCTag(ResourceLocation id) {
        this.id = id;
    }
    
    @ZenCodeType.Getter("items")
    public IItemStack[] getItems() {
        if(itemTag == null) {
            itemTag = ItemTags.getCollection().get(id);
            if(itemTag == null) {
                CraftTweakerAPI.logError("\"" + getCommandString() + "\" is not an ItemTag!");
                return new IItemStack[0];
            }
        }
        
        List<IItemStack> returned = new ArrayList<>();
        for(Item element : itemTag.getAllElements()) {
            returned.add(new MCItemStack(new ItemStack(element)));
        }
        return returned.toArray(new IItemStack[0]);
    }
    
    //    //TODO replace this with IBlock when it exists
    //    public Object[] getBlocks() {
    //        return blockTag.getAllElements().toArray();
    //    }
    //
    //    //TODO replace this with something entity related when it exists
    //    public Object[] getEntities() {
    //        return entityTypeTag.getAllElements().toArray();
    //    }
    //
    //    //TODO replace this with IFluid when it exists
    //    public Object[] getFluids() {
    //        return fluidTag.getAllElements().toArray();
    //    }
    
    @Override
    public boolean matches(IItemStack stack) {
        if(!stack.isEmpty()) {
            for(IItemStack item : getItems()) {
                if(item.matches(stack)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Ingredient asVanillaIngredient() {
        if(itemTag == null) {
            return Ingredient.EMPTY;
        }
        return Ingredient.fromTag(itemTag);
    }
    
    @Override
    public String getCommandString() {
        return String.format("<tag:%s>", this.id);
    }
}
