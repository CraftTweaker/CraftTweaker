package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.*;
import com.blamejared.crafttweaker_annotations.annotations.*;
import net.minecraft.util.text.*;
import org.openzen.zencode.java.*;

import java.util.*;
import java.util.stream.*;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.util.text.MCTextComponent")
@Document("vanilla/api/util/text/MCTextComponent")
@ZenWrapper(wrappedClass = "net.minecraft.util.text.ITextComponent", conversionMethodFormat = "%s.getInternal()", displayStringFormat = "%s.toString()")
public class MCTextComponent {
    
    private final ITextComponent internal;
    
    public MCTextComponent(ITextComponent internal) {
        this.internal = internal;
    }
    
    @ZenCodeType.Method
    public static MCTextComponent copyWithoutSiblings(MCTextComponent textComponent) {
        return new MCTextComponent(ITextComponent.copyWithoutSiblings(textComponent.getInternal()));
    }
    
    public ITextComponent getInternal() {
        return internal;
    }
    
    @ZenCodeType.Method
    public MCTextComponent appendSibling(MCTextComponent component) {
        ITextComponent set = internal.appendSibling(component.getInternal());
        return internal == set ? this : new MCTextComponent(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
    public MCTextComponent opAppend(MCTextComponent component) {
        return appendSibling(component);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public MCTextComponent opAdd(MCTextComponent component) {
        return appendSibling(component);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
    public MCTextComponent opShLeft(MCTextComponent component) {
        return appendSibling(component);
    }
    
    @ZenCodeType.Getter("siblings")
    public List<MCTextComponent> getSiblings() {
        return internal.getSiblings()
                .stream()
                .map(MCTextComponent::new)
                .collect(Collectors.toList());
    }
    
    public MCStyle getStyle() {
        return new MCStyle(internal.getStyle());
    }
    
    public MCTextComponent setStyle(MCStyle style) {
        final ITextComponent set = internal.setStyle(style.getInternal());
        return internal == set ? this : new MCTextComponent(internal);
    }
    
    @ZenCodeType.Method
    public MCTextComponent appendText(String text) {
        final ITextComponent set = internal.appendText(text);
        return internal == set ? this : new MCTextComponent(internal);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
    public MCTextComponent opCat(String text) {
        return appendText(text);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public MCTextComponent opAdd(String text) {
        return appendText(text);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
    public MCTextComponent opLShift(String text) {
        return appendText(text);
    }
    
    @ZenCodeType.Method
    public String getUnformattedComponentText() {
        return internal.getUnformattedComponentText();
    }
    
    @ZenCodeType.Getter("unformattedComponentText")
    public String getUnformattedComponentTextGetter() {
        return getUnformattedComponentText();
    }
    
    @ZenCodeType.Method
    public String getString() {
        return internal.getString();
    }
    
    @ZenCodeType.Caster
    public String asString() {
        return getString();
    }
    
    @ZenCodeType.Method
    public String getStringTruncated(int maxLen) {
        return internal.getStringTruncated(maxLen);
    }
    
    @ZenCodeType.Getter("formattedText")
    public String getFormattedText() {
        return internal.getFormattedText();
    }
    
    @ZenCodeType.Method
    public MCTextComponent shallowCopy() {
        return new MCTextComponent(internal.shallowCopy());
    }
    
    @ZenCodeType.Method
    public MCTextComponent deepCopy() {
        return new MCTextComponent(internal.deepCopy());
    }
}
