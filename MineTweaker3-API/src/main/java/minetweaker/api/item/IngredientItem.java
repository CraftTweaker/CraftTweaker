package minetweaker.api.item;

import java.util.Collections;
import java.util.List;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.player.IPlayer;
import minetweaker.util.ArrayUtil;

/**
 * Contains an item stack with modifiers applied to it.
 * 
 * @author Stan Hebben
 */
public class IngredientItem implements IIngredient {
	private final IItemStack item;
	private final String mark;
	private final IItemCondition[] conditions;
	private final IItemTransformer[] transformers;
	private final List<IItemStack> items;

	public IngredientItem(IItemStack item, String mark, IItemCondition[] conditions, IItemTransformer[] transformers) {
		this.item = item;
		this.mark = mark;
		this.conditions = conditions;
		this.transformers = transformers;

		items = Collections.singletonList(item);
	}

	@Override
	public String getMark() {
		return mark;
	}

	@Override
	public int getAmount() {
		return item.getAmount();
	}

	@Override
	public List<IItemStack> getItems() {
		return items;
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
		return new IngredientItem(item, mark, conditions, ArrayUtil.append(transformers, transformer));
	}

	@Override
	public IIngredient only(IItemCondition condition) {
		return new IngredientItem(item, mark, ArrayUtil.append(conditions, condition), transformers);
	}

	@Override
	public IIngredient marked(String mark) {
		return new IngredientItem(item, mark, conditions, transformers);
	}

	@Override
	public IIngredient or(IIngredient ingredient) {
		return new IngredientOr(this, ingredient);
	}

	@Override
	public boolean matches(IItemStack item) {
		if (!this.item.matches(item))
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
		List<IItemStack> iitems = ingredient.getItems();
		for (IItemStack iitem : iitems) {
			if (!matches(iitem))
				return false;
		}

		return true;
	}

	@Override
	public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
		for (IItemTransformer transform : transformers) {
			item = transform.transform(item, byPlayer);
		}

		return item;
	}

	@Override
	public boolean hasTransformers() {
		return transformers.length > 0;
	}

	@Override
	public Object getInternal() {
		return item.getInternal();
	}

	// #############################
	// ### Object implementation ###
	// #############################

	@Override
	public String toString() {
		return "(Ingredient) " + item.toString();
	}
}
