package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Represents an {@link ITargetingFilter} that can be applied by the user onto a {@link Replacer}.
 *
 * <p>Much like a targeting filter, a filtering rule can remove a set of recipes from being able to be inspected by a
 * {@code Replacer}. The same condition apply, meaning that the filter is <strong>positive</strong>: it specifies which
 * recipes the replacer is <strong>allowed</strong> to inspect.</p>
 *
 * <p>For example, using a {@link com.blamejared.crafttweaker.api.recipe.replacement.type.ModsFilteringRule} indicates
 * that <strong>only the specified mods</strong> will be targeted by the replacer.</p>
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/IFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.IFilteringRule")
@ZenRegister
public interface IFilteringRule extends ITargetingFilter {
    
    /**
     * Describes the actions of this filtering rule in a human-readable form, for log output.
     *
     * @return A human-readable description of this rule.
     *
     * @since 10.0.0
     */
    String describe();
    
}
