/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.item;

import java.util.Collections;
import java.util.List;
import minetweaker.MineTweakerAPI;
import minetweaker.mc172.data.NBTConverter;
import minetweaker.mc172.liquid.TweakerLiquidStack;
import minetweaker.minecraft.data.IData;
import minetweaker.minecraft.item.IIngredient;
import minetweaker.minecraft.item.IItemCondition;
import minetweaker.minecraft.item.IItemDefinition;
import minetweaker.minecraft.item.IItemStack;
import minetweaker.minecraft.item.IItemTransformer;
import minetweaker.minecraft.item.IngredientItem;
import minetweaker.minecraft.liquid.ILiquidStack;
import minetweaker.minecraft.util.ArrayUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author Stan
 */
public class TweakerItemStack implements IItemStack {
	private final ItemStack stack;
	private final List<IItemStack> items;
	private IData tag = null;
	
	public TweakerItemStack(ItemStack itemStack) {
		stack = itemStack;
		items = Collections.<IItemStack>singletonList(this);
	}
	
	private TweakerItemStack(ItemStack itemStack, IData tag) {
		stack = itemStack;
		items = Collections.<IItemStack>singletonList(this);
		this.tag = tag;
	}

	@Override
	public IItemDefinition getDefinition() {
		return new TweakerItemDefinition(stack.getItem());
	}

	@Override
	public String getName() {
		return stack.getUnlocalizedName();
	}

	@Override
	public String getDisplayName() {
		return stack.getDisplayName();
	}

	@Override
	public int getDamage() {
		return stack.getItemDamage();
	}

	@Override
	public IData getTag() {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return null;
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		return tag;
	}

	@Override
	public int getMaxDamage() {
		return stack.getMaxDamage();
	}
	
	@Override
	public ILiquidStack getLiquid() {
		FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(stack);
		return liquid == null ? null : new TweakerLiquidStack(liquid);
	}

	@Override
	public IIngredient anyDamage() {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.logger.logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, OreDictionary.WILDCARD_VALUE);
			result.stackTagCompound = stack.stackTagCompound;
			return new TweakerItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withDamage(int damage) {
		if (stack.getItem().getHasSubtypes()) {
			MineTweakerAPI.logger.logWarning("subitems don't have damaged states");
			return this;
		} else {
			ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, damage);
			result.stackTagCompound = stack.stackTagCompound;
			return new TweakerItemStack(result, tag);
		}
	}

	@Override
	public IItemStack withAmount(int amount) {
		ItemStack result = new ItemStack(stack.getItem(), amount, stack.getItemDamage());
		result.stackTagCompound = stack.stackTagCompound;
		return new TweakerItemStack(result, tag);
	}

	@Override
	public IItemStack withTag(IData tag) {
		ItemStack result = new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage());
		result.stackTagCompound = (NBTTagCompound) NBTConverter.from(tag);
		return new TweakerItemStack(result, tag);
	}

	@Override
	public IItemStack updateTag(IData tagUpdate) {
		if (tag == null) {
			if (stack.stackTagCompound == null) {
				return withTag(tagUpdate);	
			}
			
			tag = NBTConverter.from(stack.stackTagCompound, true);
		}
		
		IData updated = tag.update(tagUpdate);
		return withTag(updated);
	}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return stack.stackSize;
	}

	@Override
	public List<IItemStack> getItems() {
		return items;
	}

	@Override
	public IIngredient amount(int amount) {
		return withAmount(amount);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientItem(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[] { transformer });
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientItem(this, null, new IItemCondition[] { condition }, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientItem(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public boolean matches(IItemStack item) {
		ItemStack internal = (ItemStack) item.getInternal();
		return internal.getItem() == stack.getItem()
				&& internal.stackSize == stack.stackSize
				&& (stack.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack.getItemDamage() == internal.getItemDamage());
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		List<IItemStack> iitems = ingredient.getItems();
		if (iitems == null || iitems.size() != 1) return false;
		return matches(iitems.get(0));
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		return item;
	}

	@Override
	public Object getInternal() {
		return stack;
	}
}
