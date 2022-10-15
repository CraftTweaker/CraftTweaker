package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.saveddata.SavedData;

@ZenRegister
@Document("vanilla/api/world/SavedData")
@NativeTypeRegistration(value = SavedData.class, zenCodeName = "crafttweaker.api.world.SavedData")
public class ExpandSavedData {
    // There isn't anything in here a scripter should really be using.
}
