package com.blamejared.crafttweaker.natives.advancement;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import org.openzen.zencode.java.ZenCodeType;

import java.util.List;
import java.util.stream.StreamSupport;

@ZenRegister
@Document("vanilla/api/advancement/AdvancementProgress")
@NativeTypeRegistration(value = AdvancementProgress.class, zenCodeName = "crafttweaker.api.advancement.AdvancementProgress")
public class ExpandAdvancementProgress {
    
    @ZenCodeType.Getter("done")
    public static boolean isDone(AdvancementProgress internal) {
        
        return internal.isDone();
    }
    
    @ZenCodeType.Getter("hasProgress")
    public static boolean hasProgress(AdvancementProgress internal) {
        
        return internal.hasProgress();
    }
    
    @ZenCodeType.Method
    public static boolean grantProgress(AdvancementProgress internal, String criterionName) {
        
        return internal.grantProgress(criterionName);
    }
    
    @ZenCodeType.Method
    public static boolean revokeProgress(AdvancementProgress internal, String criterionName) {
        
        return internal.revokeProgress(criterionName);
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    public static CriterionProgress getCriterion(AdvancementProgress internal, String criterionName) {
        
        return internal.getCriterion(criterionName);
    }
    
    @ZenCodeType.Getter("percent")
    public static float getPercent(AdvancementProgress internal) {
        
        return internal.getPercent();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("progressText")
    public static String getProgressText(AdvancementProgress internal) {
        
        return internal.getProgressText();
    }
    
    @ZenCodeType.Getter("remainingCriteria")
    public static List<String> getRemainingCriteria(AdvancementProgress internal) {
        
        return StreamSupport.stream(internal.getRemainingCriteria().spliterator(), false).toList();
    }
    
    @ZenCodeType.Getter("completedCriteria")
    public static List<String> getCompletedCriteria(AdvancementProgress internal) {
        
        return StreamSupport.stream(internal.getCompletedCriteria().spliterator(), false).toList();
    }
    
}
