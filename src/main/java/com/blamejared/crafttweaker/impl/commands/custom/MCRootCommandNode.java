package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.tree.RootCommandNode;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCRootCommandNode")
@Document("vanilla/api/commands/custom/MCRootCommandNode")
public class MCRootCommandNode extends MCCommandNode {
    
    private final RootCommandNode<CommandSource> internal;
    
    public MCRootCommandNode(RootCommandNode<CommandSource> internal) {
        super(internal);
        this.internal = internal;
    }
    
    @Override
    public RootCommandNode<CommandSource> getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public boolean isValidInput(final String input) {
        return internal.isValidInput(input);
    }
    
    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCRootCommandNode && internal.equals(((MCRootCommandNode) o).internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
    public boolean opEquals(final Object o) {
        return equals(o);
    }
    
    @ZenCodeType.Method
    public int hashCode() {
        return internal.hashCode();
    }
    
    @ZenCodeType.Method
    @Override
    public String toString() {
        return internal.toString();
    }
    
    @ZenCodeType.Caster(implicit = true)
    public String asString() {
        return toString();
    }
    
}
