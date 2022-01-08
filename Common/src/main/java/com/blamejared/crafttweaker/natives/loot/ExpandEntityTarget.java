package com.blamejared.crafttweaker.natives.loot;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.storage.loot.LootContext;

@ZenRegister
@Document("vanilla/api/loot/EntityTarget")
@NativeTypeRegistration(value = LootContext.EntityTarget.class, zenCodeName = "crafttweaker.api.loot.EntityTarget")
@BracketEnum("minecraft:entitytarget")
public final class ExpandEntityTarget {
}
