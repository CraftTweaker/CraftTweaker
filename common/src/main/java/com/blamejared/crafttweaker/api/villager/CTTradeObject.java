package com.blamejared.crafttweaker.api.villager;

import com.blamejared.crafttweaker.api.item.IItemStack;

public class CTTradeObject {
    
    private final IItemStack buyingStack;
    private final IItemStack buyingStackSecond;
    private final IItemStack sellingStack;
    
    public CTTradeObject(IItemStack buyingStack, IItemStack buyingStackSecond, IItemStack sellingStack) {
        
        this.buyingStack = buyingStack;
        this.buyingStackSecond = buyingStackSecond;
        this.sellingStack = sellingStack;
    }
    
    public IItemStack getBuyingStack() {
        
        return buyingStack;
    }
    
    public IItemStack getBuyingStackSecond() {
        
        return buyingStackSecond;
    }
    
    public IItemStack getSellingStack() {
        
        return sellingStack;
    }
    
    @Override
    public String toString() {
        
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"buyingStack\": \"").append(buyingStack.getCommandString());
        sb.append("\", \"buyingStackSecond\": \"").append(buyingStackSecond.getCommandString());
        sb.append("\", \"sellingStack\": \"").append(sellingStack.getCommandString());
        sb.append("\"}");
        return sb.toString();
    }
    
}
