package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.recipe.replace.ActionBatchReplacement;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.component.IRecipeComponent;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

@Document("vanilla/api/recipe/replacement/Replacer")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.Replacer")
@ZenRegister
public final class Replacer {
    
    private final List<IFilteringRule> rules;
    private final List<ReplacementRequest<?>> requests;
    private final AtomicBoolean done;
    
    private Replacer() {
        
        this.rules = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.done = new AtomicBoolean(false);
    }
    
    @ZenCodeType.Method
    public static Replacer create() {
        
        return new Replacer();
    }
    
    @ZenCodeType.Method
    public Replacer filter(final IFilteringRule rule) {
        
        this.checkDone();
        
        if(!this.rules.contains(rule)) {
            this.rules.add(rule);
        }
        
        return this;
    }
    
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final T toReplace, final T with) {
        
        return this.replace(component, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID), toReplace, with);
    }
    
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final ITargetingStrategy strategy, final T toReplace, final T with) {
        
        if(toReplace == with || component.match(toReplace, with)) {
            return this;
        }
        
        return this.replace(component, strategy, toReplace, (Function<T, T>) it -> with);
    }
    
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final ITargetingStrategy strategy, final T toReplace, final Function<T, T> with) {
        
        this.checkDone();
        final ReplacementRequest<T> request = new ReplacementRequest<>(component, strategy, toReplace, with::apply);
        this.requests.add(request);
        return this;
    }
    
    public void execute() {
        
        this.checkDone();
        this.done.set(true);
        CraftTweakerAPI.apply(ActionBatchReplacement.of(this.rules, this.requests));
    }
    
    private void checkDone() {
        
        if(this.done.get()) {
            throw new IllegalArgumentException("Replacer has already been exhausted");
        }
    }
    
}
