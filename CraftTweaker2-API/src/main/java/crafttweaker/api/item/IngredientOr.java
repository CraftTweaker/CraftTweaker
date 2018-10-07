package crafttweaker.api.item;

import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import crafttweaker.util.ArrayUtil;

import java.util.*;

/**
 * @author Stan
 */
public class IngredientOr implements IIngredient {
    
    private final IIngredient[] elements;
    private final String mark;
    private final IItemCondition[] conditions;
    private final IItemTransformerNew[] transformerNews;
    private final IItemTransformer[] transformer;
    
    public IngredientOr(IIngredient[] elements) {
        this.elements = elements;
        mark = null;
        conditions = ArrayUtil.EMPTY_CONDITIONS;
        transformerNews = ArrayUtil.EMPTY_TRANSFORMERS;
        transformer = ArrayUtil.EMPTY_TRANSFORMERS_NEW;
    }
    
    public IngredientOr(IIngredient a, IIngredient b) {
        this(new IIngredient[]{a, b});
    }
    
    private IngredientOr(IIngredient[] elements, String mark, IItemCondition[] conditions, IItemTransformerNew[] transformerNews, IItemTransformer[] transformers) {
        this.elements = elements;
        this.mark = mark;
        this.conditions = conditions;
        this.transformerNews = transformerNews;
        this.transformer = transformers;
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
        List<IItemStack> result = new ArrayList<>();
        for(IIngredient element : elements) {
            result.addAll(element.getItems());
        }
        return result;
    }
    
    @Override
    public IItemStack[] getItemArray() {
        List<IItemStack> items = getItems();
        return items.toArray(new IItemStack[items.size()]);
    }
    
    @Override
    public List<ILiquidStack> getLiquids() {
        List<ILiquidStack> result = new ArrayList<>();
        for(IIngredient element : elements) {
            result.addAll(element.getLiquids());
        }
        return result;
    }
    
    @Override
    public IIngredient amount(int amount) {
        IIngredient[] result = new IIngredient[elements.length];
        for(int i = 0; i < elements.length; i++) {
            result[i] = elements[i].amount(amount);
        }
        return new IngredientOr(result);
    }
    
    @Override
    public IIngredient transformNew(IItemTransformerNew transformer) {
        return new IngredientOr(elements, mark, conditions, ArrayUtil.append(transformerNews, transformer), this.transformer);
    }
    
    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientOr(elements, mark, ArrayUtil.append(conditions, condition), transformerNews, transformer);
    }
    
    @Override
    public IIngredient marked(String mark) {
        return new IngredientOr(elements, mark, conditions, transformerNews, transformer);
    }
    
    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(ArrayUtil.append(elements, ingredient));
    }
    
    @Override
    public boolean matches(IItemStack item) {
        for(IIngredient ingredient : elements) {
            if(ingredient.matches(item))
                return true;
        }
        
        return false;
    }
    
    @Override
    public boolean matchesExact(IItemStack item) {
        for(IIngredient ingredient : elements) {
            if(ingredient.matchesExact(item))
                return true;
        }
        
        return false;
    }
    
    @Override
    public boolean matches(ILiquidStack liquid) {
        for(IIngredient ingredient : elements) {
            if(ingredient.matches(liquid))
                return true;
        }
        
        return false;
    }
    
    @Override
    public boolean contains(IIngredient ingredient) {
        List<IItemStack> items = ingredient.getItems();
        for(IItemStack item : items) {
            if(!matches(item))
                return false;
        }
        
        return true;
    }
    
    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        for(IItemTransformer transformer : transformer) {
            item = transformer.transform(item, byPlayer);
        }
        
        return item;
    }
    
    @Override
    public IItemStack applyNewTransform(IItemStack item) {

        boolean changed = false;

        for(IIngredient element : elements) {
            if(element.matches(item) && element.hasNewTransformers()) {
                item = element.applyNewTransform(item);
                changed = true;
            }
        }

        if(transformerNews.length == 0 && !changed)
            return ItemStackUnknown.INSTANCE;

        for(IItemTransformerNew transformer : transformerNews) {
            item = transformer.transform(item);
        }
    
        return item;
    }
    
    @Override
    public boolean hasNewTransformers() {
        for(IIngredient element : elements)
            if(element.hasNewTransformers())
                return true;
        return transformerNews.length > 0;
    }
    
    @Override
    public boolean hasTransformers() {
        return false;
    }
    
    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return null;
    }
    
    @Override
    public Object getInternal() {
        return elements;
    }
    
    @Override
    public String toCommandString() {
        if(elements.length == 0)
            return "ERROR";
        StringJoiner joiner = new StringJoiner(" | ");
        for(IIngredient element : elements) {
            joiner.add(element == null ? "null" : element.toCommandString());
        }
        return joiner.toString();
    }

    @Override
    public String toString() {
        return toCommandString();
    }
}
