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
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("data")
    public T getData() {
        
        return data;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("percentage")
    public double getPercentage() {
        
        return percentage;
    }
    
    @Override
    public String getCommandString() {
        
        return commandStringFunc.apply(getData());
    }
    
    @Override
    public boolean equals(Object o) {
        
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;
        
        Percentaged<?> that = (Percentaged<?>) o;
        
        if(Double.compare(that.getPercentage(), getPercentage()) != 0)
            return false;
        return getData().equals(that.getData());
    }
    
    @Override
    public int hashCode() {
        
        int result;
        long temp;
        result = getData().hashCode();
        temp = Double.doubleToLongBits(getPercentage());
        return  31 * result + (int) (temp ^ (temp >>> 32));
    }
    
}
