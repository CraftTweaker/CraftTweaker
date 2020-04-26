package com.blamejared.crafttweaker.impl.commands.custom;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.*;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.commands.custom.MCLiteralCommandNode")
@Document("crafttweaker/api/commands/custom/MCLiteralCommandNode")
public class MCLiteralCommandNode extends MCCommandNode {
    private final LiteralCommandNode<CommandSource> internal;

    public MCLiteralCommandNode(LiteralCommandNode<CommandSource> internal) {
        super(internal);
        this.internal = internal;
    }

    public LiteralCommandNode<CommandSource> getInternal() {
        return internal;
    }

    @ZenCodeType.Method
    public String getLiteral() {
        return internal.getLiteral();
    }

    @ZenCodeType.Method
    public boolean isValidInput(final String input) {
        return internal.isValidInput(input);
    }

    @ZenCodeType.Method
    @Override
    public MCLiteralArgumentBuilder createBuilder() {
        return new MCLiteralArgumentBuilder(internal.createBuilder());
    }

    @ZenCodeType.Method
    @Override
    public boolean equals(final Object o) {
        return o instanceof MCLiteralCommandNode && internal.equals(((MCLiteralCommandNode) o).internal);
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
