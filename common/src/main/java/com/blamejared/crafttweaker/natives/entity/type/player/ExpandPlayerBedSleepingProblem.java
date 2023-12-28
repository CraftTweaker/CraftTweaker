package com.blamejared.crafttweaker.natives.entity.type.player;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/entity/type/player/PlayerBedSleepingProblem")
@NativeTypeRegistration(value = Player.BedSleepingProblem.class, zenCodeName = "crafttweaker.api.entity.type.player.PlayerBedSleepingProblem")
@BracketEnum("minecraft:player/bed_sleeping_problem")
public class ExpandPlayerBedSleepingProblem {
    
    @ZenCodeType.Nullable
    @ZenCodeType.Getter("message")
    public static Component getMessage(Player.BedSleepingProblem internal) {
        
        return internal.getMessage();
    }
    
}
