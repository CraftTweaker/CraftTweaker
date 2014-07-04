/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.api.item;

import java.util.List;

/**
 *
 * @author Stan
 */
public class IngredientStack implements IIngredient {
	private final IIngredient ingredient;
	private final int amount;
	
	public IngredientStack(IIngredient ingredient, int amount) {
		this.ingredient = ingredient;
		this.amount = amount;
	}

	@Override
	public String getMark() {
		return null;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public List<IItemStack> getItems() {
		return ingredient.getItems();
	}

	@Override
	public IIngredient amount(int amount) {
		return new IngredientStack(ingredient, amount);
	}

	@Override
	public IIngredient transform(IItemTransformer transformer) {
		return new IngredientStack(ingredient.transform(transformer), amount);
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientStack(ingredient.only(condition), amount);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientStack(ingredient.marked(mark), amount);
	}

	@Override
	public boolean matches(IItemStack item) {
		return item.getAmount() == amount && ingredient.matches(item);
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		return this.ingredient.contains(ingredient);
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		return ingredient.applyTransform(item);
	}

	@Override
	public Object getInternal() {
		return null;
	}

	@Override
	public boolean hasTransformers() {
		return ingredient.hasTransformers();
	}
}
