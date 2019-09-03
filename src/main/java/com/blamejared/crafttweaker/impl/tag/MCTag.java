package com.blamejared.crafttweaker.impl.tag;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;


@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
public class MCTag implements IIngredient {
    
    private final ResourceLocation id;
    private Tag<Item> itemTag;
    private Tag<Block> blockTag;
    private Tag<EntityType<?>> entityTypeTag;
    private Tag<Fluid> fluidTag;
    
    public MCTag(ResourceLocation id) {
        this.id = id;
    }
    
    @ZenCodeType.Method
    public MCTag createItemTag() {
        CraftTweakerAPI.apply(new ActionTagCreate<>(ItemTags.getCollection(), "Item", new Tag<Item>(id, Sets.newHashSet(), false)));
        return this;
    }
    
    @ZenCodeType.Method
    public MCTag createBlockTag() {
        CraftTweakerAPI.apply(new ActionTagCreate<>(BlockTags.getCollection(), "Block", new Tag<Block>(id, Sets.newHashSet(), false)));
        return this;
    }
    
    @Override
    public IItemStack[] getItems() {
        if(getItemTag() == null) {
            CraftTweakerAPI.logError("\"" + getCommandString() + "\" is not an ItemTag!");
            return new IItemStack[0];
        }
        
        List<IItemStack> returned = new ArrayList<>();
        for(Item element : itemTag.getAllElements()) {
            returned.add(new MCItemStack(new ItemStack(element)));
        }
        return returned.toArray(new IItemStack[0]);
    }
    
    @ZenCodeType.Getter("blocks")
    public MCBlock[] getBlocks() {
        if(getBlockTag() == null) {
            CraftTweakerAPI.logError("\"" + getCommandString() + "\" is not a BlockTag!");
            return new MCBlock[0];
        }
        
        List<MCBlock> returned = new ArrayList<>();
        for(Block element : blockTag.getAllElements()) {
            returned.add(new MCBlock(element));
        }
        return returned.toArray(new MCBlock[0]);
    }
    
    @ZenCodeType.Method
    public void addItems(IItemStack... items) {
        CraftTweakerAPI.apply(new ActionTagAdd<Item>(getItemTag(), CraftTweakerHelper.getItems(items)));
    }
    
    @ZenCodeType.Method
    public void removeItems(IItemStack... items) {
        CraftTweakerAPI.apply(new ActionTagRemove<>(getItemTag(), CraftTweakerHelper.getItems(items)));
    }
    
    
    @ZenCodeType.Method
    public void addBlocks(MCBlock... blocks) {
        CraftTweakerAPI.apply(new ActionTagAdd<Block>(getBlockTag(), CraftTweakerHelper.getBlocks(blocks)));
    }
    
    @ZenCodeType.Method
    public void removeBlocks(MCBlock... blocks) {
        CraftTweakerAPI.apply(new ActionTagRemove<Block>(getBlockTag(), CraftTweakerHelper.getBlocks(blocks)));
    }
    
    
    
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
        if(getItemTag() == null) {
            return Ingredient.EMPTY;
        }
        return Ingredient.fromTag(itemTag);
    }
    
    public Tag<Item> getItemTag() {
        if(itemTag == null) {
            itemTag = ItemTags.getCollection().get(id);
        }
        return itemTag;
    }
    
    public Tag<Block> getBlockTag() {
        if(blockTag == null) {
            blockTag = BlockTags.getCollection().get(id);
        }
        return blockTag;
    }
    
    
    @Override
    public String getCommandString() {
        return String.format("<tag:%s>", this.id);
    }
}
