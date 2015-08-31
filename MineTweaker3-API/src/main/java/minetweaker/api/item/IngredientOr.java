/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import java.util.ArrayList;
import java.util.List;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import minetweaker.util.ArrayUtil;

/**
 *
 * @author Stan
 */
public class IngredientOr implements IIngredient {
	private final IIngredient[] elements;
	private final String mark;
	private final IItemCondition[] conditions;
	private final IItemTransformer[] transformers;

	public IngredientOr(IIngredient[] elements) {
		this.elements = elements;
		mark = null;
		conditions = ArrayUtil.EMPTY_CONDITIONS;
		transformers = ArrayUtil.EMPTY_TRANSFORMERS;
	}

	public IngredientOr(IIngredient a, IIngredient b) {
		this(new IIngredient[] { a, b });
	}

	private IngredientOr(IIngredient[] elements, String mark, IItemCondition[] conditions, IItemTransformer[] transformers) {
		this.elements = elements;
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
		return elements[0].getAmount();
	}

	@Override
	public List<IItemStack> getItems() {
		List<IItemStack> result = new ArrayList<IItemStack>();
		for (IIngredient element : elements) {
			result.addAll(element.getItems());
		}
		return result;
	}

	@Override
	public List<ILiquidStack> getLiquids() {
		List<ILiquidStack> result = new ArrayList<ILiquidStack>();
		for (IIngredient element : elements) {
			result.addAll(element.getLiquids());
		}
		return result;
	}

	@Override
	public IIngredient amount(int amount) {
		IIngredient[] result = new IIngredient[elements.length];
		for (int i = 0; i < elements.length; i++) {
			result[i] = elements[i].amount(amount);
		}
		return new IngredientOr(result);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientOr(elements, mark, conditions, ArrayUtil.append(transformers, transformer));
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientOr(elements, mark, ArrayUtil.append(conditions, condition), transformers);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientOr(elements, mark, conditions, transformers);
	}

	@Override
	public IIngredient or(IIngredient ingredient) {
		return new IngredientOr(ArrayUtil.append(elements, ingredient));
	}

	@Override
	public boolean matches(IItemStack item) {
		for (IIngredient ingredient : elements) {
			if (ingredient.matches(item))
				return true;
		}

		return false;
	}

	@Override
	public boolean matches(ILiquidStack liquid) {
		for (IIngredient ingredient : elements) {
			if (ingredient.matches(liquid))
				return true;
		}

		return false;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		List<IItemStack> items = ingredient.getItems();
		for (IItemStack item : items) {
			if (!matches(item))
				return false;
		}

		return true;
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
		return null;
	}
}
