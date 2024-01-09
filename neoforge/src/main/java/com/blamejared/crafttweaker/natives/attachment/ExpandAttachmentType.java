package com.blamejared.crafttweaker.natives.attachment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/attachment/AttachmentType")
@NativeTypeRegistration(value = AttachmentType.class, zenCodeName = "crafttweaker.neoforge.api.attachment.AttachmentType")
public class ExpandAttachmentType {
    
    /**
     * Gets the registry name of this AttachmentType.
     *
     * @return A ResourceLocation of the registry name of this AttachmentType.
     */
    @ZenCodeType.Method
    @ZenCodeType.Getter("registryName")
    public static ResourceLocation getRegistryName(AttachmentType internal) {
        
        return NeoForgeRegistries.ATTACHMENT_TYPES.getKey(internal);
    }
    
    /**
     * Gets the attachment_type bracket handler syntax for this AttachmentType.
     *
     * E.G.
     * {@code <attachment_type:minecraft:mana>}
     *
     * @return The attachment_type bracket handler syntax for this AttachmentType.
     */
    @ZenCodeType.Method
    @ZenCodeType.Caster
    @ZenCodeType.Getter("commandString")
    public static String getCommandString(AttachmentType internal) {
        
        return "<attachment_type:" + getRegistryName(internal) + ">";
    }
    
}
