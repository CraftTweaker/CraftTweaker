package minetweaker.api.item;

import java.util.List;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import minetweaker.util.ArrayUtil;

/**
 * Represents the wildcard ingredient (<*>).
 * 
 * @author Stan Hebben
 */
public class IngredientAny implements IIngredient {
	public static final IngredientAny INSTANCE = new IngredientAny();

	public static Object INTERNAL_ANY = null; // platforms supporting an "any"
												// item should fill it here

	private IngredientAny() {
	}

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
	public List<ILiquidStack> getLiquids() {
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
	public IIngredient or(IIngredient ingredient) {
		return this;
	}

	@Override
	public boolean matches(IItemStack item) {
		return true;
	}

	@Override
	public boolean matches(ILiquidStack liquid) {
		return true;
	}

	@Override
	public boolean contains(IIngredient ingredient) {
		return true;
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
		return item;
	}

	@Override
	public boolean hasTransformers() {
		return false;
	}

	@Override
	public Object getInternal() {
		return INTERNAL_ANY;
	}

	@Override
	public String toString() {
		return "<*>";
	}
}
