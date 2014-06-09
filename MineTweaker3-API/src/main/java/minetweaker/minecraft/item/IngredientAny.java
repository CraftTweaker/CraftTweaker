/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.minecraft.item;

import java.util.List;

/**
 *
 * @author Stan
 */
public class IngredientAny implements IIngredient {
	public static IngredientAny INSTANCE = new IngredientAny();
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
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IIngredient marked(String mark) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean matches(IItemStack item) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public IItemStack applyTransform(IItemStack item) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public Object getInternal() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
