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
public class IngredientAny implements IIngredient {
	public static final IngredientAny INSTANCE = new IngredientAny();
	
	public static Object INTERNAL_ANY = null; // platforms supporting an "any" item should fill it here
	
	private IngredientAny() {}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return -1;
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
		return new IngredientAnyAdvanced(null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[] { transformer });
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientAnyAdvanced(null, new IItemCondition[] { condition }, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientAnyAdvanced(mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
	}

	@Override
	public boolean matches(IItemStack item) {
		return true;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		return true;
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		return item;
	}

	@Override
	public Object getInternal() {
		return INTERNAL_ANY;
	}
}
