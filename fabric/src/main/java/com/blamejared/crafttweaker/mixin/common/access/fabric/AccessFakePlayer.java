package com.blamejared.crafttweaker.mixin.common.access.fabric;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = FakePlayer.class, remap = false)
public interface AccessFakePlayer {
    
    @Accessor(remap = false)
    static Map<Object, Player> getFAKE_PLAYER_MAP() {throw new UnsupportedOperationException();}
    
    @Accessor(remap = false)
    static GameProfile getDEFAULT_PROFILE() {throw new UnsupportedOperationException();}
    
}
