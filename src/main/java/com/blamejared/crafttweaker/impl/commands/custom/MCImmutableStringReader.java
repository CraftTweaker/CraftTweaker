package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.mojang.brigadier.ImmutableStringReader;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCImmutableStringReader")
@Document("vanilla/api/commands/custom/MCImmutableStringReader")
@ZenWrapper(wrappedClass = "com.mojang.brigadier.ImmutableStringReader", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCImmutableStringReader {
    
    private final ImmutableStringReader internal;
    
    public MCImmutableStringReader(ImmutableStringReader internal) {
        
        this.internal = internal;
    }
    
    public ImmutableStringReader getInternal() {
        
        return this.internal;
    }
    
    @ZenCodeType.Method
    public String getRemaining() {
        
        return getInternal().getRemaining();
    }
    
    
    @ZenCodeType.Method
    public boolean canRead(int arg0) {
        
        return getInternal().canRead(arg0);
    }
    
    
    @ZenCodeType.Method
    public int getCursor() {
        
        return getInternal().getCursor();
    }
    
    
    @ZenCodeType.Method
    public String getRead() {
        
        return getInternal().getRead();
    }
    
    
    @ZenCodeType.Method
    public boolean canRead() {
        
        return getInternal().canRead();
    }
    
    
    @ZenCodeType.Method
    public char peek() {
        
        return getInternal().peek();
    }
    
    
    @ZenCodeType.Method
    public String getString() {
        
        return getInternal().getString();
    }
    
    
    @ZenCodeType.Method
    public int getRemainingLength() {
        
        return getInternal().getRemainingLength();
    }
    
    
    @ZenCodeType.Method
    public char peek(int arg0) {
        
        return getInternal().peek(arg0);
    }
    
    
    @ZenCodeType.Method
    public int getTotalLength() {
        
        return getInternal().getTotalLength();
    }
    
    
}
