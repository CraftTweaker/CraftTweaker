package com.blamejared.crafttweaker.impl.custom_commands;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import com.mojang.brigadier.ImmutableStringReader;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.custom_commands.MCImmutableStringReader")
@Document("vanilla/api/custom_commands/MCImmutableStringReader")
@ZenWrapper(wrappedClass = "com.mojang.brigadier.ImmutableStringReader", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCImmutableStringReader {
    private final ImmutableStringReader internal;

    public MCImmutableStringReader(ImmutableStringReader internal){
        this.internal = internal;
    }

    public ImmutableStringReader getInternal() {
        return this.internal;
    }

    @ZenCodeType.Method
    public String getRemaining() {
        return (internal.getRemaining());
    }


    @ZenCodeType.Method
    public boolean canRead(int arg0) {
        return internal.canRead(arg0);
    }


    @ZenCodeType.Method
    public int getCursor() {
        return internal.getCursor();
    }


    @ZenCodeType.Method
    public String getRead() {
        return (internal.getRead());
    }


    @ZenCodeType.Method
    public boolean canRead() {
        return internal.canRead();
    }


    @ZenCodeType.Method
    public char peek() {
        return internal.peek();
    }


    @ZenCodeType.Method
    public String getString() {
        return (internal.getString());
    }


    @ZenCodeType.Method
    public int getRemainingLength() {
        return internal.getRemainingLength();
    }


    @ZenCodeType.Method
    public char peek(int arg0) {
        return internal.peek(arg0);
    }


    @ZenCodeType.Method
    public int getTotalLength() {
        return internal.getTotalLength();
    }


}
