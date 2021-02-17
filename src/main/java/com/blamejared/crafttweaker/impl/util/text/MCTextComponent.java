package com.blamejared.crafttweaker.impl.util.text;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.ZenWrapper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.Collectors;

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
    public MCStyle getStyle() {
        return new MCStyle(internal.getStyle());
    }
    
    @ZenCodeType.Method
    public MCTextComponent setStyle(MCStyle style) {
        if(getInternal() instanceof IFormattableTextComponent){
            IFormattableTextComponent formatted = (IFormattableTextComponent) internal;
            formatted.setStyle(internal.getStyle().mergeStyle(style.getInternal()));
        }
        return new MCTextComponent(internal);
    }

    @ZenCodeType.Method
    public static MCTextComponent createStringTextComponent(String text) {
        return new MCTextComponent(new StringTextComponent(text));
    }

    @ZenCodeType.Method
    public static MCTextComponent createTranslationTextComponent(String translationKey) {
        return new MCTextComponent(new TranslationTextComponent(translationKey));
    }

    @ZenCodeType.Method
    public static MCTextComponent createTranslationTextComponent(String translationKey, Object... args) {
        return new MCTextComponent(new TranslationTextComponent(translationKey, args));
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
    
    @ZenCodeType.Getter("siblings")
    public List<MCTextComponent> getSiblings() {
        return internal.getSiblings().stream().map(MCTextComponent::new).collect(Collectors.toList());
    }
    
    @ZenCodeType.Method
    public MCTextComponent appendSibling(MCTextComponent component) {
        internal.getSiblings().add(component.getInternal());
        return new MCTextComponent(internal);
    }
    
    @ZenCodeType.Method
    public MCTextComponent copyRaw() {
        return new MCTextComponent(internal.copyRaw());
    }
    
    @ZenCodeType.Method
    public MCTextComponent deepCopy() {
        return new MCTextComponent(internal.deepCopy());
    }
    
    
    @ZenCodeType.Method
    public MCTextComponent appendText(String text) {
        internal.getSiblings().add(new StringTextComponent(text));
        return new MCTextComponent(internal);
    }
    
    @ZenCodeType.Getter("formattedText")
    public String getFormattedText() {
        return internal.getString();
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
    
    public ITextComponent getInternal() {
        return internal;
    }
    
}
