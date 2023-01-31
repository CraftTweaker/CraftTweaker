package com.blamejared.crafttweaker.api.util;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

/**
 * Used to represent data with an amount. (Think something like a <tag>, that does not have an amount by itself)
 */
@ZenRegister(loaders = {CraftTweakerConstants.DEFAULT_LOADER_NAME, CraftTweakerConstants.TAGS_LOADER_NAME})
@ZenCodeType.Name("crafttweaker.api.util.Many")
@Document("vanilla/api/util/Many")
public class Many<T> implements CommandStringDisplayable {
    
    private final T data;
    private final int amount;
    
    private final Function<T, String> commandStringFunc;
    
    public Many(T data, int amount, Function<T, String> commandStringFunc) {
        
        this.data = data;
        this.amount = amount;
        this.commandStringFunc = commandStringFunc;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public T getData() {
        
        return data;
    }
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public int getAmount() {
        
        return amount;
    }
    
    public Function<T, String> getCommandStringFunc() {
        
        return commandStringFunc;
    }
    
    @Override
    public String getCommandString() {
        
        return commandStringFunc.apply(getData());
    }
    
}
