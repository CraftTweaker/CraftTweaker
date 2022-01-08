package com.blamejared.crafttweaker.api.mod;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.mod.Mod")
@Document("vanilla/api/mod/Mod")
public record Mod(
        @ZenCodeType.Method @ZenCodeType.Getter("id") String id,
        @ZenCodeType.Method @ZenCodeType.Getter("displayName") String displayName,
        @ZenCodeType.Method @ZenCodeType.Getter("version") String version) {}
