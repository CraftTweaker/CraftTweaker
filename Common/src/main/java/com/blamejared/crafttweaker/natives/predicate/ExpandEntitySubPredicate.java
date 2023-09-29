package com.blamejared.crafttweaker.natives.predicate;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/predicate/EntitySubPredicate")
@NativeTypeRegistration(value = EntitySubPredicate.class, zenCodeName = "crafttweaker.api.predicate.EntitySubPredicate")
public final class ExpandEntitySubPredicate {
    
}
