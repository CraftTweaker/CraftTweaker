/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import java.util.Collections;
import java.util.List;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;

/**
 *
 * @author Stan
 */
public class IngredientUnknown implements IIngredient
{
	public static final IngredientUnknown INSTANCE = new IngredientUnknown(1);

	private final int size;

	public IngredientUnknown(int size)
	{
		this.size = size;
	}

	@Override
	public String getMark()
	{
		return null;
	}

	@Override
	public int getAmount()
	{
		return size;
	}

	@Override
	public List<IItemStack> getItems()
	{
		return Collections.emptyList();
	}

	@Override
	public List<ILiquidStack> getLiquids()
	{
		return Collections.emptyList();
	}

	@Override
	public IIngredient amount(int amount)
	{
		return new IngredientUnknown(amount);
	}

	@Override
	public IIngredient or(IIngredient ingredient)
	{
		return ingredient;
	}

	@Override
	public IIngredient transform(IItemTransformer transformer)
	{
		return this;
	}

	@Override
	public IIngredient only(IItemCondition condition)
	{
		return this;
	}

	@Override
	public IIngredient marked(String mark)
	{
		return this;
	}

	@Override
	public boolean matches(IItemStack item)
	{
		return false;
	}

	@Override
	public boolean matches(ILiquidStack liquid)
	{
		return false;
	}

	@Override
	public boolean contains(IIngredient ingredient)
	{
		return false;
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer)
	{
		return item;
	}

	@Override
	public boolean hasTransformers()
	{
		return false;
	}

	@Override
	public Object getInternal()
	{
		return null;
	}
}
