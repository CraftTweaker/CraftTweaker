package com.blamejared.crafttweaker.api.util;


import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class IngredientUtil {
    
    public static boolean canConflict(final Ingredient a, final Ingredient b) {
        
        return canConflict(a, b, Ingredient::isEmpty, Ingredient::getItems, ItemStackUtil::areStacksTheSame);
    }
    
    public static <T extends Predicate<U>, U> boolean canConflict(final T a, final T b, final Predicate<T> isEmpty, final Function<T, U[]> elements, final BiPredicate<U, U> compare) {
        
        return a == b || (elements.apply(a).length == 0 && elements.apply(b).length == 0) || !findIntersection(a, b, isEmpty, elements, compare).isEmpty();
    }
    
    public static List<ItemStack> findIntersection(final Ingredient a, final Ingredient b) {
        
        return findIntersection(a, b, Ingredient::isEmpty, Ingredient::getItems, ItemStackUtil::areStacksTheSame);
    }
    
    public static boolean doIngredientsConflict(final List<Ingredient> first, final List<Ingredient> second) {
        
        return doIngredientsConflict(first, second, Ingredient::isEmpty, Ingredient::getItems, ItemStackUtil::areStacksTheSame);
    }
    
    public static <T extends Predicate<U>, U> boolean doIngredientsConflict(final List<T> first, final List<T> second, final Predicate<T> isEmpty, final Function<T, U[]> elements, final BiPredicate<U, U> compare) {
        
        final BitSet visitData = new BitSet(second.size());
        
        for(final T target : first) {
            
            for(int j = 0; j < second.size(); ++j) {
                
                if(visitData.get(j)) {
                    continue;
                }
                
                final T attempt = second.get(j);
                
                if(IngredientUtil.canConflict(target, attempt, isEmpty, elements, compare)) {
                    
                    visitData.set(j);
                    break;
                }
            }
        }
        
        // Since all ingredients must have been used, visitData must have been set fully to 1
        return visitData.nextClearBit(0) == second.size();
    }
    
    public static <T extends Predicate<U>, U> List<U> findIntersection(final T a, final T b, final Predicate<T> isEmpty, final Function<T, U[]> elements, final BiPredicate<U, U> compare) {
        
        if(isEmpty.test(a) || isEmpty.test(b)) {
            return List.of();
        }
        
        final U[] aElements = elements.apply(a);
        final U[] bElements = elements.apply(b);
        
        if(a == b) {
            return List.of(aElements);
        }
        
        List<U> intersection = null;
        
        // Empiric testing shows that the naive double-for-loop is very efficient for small arrays rather than more
        // specialized data structures, which have more costs associated with them
        for(final U aElement : aElements) {
            
            for(final U bElement : bElements) {
                
                if(compare.test(aElement, bElement)) {
                    
                    if(intersection == null) {
                        
                        intersection = new ArrayList<>();
                    }
                    
                    intersection.add(aElement);
                }
            }
        }
        
        return intersection == null ? List.of() : intersection;
    }
    
}
