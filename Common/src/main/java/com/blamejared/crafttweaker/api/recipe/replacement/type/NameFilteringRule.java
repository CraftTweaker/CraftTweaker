package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Filters recipes that match a specific pattern on their name.
 *
 * <p>This acts as a less specialized version of {@link ModsFilteringRule} as it allows custom matching on the entirety
 * of a recipe's name as determined by {@link Recipe#getId()}, at the cost of some efficiency.</p>
 *
 * @since 10.0.0
 */
@Document("vanilla/api/recipe/replacement/type/NameFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.type.NameFilteringRule")
@ZenRegister
public final class NameFilteringRule implements IFilteringRule {
    
    @FunctionalInterface
    private interface Matcher {
        
        boolean matches(final String s);
        
    }
    
    private record ExactMatcher(Set<String> exact) implements Matcher {
        
        @Override
        public boolean matches(final String s) {
            
            return this.exact().contains(s);
        }
        
        @Override
        public String toString() {
            
            return "exactly one of " + this.exact().toString().replace('[', '{').replace(']', '}');
        }
        
    }
    
    private record SimpleMatcher(String content) implements Matcher {
        
        @Override
        public boolean matches(final String s) {
            
            return this.content().contains(s);
        }
        
        @Override
        public String toString() {
            
            return "anything containing " + this.content();
        }
        
    }
    
    private record RegexMatcher(Pattern pattern) implements Matcher {
        
        @Override
        public boolean matches(final String s) {
            
            return this.pattern().matcher(s).matches();
        }
        
        @Override
        public String toString() {
            
            return this.pattern().toString();
        }
        
    }
    
    private final Matcher matcher;
    
    private NameFilteringRule(final Matcher matcher) {
        
        this.matcher = matcher;
    }
    
    /**
     * Creates a rule that filters only recipes with the specific given names.
     *
     * <p>The names are matched exactly, in both namespace and path.</p>
     *
     * @param exactNames The exact names to look for.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static NameFilteringRule anyOf(final String... exactNames) {
        
        if(exactNames.length < 1) {
            
            throw new IllegalArgumentException("Unable to build name filtering rule without at least one name");
        }
        
        return of(new ExactMatcher(Set.of(exactNames)));
    }
    
    /**
     * Creates a rule filtering recipes that have the given word in their name.
     *
     * <p>The word is not matched exactly and word boundaries aren't considered. This means that if a recipe is called
     * {@code "minecraft:pumpkin_pie"} and the rule is set up to check {@code "pump"}, that recipe will pass the check
     * instead of failing it.</p>
     *
     * @param contents The contents to look for in the recipe's name.
     *
     * @return A rule carrying out exactly what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static NameFilteringRule containing(final String contents) {
        
        if(contents.isEmpty()) {
            
            throw new IllegalArgumentException("Everything contains the empty string");
        }
        
        return of(new SimpleMatcher(contents));
    }
    
    /**
     * Creates a rule filtering recipes based on the given regular expression.
     *
     * @param regex The regular expression to use.
     *
     * @return A rule carrying out what has been specified.
     *
     * @since 10.0.0
     */
    @ZenCodeType.Method
    public static NameFilteringRule regex(final String regex) {
        
        return of(new RegexMatcher(Pattern.compile(regex)));
    }
    
    private static NameFilteringRule of(final Matcher matcher) {
        
        return new NameFilteringRule(matcher);
    }
    
    @Override
    public Stream<? extends Recipe<?>> castFilter(final Stream<? extends Recipe<?>> allRecipes) {
        
        return allRecipes.filter(it -> this.matcher.matches(it.getId().toString()));
    }
    
    @Override
    public String describe() {
        
        return "recipes matching " + this.matcher.toString();
    }
    
}
