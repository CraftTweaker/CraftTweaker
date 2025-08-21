package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.data.op.IDataOps;
import com.blamejared.crafttweaker.api.data.visitor.DataToStringVisitor;
import com.blamejared.crafttweaker.api.ingredient.condition.IngredientConditions;
import com.blamejared.crafttweaker.natives.component.ExpandDataComponentType;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.function.Predicate;

public final class ItemStackUtil {
    
    public static String getCommandString(final ItemLike itemLike) {
        
        return getCommandString(itemLike.asItem().getDefaultInstance());
    }
    
    public static String getCommandString(final ItemStack stack) {
        
        return getCommandString(stack, false);
    }
    
    public static String getCommandString(final ItemStack stack, final boolean mutable) {
        
        final StringBuilder sb = new StringBuilder("<item:").append(BuiltInRegistries.ITEM.getKey(stack.getItem()))
                .append('>');
        
        DataComponentPatch.SplitResult split = stack.getComponentsPatch().split();
        split.added().filter(Predicate.not(DataComponentType::isTransient)).forEach(typedDataComponent -> {
            sb.append(".withJsonComponent(")
                    .append(ExpandDataComponentType.getCommandString(typedDataComponent.type()))
                    .append(", ")
                    .append(typedDataComponent.encodeValue(IDataOps.INSTANCE.withRegistryAccess()).getOrThrow().accept(DataToStringVisitor.ESCAPE))
                    .append(")");
        });
        split.removed()
                .forEach(dataComponentType -> sb.append(".without(")
                        .append(ExpandDataComponentType.getCommandString(dataComponentType))
                        .append(")"));
        
        if(!stack.isEmpty() && stack.getCount() != 1) {
            
            sb.append(" * ").append(stack.getCount());
        }
        
        if(mutable) {
            
            // Another option would be to mark it as mutable from the start: which one do we prefer?
            sb.insert(0, '(').append(").mutable()");
        }
        
        return sb.toString();
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second) {
        
        return areStacksTheSame(first, second, IngredientConditions.EMPTY);
    }
    
    public static boolean areStacksTheSame(final ItemStack first, final ItemStack second, final IngredientConditions conditions) {
        
        //Sometimes mods are bad and use null itemstacks, so lets just sort that out here
        if(first == null || second == null) {
            return first == second;
        }
        if(first.isEmpty() != second.isEmpty()) {
            return false;
        }
        if(first.getItem() != second.getItem()) {
            return false;
        }
        if(first.getCount() > second.getCount()) {
            return false;
        }
        return DataComponentPredicate.allOf(first.getComponents().filter(conditions.componentFilter())).test(second);
    }
    
}
