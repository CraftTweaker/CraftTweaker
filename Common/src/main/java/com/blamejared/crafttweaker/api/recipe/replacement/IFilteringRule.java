package com.blamejared.crafttweaker.api.recipe.replacement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@Document("vanilla/api/recipe/replacement/IFilteringRule")
@ZenCodeType.Name("crafttweaker.api.recipe.replacement.IFilteringRule")
@ZenRegister
public interface IFilteringRule extends ITargetingFilter {
    
    String describe();
    
}
