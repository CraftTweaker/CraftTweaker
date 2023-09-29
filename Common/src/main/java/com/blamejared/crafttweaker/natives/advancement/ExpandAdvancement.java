package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Map;

//TODO 1.20.2 redo advancements
@ZenRegister
@Document("vanilla/api/advancement/Advancement")
@NativeTypeRegistration(value = Advancement.class, zenCodeName = "crafttweaker.api.advancement.Advancement")
public class ExpandAdvancement {
    
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("parent")
//    public static Advancement parent(Advancement internal) {
//
//        return internal.parent();
//    }
//
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("display")
//    public static DisplayInfo displayInfo(Advancement internal) {
//
//        return internal.display();
//    }
//
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("rewards")
//    public static AdvancementRewards getRewards(Advancement internal) {
//
//        return internal.rewards();
//    }
//
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("criteria")
//    public static Map<String, Criterion> criteria(Advancement internal) {
//
//        return GenericUtil.uncheck(internal.criteria());
//    }
//
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("id")
//    public static ResourceLocation getId(Advancement internal) {
//
//        return internal.getId();
//    }
//
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("requirements")
//    public static String[][] getRequirements(Advancement internal) {
//
//        return internal.getRequirements();
//    }
//
//    @ZenCodeType.Nullable
//    @ZenCodeType.Method
//    @ZenCodeType.Getter("chatComponent")
//    public static Component getChatComponent(Advancement internal) {
//
//        return internal.getChatComponent();
//    }
    
}
