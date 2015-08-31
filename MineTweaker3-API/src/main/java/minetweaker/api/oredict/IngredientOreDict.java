/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.oredict;

import java.util.Collections;
import java.util.List;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemCondition;
import minetweaker.api.item.IItemStack;
import minetweaker.api.item.IItemTransformer;
import minetweaker.api.item.IngredientOr;
import minetweaker.api.item.IngredientStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import minetweaker.util.ArrayUtil;

/**
 *
 * @author Stan Hebben
 */
public class IngredientOreDict implements IIngredient {
	private final IOreDictEntry entry;
	private final String mark;
	private final IItemCondition[] conditions;
	private final IItemTransformer[] transformers;

	public IngredientOreDict(
			IOreDictEntry entry,
			String mark,
			IItemCondition[] conditions,
			IItemTransformer[] transformers) {
		this.entry = entry;
		this.mark = mark;
		this.conditions = conditions;
		this.transformers = transformers;
	}

	@Override
	public String getMark() {
		return mark;
	}

	@Override
	public int getAmount() {
		return 1;
	}

	@Override
	public List<IItemStack> getItems() {
		return entry.getItems();
	}

	@Override
	public List<ILiquidStack> getLiquids() {
		return Collections.emptyList();
	}

	@Override
	public IIngredient amount(int amount) {
		return new IngredientStack(this, amount);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientOreDict(entry, mark, conditions, ArrayUtil.append(transformers, transformer));
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientOreDict(entry, mark, ArrayUtil.append(conditions, condition), transformers);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientOreDict(entry, mark, conditions, transformers);
	}

	@Override
	public IIngredient or(IIngredient ingredient) {
		return new IngredientOr(this, ingredient);
	}

	@Override
	public boolean matches(IItemStack item) {
		if (!entry.matches(item))
			return false;

		for (IItemCondition condition : conditions) {
			if (!condition.matches(item))
				return false;
		}

		return true;
	}

	@Override
	public boolean matches(ILiquidStack liquid) {
		return false;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		return entry.contains(ingredient);
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
		for (IItemTransformer transformer : transformers) {
			item = transformer.transform(item, byPlayer);
		}

		return item;
	}

	@Override
	public boolean hasTransformers() {
		return transformers.length > 0;
	}

	@Override
	public Object getInternal() {
		return entry.getInternal();
	}
}
