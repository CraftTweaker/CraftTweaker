package crafttweaker.api.item;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.ArrayUtil;

import java.util.List;

/**
 * Represents the wildcard ingredient, with conditions and/or transformers
 * applied to it.
 *
 * @author Stan Hebben
 */
public class IngredientAnyAdvanced implements IIngredient {
    
    private final String mark;
    private final IItemCondition[] conditions;
    private final IItemTransformerNew[] transformerNews;
    private final IItemTransformer[] transformers;
    
    public IngredientAnyAdvanced(String mark, IItemCondition[] conditions, IItemTransformerNew[] transformerNews, IItemTransformer[] transformers) {
        this.mark = mark;
        this.conditions = conditions;
        this.transformerNews = transformerNews;
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
	public IItemStack[] getItemArray() {
		return new IItemStack[]{};
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
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new IngredientAnyAdvanced(mark, conditions, ArrayUtil.append(transformerNews, transformer), transformers);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientAnyAdvanced(mark, ArrayUtil.append(conditions, condition), transformerNews, transformers);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientAnyAdvanced(mark, conditions, transformerNews, transformers);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        for(IItemCondition condition : conditions) {
            if(!condition.matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        for(IItemCondition condition : conditions) {
            if(!condition.matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        return true;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        List<IItemStack> iitems = ingredient.getItems();
        for(IItemStack iitem : iitems) {
            if(!matches(iitem))
                return false;
        }
        
        return true;
    }
    
    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        for(IItemTransformer transform : transformers) {
            item = transform.transform(item, byPlayer);
        }
    
        return item;
    }
    
    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        for(IItemTransformerNew transform : transformerNews) {
            item = transform.transform(item);
        }
        
        return item;
    }
    
    @Override
    public boolean hasNewTransformers() {
        return transformerNews.length > 0;
    }
    
    @Override
    public boolean hasTransformers() {
        return transformers.length > 0;
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientAnyAdvanced(mark, conditions, transformerNews, stanhebben.zenscript.util.ArrayUtil.add(transformers, transformer));
    }
    
    @Override
    public Object getInternal() {
        return IngredientAny.INTERNAL_ANY;
    }
    
    @Override
    public String toCommandString() {
        return "<*>";
    }
}
