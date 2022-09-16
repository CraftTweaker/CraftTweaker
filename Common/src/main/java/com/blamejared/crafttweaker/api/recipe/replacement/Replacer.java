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
import java.util.function.Predicate;

/**
 * Handles the replacement of {@link IRecipeComponent}s for {@link net.minecraft.world.item.crafting.Recipe}s.
 *
 * <p>Due to the expensive nature of the replacing process, once the replacer is created, it is not immediately executed
 * like with other calls, but rather the user needs to determine when the execution should occur with the
 * {@link #execute()} method. It is suggested to chain as many replacements as possible in a single invocation to avoid
 * too big performance hits.</p>
 *
 * <p>A replacer instance can be created through {@link #create()} and various filters specified through
 * {@link #filter(IFilteringRule)}. Note that all filters are <strong>positive</strong>, meaning that they specify the
 * set of recipes the replacer should target. Replacements can then be specified with the various {@code replace}
 * methods. Refer to their documentation for more information.</p>
 *
 * @docParam this Replacer.create()
 * @since 10.0.0
 */
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
    
    /**
     * Creates a new replacer instance.
     *
     * @return A new replacer instance.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static Replacer create() {
        
        return new Replacer();
    }
    
    /**
     * Specifies a {@link IFilteringRule} that the replacer must follow.
     *
     * <p>Note that the rules are <strong>positive</strong>, meaning that they specify the recipes that the replacer
     * should act upon.</p>
     *
     * <p>You can specify as many filters as you want. Note that they will all be applied at the same time. For example,
     * filtering for mod {@code "crafttweaker"} and with recipe type {@code <recipetype:minecraft:crafting>} will
     * indicate that only recipes added by you to the crafting table will be targeted.</p>
     *
     * <p>A replacer cannot be modified after execution.</p>
     *
     * @param rule The rule that should be applied.
     *
     * @return This for chaining.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public Replacer filter(final IFilteringRule rule) {
        
        this.checkDone();
        
        if(!this.rules.contains(rule)) {
            this.rules.add(rule);
        }
        
        return this;
    }
    
    /**
     * Specifies a replacement that should be carried out by this replacer.
     *
     * <p>In particular, the given {@link IRecipeComponent} will be used to query the recipe and alter exactly what has
     * been specified. The first element is then used to determine what needs to be replaced and the second element
     * indicates what the element should be replaced by. In other words, any instance of {@code toReplace} will be
     * replaced by {@code with}.</p>
     *
     * <p>The replacement strategy used will be default one, so the components will be checked directly, without
     * examining the various elements in detail. For example, a
     * {@link com.blamejared.crafttweaker.api.ingredient.IIngredient} like
     * {@code <item:minecraft:dirt> | <item:minecraft:diamond>} will be considered like a single entity. Attempting to
     * replace only dirt, for example, won't work.</p>
     *
     * <p>A replacer cannot be modified after execution.</p>
     *
     * @param component The {@link IRecipeComponent} indicating what should be targeted.
     * @param toReplace The oracle representing the element that needs to be replaced.
     * @param with      The element which will replace the oracle as needed.
     * @param <T>       The type of elements targeted by the component.
     *
     * @return This replacer for chaining.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final T toReplace, final T with) {
        
        return this.replace(component, ITargetingStrategy.find(ITargetingStrategy.DEFAULT_STRATEGY_ID), toReplace, with);
    }
    
    /**
     * Specifies a replacement that should be carried out by this replacer.
     *
     * <p>In particular, the given {@link IRecipeComponent} will be used to query the recipe and alter exactly what has
     * been specified. The first element is then used to determine what needs to be replaced and the second element
     * indicates what the element should be replaced by. In other words, any instance of {@code toReplace} will be
     * replaced by {@code with}.</p>
     *
     * <p>The strategy used can be determined by you, allowing for example to consider each element in detail instead of
     * directly. For example, a {@link com.blamejared.crafttweaker.api.ingredient.IIngredient} like
     * {@code <item:minecraft:dirt> | <item:minecraft:diamond>} can also be considered as two separate ingredients and
     * thus replacing only dirt can happen.</p>
     *
     * <p>A replacer cannot be modified after execution.</p>
     *
     * @param component The {@link IRecipeComponent} indicating what should be targeted.
     * @param strategy  The {@link ITargetingStrategy} that will be used to target components.
     * @param toReplace The oracle representing the element that needs to be replaced.
     * @param with      The element which will replace the oracle as needed.
     * @param <T>       The type of elements targeted by the component.
     *
     * @return This replacer for chaining.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final ITargetingStrategy strategy, final T toReplace, final T with) {
        
        if(toReplace == with || component.match(toReplace, with)) {
            return this;
        }
        
        final DescriptivePredicate<T> predicate = DescriptivePredicate.of(it -> component.match(toReplace, it), toReplace.toString());
        final DescriptiveUnaryOperator<T> operator = DescriptiveUnaryOperator.of(it -> with, with.toString());
        return this.replace(component, strategy, predicate, operator);
    }
    
    /**
     * Specifies a replacement that should be carried out by this replacer.
     *
     * <p>In particular, the given {@link IRecipeComponent} will be used to query the recipe and alter exactly what has
     * been specified. The first element is then used to determine what needs to be replaced and the second element
     * will determine what the element should be replaced by. In other words, any instance of {@code toReplace} will be
     * replaced by the result of the execution of {@code with}.</p>
     *
     * <p>The strategy used can be determined by you, allowing for example to consider each element in detail instead of
     * directly. For example, a {@link com.blamejared.crafttweaker.api.ingredient.IIngredient} like
     * {@code <item:minecraft:dirt> | <item:minecraft:diamond>} can also be considered as two separate ingredients and
     * thus replacing only dirt can happen.</p>
     *
     * <p>A replacer cannot be modified after execution.</p>
     *
     * @param component The {@link IRecipeComponent} indicating what should be targeted.
     * @param strategy  The {@link ITargetingStrategy} that will be used to target components.
     * @param toReplace The oracle representing the element that needs to be replaced.
     * @param with      A {@link Function} that determines the replacement for {@code toReplace}. The argument given to
     *                  the function is the target that needs to be replaced. The function can then determine freely how
     *                  the replacement should be carried out.
     * @param <T>       The type of elements targeted by the component.
     *
     * @return This replacer for chaining.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final ITargetingStrategy strategy, final T toReplace, final Function<T, T> with) {
        
        final DescriptivePredicate<T> predicate = DescriptivePredicate.of(it -> component.match(toReplace, it), toReplace.toString());
        final DescriptiveUnaryOperator<T> operator = DescriptiveUnaryOperator.wrap(with::apply);
        return this.replace(component, strategy, predicate, operator);
    }
    
    /**
     * Specifies a replacement that should be carried out by this replacer.
     *
     * <p>In particular, the given {@link IRecipeComponent} will be used to query the recipe and alter exactly what has
     * been specified. The predicate will be used to determine whether a specific target needs to be replaced, whereas
     * the function will determine what the element should be replaced by. In other words, any instance that makes
     * {@code toReplace} return {@code true} will be replaced by the result of the execution of {@code with}.</p>
     *
     * <p>The strategy used can be determined by you, allowing for example to consider each element in detail instead of
     * directly. For example, a {@link com.blamejared.crafttweaker.api.ingredient.IIngredient} like
     * {@code <item:minecraft:dirt> | <item:minecraft:diamond>} can also be considered as two separate ingredients and
     * thus replacing only dirt can happen.</p>
     *
     * <p>A replacer cannot be modified after execution.</p>
     *
     * @param component The {@link IRecipeComponent} indicating what should be targeted.
     * @param strategy  The {@link ITargetingStrategy} that will be used to target components.
     * @param toReplace A {@link Predicate} determining whether a specific element should be replaced or not. The
     *                  argument given to it is the target that might have to be replaced.
     * @param with      A {@link Function} that determines the replacement for elements that {@code toReplace} deems
     *                  necessary of replacement. The argument given to the function is the target that needs to be
     *                  replaced. The function can then determine freely how the replacement should be carried out.
     * @param <T>       The type of elements targeted by the component.
     *
     * @return This replacer for chaining.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public <T> Replacer replace(final IRecipeComponent<T> component, final ITargetingStrategy strategy, final Predicate<T> toReplace, final Function<T, T> with) {
        
        return this.replace(component, strategy, DescriptivePredicate.wrap(toReplace), DescriptiveUnaryOperator.wrap(with::apply));
    }
    
    private <T> Replacer replace(
            final IRecipeComponent<T> component,
            final ITargetingStrategy strategy,
            final DescriptivePredicate<T> predicate,
            final DescriptiveUnaryOperator<T> operator
    ) {
        
        this.checkDone();
        final ReplacementRequest<T> request = new ReplacementRequest<>(component, strategy, predicate, operator);
        this.requests.add(request);
        return this;
    }
    
    /**
     * Executes the replacer, carrying out all replacements determined up until now.
     *
     * <p>After this method is called, the replacer will be exhausted, meaning that a new replacer will be needed to
     * carry out additional replacements.</p>
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
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
