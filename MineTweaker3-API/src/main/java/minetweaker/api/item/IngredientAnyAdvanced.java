/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import java.util.List;
import minetweaker.util.ArrayUtil;

/**
 *
 * @author Stan
 */
public class IngredientAnyAdvanced implements IIngredient {
	private final String mark;
	private final IItemCondition[] conditions;
	private final IItemTransformer[] transformers;
	
	public IngredientAnyAdvanced(String mark, IItemCondition[] conditions, IItemTransformer[] transformers) {
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
		return null;
	}

	@Override
	public IIngredient amount(int amount) {
		return new IngredientStack(this, amount);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientAnyAdvanced(mark, conditions, ArrayUtil.append(transformers, transformer));
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientAnyAdvanced(mark, ArrayUtil.append(conditions, condition), transformers);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientAnyAdvanced(mark, conditions, transformers);
	}

	@Override
	public boolean matches(IItemStack item) {
		if (!item.matches(item)) return false;
		
		for (IItemCondition condition : conditions) {
			if (!condition.matches(item)) return false;
		}
		
		return true;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		List<IItemStack> iitems = ingredient.getItems();
		for (IItemStack iitem : iitems) {
			if (!matches(iitem)) return false;
		}
		
		return true;
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		for (IItemTransformer transform : transformers) {
			item = transform.transform(item);
		}
		
		return item;
	}

	@Override
	public Object getInternal() {
		return IngredientAny.INTERNAL_ANY;
	}
}
