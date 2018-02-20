package crafttweaker.api.oredict;

import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.ArrayUtil;

import java.util.*;

/**
 * @author Stan Hebben
 */
public class IngredientOreDict implements IIngredient {
    
    private final IOreDictEntry entry;
    private final String mark;
    private final IItemCondition[] conditions;
    private final IItemTransformerNew[] transformerNews;
    private final IItemTransformer[] transformers;
    
    public IngredientOreDict(IOreDictEntry entry, String mark, IItemCondition[] conditions, IItemTransformerNew[] transformerNews, IItemTransformer[] transformers) {
        this.entry = entry;
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
        return entry.getItems();
    }
    
    @Override
    public IItemStack[] getItemArray() {
    	List<IItemStack> items = getItems();
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
        return new IngredientOreDict(entry, mark, conditions, ArrayUtil.append(transformerNews, transformer), transformers);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientOreDict(entry, mark, ArrayUtil.append(conditions, condition), transformerNews, transformers);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientOreDict(entry, mark, conditions, transformerNews, transformers);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }
    
    @Override
    public boolean matches(IItemStack item) {
        if(!entry.matches(item))
            return false;
        
        for(IItemCondition condition : conditions) {
            if(!condition.matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        if(!entry.matchesExact(item))
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
        return entry.contains(ingredient);
    }
    
    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        for(IItemTransformer transformer : transformers) {
            item = transformer.transform(item, byPlayer);
        }
        
        return item;
    }
    
    @Override
    public IItemStack applyNewTransform(IItemStack item) {
        for(IItemTransformerNew transformer : transformerNews) {
            item = transformer.transform(item);
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
        return new IngredientOreDict(entry, mark, conditions, transformerNews, stanhebben.zenscript.util.ArrayUtil.add(transformers, transformer));
    }
    
    @Override
    public Object getInternal() {
        return entry.getInternal();
    }
    
    @Override
    public String toCommandString() {
        return entry.toCommandString();
    }
}
