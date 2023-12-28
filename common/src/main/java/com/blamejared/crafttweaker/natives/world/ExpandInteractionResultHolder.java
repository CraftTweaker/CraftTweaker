package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/InteractionResultHolder")
@NativeTypeRegistration(value = InteractionResultHolder.class, zenCodeName = "crafttweaker.api.world.InteractionResultHolder")
public class ExpandInteractionResultHolder {
    
    @ZenCodeType.Getter("result")
    public static InteractionResult getResult(InteractionResultHolder internal) {
        
        return internal.getResult();
    }
    
    @ZenCodeType.Method
    public static <T> T getObject(InteractionResultHolder<T> internal, Class<T> tClass) {
        
        return GenericUtil.uncheck(internal.getObject());
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> InteractionResultHolder<T> success(Class<T> tClass, T object) {
        
        return InteractionResultHolder.success(object);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> InteractionResultHolder<T> consume(Class<T> tClass, T object) {
        
        return InteractionResultHolder.consume(object);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> InteractionResultHolder<T> pass(Class<T> tClass, T object) {
        
        return InteractionResultHolder.pass(object);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> InteractionResultHolder<T> fail(Class<T> tClass, T object) {
        
        return InteractionResultHolder.fail(object);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static <T> InteractionResultHolder<T> sidedSuccess(Class<T> tClass, T object, boolean success) {
        
        return InteractionResultHolder.sidedSuccess(object, success);
    }
    
}
