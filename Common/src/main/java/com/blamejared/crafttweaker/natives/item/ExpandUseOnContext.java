package com.blamejared.crafttweaker.natives.item;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/UseOnContext")
@NativeTypeRegistration(value = UseOnContext.class, zenCodeName = "crafttweaker.api.item.UseOnContext")
public class ExpandUseOnContext {
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("clickedPos")
    public static BlockPos getClickedPos(UseOnContext internal) {
        
        return internal.getClickedPos();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("clickedFace")
    public static Direction getClickedFace(UseOnContext internal) {
        
        return internal.getClickedFace();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("clickLocation")
    public static Vec3 getClickLocation(UseOnContext internal) {
        
        return internal.getClickLocation();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isInside")
    public static boolean isInside(UseOnContext internal) {
        
        return internal.isInside();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("itemInHand")
    public static ItemStack getItemInHand(UseOnContext internal) {
        
        return internal.getItemInHand();
    }
    
    @ZenCodeType.Nullable
    @ZenCodeType.Method
    @ZenCodeType.Getter("player")
    public static Player getPlayer(UseOnContext internal) {
        
        return internal.getPlayer();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("hand")
    public static InteractionHand getHand(UseOnContext internal) {
        
        return internal.getHand();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("level")
    public static Level getLevel(UseOnContext internal) {
        
        return internal.getLevel();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("horizontalDirection")
    public static Direction getHorizontalDirection(UseOnContext internal) {
        
        return internal.getHorizontalDirection();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("isSecondaryUseActive")
    public static boolean isSecondaryUseActive(UseOnContext internal) {
        
        return internal.isSecondaryUseActive();
    }
    
    @ZenCodeType.Method
    @ZenCodeType.Getter("rotation")
    public static float getRotation(UseOnContext internal) {
        
        return internal.getRotation();
    }
    
}
