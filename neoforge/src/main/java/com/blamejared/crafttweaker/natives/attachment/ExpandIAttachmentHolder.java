package com.blamejared.crafttweaker.natives.attachment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.openzen.zencode.java.ZenCodeType;

import java.util.function.Supplier;

@ZenRegister
@Document("neoforge/api/attachment/IAttachmentHolder")
@NativeTypeRegistration(value = IAttachmentHolder.class, zenCodeName = "crafttweaker.neoforge.api.attachment.IAttachmentHolder")
public class ExpandIAttachmentHolder {
    
    @ZenCodeType.Method
    public static <T> boolean hasAttachmentData(IAttachmentHolder internal, Class<T> tClass, AttachmentType<T> type) {
        
        return internal.hasData(type);
    }
    
    @ZenCodeType.Method
    public static <T> boolean hasAttachmentData(IAttachmentHolder internal, Class<T> tClass, Supplier<AttachmentType<T>> type) {
        
        return internal.hasData(type.get());
    }
    
    @ZenCodeType.Method
    public static <T> T getAttachmentData(IAttachmentHolder internal, Class<T> tClass, AttachmentType<T> type) {
        
        return internal.getData(type);
    }
    
    @ZenCodeType.Method
    public static <T> T getAttachmentData(IAttachmentHolder internal, Class<T> tClass, Supplier<AttachmentType<T>> type) {
        
        return internal.getData(type);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T setAttachmentData(IAttachmentHolder internal, Class<T> tClass, AttachmentType<T> type, T data) {
        
        return internal.setData(type, data);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static <T> T setAttachmentData(IAttachmentHolder internal, Class<T> tClass, Supplier<AttachmentType<T>> type, T data) {
        
        return internal.setData(type, data);
    }
    
}
