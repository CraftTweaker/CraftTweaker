package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.AdvancementRequirements;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@ZenRegister
@Document("vanilla/api/advancement/AdvancementRequirements")
@NativeTypeRegistration(value = AdvancementRequirements.class, zenCodeName = "crafttweaker.api.advancement.AdvancementRequirements")
public class ExpandAdvancementRequirements {
    
    
    @ZenCodeType.Getter("size")
    public static int size(AdvancementRequirements internal) {
        
        return internal.size();
    }
    
    @ZenCodeType.Method
    public static boolean test(AdvancementRequirements internal, Predicate<String> test) {
        
        return internal.test(test);
    }
    
    @ZenCodeType.Method
    public static int count(AdvancementRequirements internal, Predicate<String> test) {
        
        return internal.count(test);
    }
    
    @ZenCodeType.Getter("isEmpty")
    public static boolean isEmpty(AdvancementRequirements internal) {
        
        return internal.isEmpty();
    }
    
    @ZenCodeType.Getter("names")
    public static Set<String> names(AdvancementRequirements internal) {
        
        return internal.names();
    }
    
    @ZenCodeType.Getter("requirements")
    public static List<List<String>> requirements(AdvancementRequirements internal) {
        
        return internal.requirements();
    }
    
}
