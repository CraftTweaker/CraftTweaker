package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.InteractionResult;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/InteractionResult")
@NativeTypeRegistration(value = InteractionResult.class, zenCodeName = "crafttweaker.api.world.InteractionResult")
@BracketEnum("minecraft:world/interactionresult")
public class ExpandInteractionResult {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("consumesAction")
    public static boolean consumesAction(InteractionResult internal) {
        
        return internal.consumesAction();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldSwing")
    public static boolean shouldSwing(InteractionResult internal) {
        
        return internal.shouldSwing();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("shouldAwardStats")
    public static boolean shouldAwardStats(InteractionResult internal) {
        
        return internal.shouldAwardStats();
    }
    
    /**
     * Returns true if {@code successSide} is true, this can be used to return {@code SUCCESS} on the client, but {@code CONSUME} on the server.
     *
     * @param successSide Is the current side the success side.
     *
     * @return <constant:minecraft:world/interactionresult:success> if {@code successSide} is true, otherwise <constant:minecraft:world/interactionresult:consume>.
     *
     * @docParam successSide true
     */
    @ZenCodeType.StaticExpansionMethod
    public static InteractionResult sidedSuccess(boolean successSide) {
        
        return InteractionResult.sidedSuccess(successSide);
    }
    
}
