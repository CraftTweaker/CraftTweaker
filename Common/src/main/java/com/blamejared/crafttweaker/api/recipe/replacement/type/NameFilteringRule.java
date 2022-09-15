package com.blamejared.crafttweaker.api.recipe.replacement.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.recipe.replacement.IFilteringRule;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.item.crafting.Recipe;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
    
    @ZenCodeType.Method
    public static NameFilteringRule anyOf(final String... exactNames) {
        
        if(exactNames.length < 1) {
            
            throw new IllegalArgumentException("Unable to build name filtering rule without at least one name");
        }
        
        return of(new ExactMatcher(Set.of(exactNames)));
    }
    
    @ZenCodeType.Method
    public static NameFilteringRule containing(final String contents) {
        
        if(contents.isEmpty()) {
            
            throw new IllegalArgumentException("Everything contains the empty string");
        }
        
        return of(new SimpleMatcher(contents));
    }
    
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
