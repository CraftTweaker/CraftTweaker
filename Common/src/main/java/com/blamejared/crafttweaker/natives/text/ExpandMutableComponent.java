package com.blamejared.crafttweaker.natives.text;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Function;

@ZenRegister
@Document("vanilla/api/text/MutableComponent")
@NativeTypeRegistration(value = MutableComponent.class, zenCodeName = "crafttweaker.api.text.MutableComponent")
public class ExpandMutableComponent {
    
    @ZenCodeType.Method
    public static MutableComponent setStyle(MutableComponent internal, Style style) {
        
        return internal.setStyle(style);
    }
    
    @ZenCodeType.Method
    public static MutableComponent append(MutableComponent internal, String content) {
        
        return internal.append(content);
    }
    
    @ZenCodeType.Method
    public static MutableComponent append(MutableComponent internal, Component component) {
        
        return internal.append(component);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
    public static MutableComponent opCatString(MutableComponent internal, String content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.CAT)
    public static MutableComponent opCatComponent(MutableComponent internal, Component content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static MutableComponent opAddString(MutableComponent internal, String content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.ADD)
    public static MutableComponent opAddComponent(MutableComponent internal, Component content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
    public static MutableComponent opShiftLeftString(MutableComponent internal, String content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Operator(ZenCodeType.OperatorType.SHL)
    public static MutableComponent opShiftLeftComponent(MutableComponent internal, Component content) {
        
        return append(internal, content);
    }
    
    @ZenCodeType.Method
    public static MutableComponent withStyle(MutableComponent internal, Function<Style, Style> styleOperator) {
        // ZC doesn't like UnaryOperator...
        return internal.withStyle(styleOperator::apply);
    }
    
    @ZenCodeType.Method
    public static MutableComponent withStyle(MutableComponent internal, Style style) {
        
        return internal.withStyle(style);
    }
    
    @ZenCodeType.Method
    public static MutableComponent withStyle(MutableComponent internal, ChatFormatting... formatting) {
        
        return internal.withStyle(formatting);
    }
    
}
