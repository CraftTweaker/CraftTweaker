package com.blamejared.crafttweaker.api.util.random;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

/**
 * Used to represent data with an attached percentage (think an ItemStack with a 50% chance of being outputted).
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.random.Percentaged")
@Document("vanilla/api/util/random/Percentaged")
public class Percentaged<T> implements CommandStringDisplayable {
    
    private final T data;
    private final double percentage;
    private final Function<T, String> commandStringFunc;
    
    public Percentaged(T data, double percentage, Function<T, String> commandStringFunc) {
        
        this.data = data;
        this.percentage = percentage;
        this.commandStringFunc = commandStringFunc;
    }
    
    public T getData() {
        
        return data;
    }
    
    public double getPercentage() {
        
        return percentage;
    }
    
    @Override
    public String getCommandString() {
        
        return commandStringFunc.apply(getData());
    }
    
}
