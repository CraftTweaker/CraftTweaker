package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.openzen.zencode.java.ZenCodeType;

import java.io.File;

@ZenRegister
@Document("vanilla/api/world/SavedData")
@NativeTypeRegistration(value = SavedData.class, zenCodeName = "crafttweaker.api.world.SavedData")
public class ExpandSavedData {
    // There isn't anything in here a scripter should really be using.
}
