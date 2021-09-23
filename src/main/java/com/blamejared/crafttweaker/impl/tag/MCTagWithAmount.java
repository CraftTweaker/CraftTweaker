package com.blamejared.crafttweaker.impl.tag;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker.api.brackets.CommandStringDisplayable;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/tags/MCTagWithAmount")
@ZenCodeType.Name("crafttweaker.api.tag.MCTagWithAmount")
public class MCTagWithAmount<T> implements CommandStringDisplayable {
    
    private final MCTag<T> tag;
    private int amount;
    
    public MCTagWithAmount(MCTag<T> tag, int amount) {
        
        this.tag = tag;
        this.amount = amount;
    }
    
    @Override
    public String getCommandString() {
        
        if(amount <= 1) {
            return tag.getCommandString();
        }
        
        return tag.getCommandString() + " * " + amount;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("tag")
    public MCTag<T> getTag() {
        
        return tag;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("amount")
    public int getAmount() {
        
        return amount;
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Setter("amount")
    public MCTagWithAmount<T> setAmount(int amount) {
        
        this.amount = amount;
        return this;
    }
    
    
}
