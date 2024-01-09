package com.blamejared.crafttweaker.natives.attachment;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.neoforged.neoforge.attachment.AttachmentHolder;

@ZenRegister
@Document("neoforge/api/attachment/AttachmentHolder")
@NativeTypeRegistration(value = AttachmentHolder.class, zenCodeName = "crafttweaker.neoforge.api.attachment.AttachmentHolder")
public class ExpandAttachmentHolder {
    
}
