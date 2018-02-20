package crafttweaker.api.item;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.ArrayUtil;

import java.util.*;

/**
 * Contains an item stack with modifiers applied to it.
 *
 * @author Stan Hebben
 */
public class IngredientItem implements IIngredient {
    
    private final IItemStack item;
    private final String mark;
    private final IItemCondition[] conditions;
    private final IItemTransformerNew[] transformerNews;
    private final List<IItemStack> items;
    private final IItemTransformer[] transformers;
    
    public IngredientItem(IItemStack item, String mark, IItemCondition[] conditions, IItemTransformerNew[] transformerNews, IItemTransformer[] transformers) {
        this.item = item;
        this.mark = mark;
        this.conditions = conditions;
        this.transformerNews = transformerNews;
        
        items = Collections.singletonList(item);
        this.transformers = transformers;
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
	public IItemStack[] getItemArray() {
		return items.toArray(new IItemStack[items.size()]);
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
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new IngredientItem(item, mark, conditions, ArrayUtil.append(transformerNews, transformer), transformers);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientItem(item, mark, ArrayUtil.append(conditions, condition), transformerNews, transformers);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientItem(item, mark, conditions, transformerNews, transformers);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        if(!this.item.matches(item))
            return false;
        
        for(IItemCondition condition : conditions) {
            if(!condition.matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        if(!this.item.matchesExact(item))
            return false;
        
        for(IItemCondition condition : conditions) {
            if(!condition.matches(item))
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
        return new IngredientItem(item, mark, conditions, transformerNews, stanhebben.zenscript.util.ArrayUtil.add(transformers, transformer));
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
    
    @Override
    public String toCommandString() {
        return item.toCommandString();
    }
}
