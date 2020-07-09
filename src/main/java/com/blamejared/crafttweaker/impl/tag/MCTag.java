package com.blamejared.crafttweaker.impl.tag;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.item.IIngredient;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagAdd;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagCreate;
import com.blamejared.crafttweaker.impl.actions.tags.ActionTagRemove;
import com.blamejared.crafttweaker.impl.blocks.MCBlock;
import com.blamejared.crafttweaker.impl.entity.MCEntityType;
import com.blamejared.crafttweaker.impl.helper.CraftTweakerHelper;
import com.blamejared.crafttweaker.impl.item.MCItemStack;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;


@ZenRegister
@ZenCodeType.Name("crafttweaker.api.tag.MCTag")
@Document("vanilla/api/tags/MCTag")
@ZenWrapper(wrappedClass = "net.minecraft.tags.Tag", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.getCommandString()")
public class MCTag implements IIngredient {
    
    private final ResourceLocation id;
    private ITag<Item> itemTag;
    private ITag<Block> blockTag;
    private ITag<EntityType<?>> entityTypeTag;
    private ITag<Fluid> fluidTag;
    
    public MCTag(ResourceLocation id) {
        this.id = id;
    }
    
    @ZenCodeType.Method
    public MCTag createItemTag() {
        CraftTweakerAPI.apply(new ActionTagCreate<>(TagCollectionManager.func_232928_e_().func_232925_b_(), "Item",  Tag.func_241286_a_(Sets.newHashSet()), id));
        return this;
    }
    
    @ZenCodeType.Method
    public MCTag createBlockTag() {
        CraftTweakerAPI.apply(new ActionTagCreate<>(TagCollectionManager.func_232928_e_().func_232923_a_(), "Block",  Tag.func_241286_a_(Sets.newHashSet()), id));
        return this;
    }
    
    @ZenCodeType.Method
    public MCTag createEntityTypeTag() {
        CraftTweakerAPI.apply(new ActionTagCreate<>(TagCollectionManager.func_232928_e_().func_232927_d_(), "EntityType",  Tag.func_241286_a_(Sets.newHashSet()), id));
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
    
    @ZenCodeType.Getter("entityTypes")
    public MCEntityType[] getEntityTypes() {
        if(getEntityTypeTag() == null) {
            CraftTweakerAPI.logError("\"" + getCommandString() + "\" is not an EntityTypeTag!");
            return new MCEntityType[0];
        }
        
        List<MCEntityType> returned = new ArrayList<>();
        for(EntityType<?> element : entityTypeTag.getAllElements()) {
            returned.add(new MCEntityType(element));
        }
        return returned.toArray(new MCEntityType[0]);
    }
    
    @ZenCodeType.Getter("firstItem")
    public IItemStack getFirstItem() {
        if(getItemTag() == null) {
            throw new IllegalArgumentException("\"" + getCommandString() + "\" is not an ItemTag!");
        }
        return getItems()[0];
    }
    
    @ZenCodeType.Getter("firstBlock")
    public MCBlock getFirstBlock() {
        if(getBlockTag() == null) {
            throw new IllegalArgumentException("\"" + getCommandString() + "\" is not a BlockTag!");
        }
        return getBlocks()[0];
    }
    
    @ZenCodeType.Getter("firstEntityType")
    public MCEntityType getFirstEntityType() {
        if(getEntityTypeTag() == null) {
            throw new IllegalArgumentException("\"" + getCommandString() + "\" is not an EntityTypeTag!");
        }
        return getEntityTypes()[0];
    }
    
    @ZenCodeType.Method
    public void addItems(IItemStack... items) {
        CraftTweakerAPI.apply(new ActionTagAdd<Item>(getItemTag(), CraftTweakerHelper.getItems(items), id));
    }
    
    @ZenCodeType.Method
    public void removeItems(IItemStack... items) {
        CraftTweakerAPI.apply(new ActionTagRemove<>(getItemTag(), CraftTweakerHelper.getItems(items), id));
    }
    
    
    @ZenCodeType.Method
    public void addBlocks(MCBlock... blocks) {
        CraftTweakerAPI.apply(new ActionTagAdd<Block>(getBlockTag(), CraftTweakerHelper.getBlocks(blocks), id));
    }
    
    @ZenCodeType.Method
    public void removeBlocks(MCBlock... blocks) {
        CraftTweakerAPI.apply(new ActionTagRemove<Block>(getBlockTag(), CraftTweakerHelper.getBlocks(blocks), id));
    }
    
    @ZenCodeType.Method
    public void addEntityTypes(MCEntityType... entities) {
        CraftTweakerAPI.apply(new ActionTagAdd<>(getEntityTypeTag(), CraftTweakerHelper.getEntityTypes(entities), id));
    }
    
    @ZenCodeType.Method
    public void removeEntityTypes(MCEntityType... entities) {
        CraftTweakerAPI.apply(new ActionTagRemove<>(getEntityTypeTag(), CraftTweakerHelper.getEntityTypes(entities), id));
    }
    
    
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
    
    
    @ZenCodeType.Getter("isItemTag")
    public boolean isItemTag() {
        return getItemTag() != null;
    }
    
    @ZenCodeType.Getter("isBlockTag")
    public boolean isBlockTag() {
        return getBlockTag() != null;
    }
    
    @ZenCodeType.Getter("isEntityTypeTag")
    public boolean isEntityTypeTag() {
        return getEntityTypeTag() != null;
    }
    
    
    @Override
    public Ingredient asVanillaIngredient() {
        return Ingredient.fromTag(getItemTag());
    }
    
    public ITag<Item> getItemTag() {
        if(itemTag == null) {
            itemTag = TagCollectionManager.func_232928_e_().func_232925_b_().get(id);
        }
        return itemTag;
    }
    
    public ITag<Block> getBlockTag() {
        if(blockTag == null) {
            blockTag =  TagCollectionManager.func_232928_e_().func_232923_a_().get(id);
        }
        return blockTag;
    }
    
    public ITag<EntityType<?>> getEntityTypeTag() {
        if(entityTypeTag == null) {
            entityTypeTag =  TagCollectionManager.func_232928_e_().func_232927_d_().get(id);
        }
        return entityTypeTag;
    }
    
    
    @Override
    public String getCommandString() {
        return String.format("<tag:%s>", this.id);
    }
    
    @Override
    public String toString() {
        return getCommandString();
    }
}
