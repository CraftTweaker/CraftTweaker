package com.blamejared.crafttweaker.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(FakePlayer.class)
public interface AccessFakePlayer {
    
    @Accessor
    static Map<Object, Player> getFAKE_PLAYER_MAP() {throw new UnsupportedOperationException();}
    
    @Accessor
    static GameProfile getDEFAULT_PROFILE() {throw new UnsupportedOperationException();}
    
}
