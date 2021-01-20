package com.blamejared.crafttweaker.impl.predicate;

import com.blamejared.crafttweaker.api.annotations.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.GameType;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.predicate.GameMode")
@Document("vanilla/api/predicate/GameMode")
public enum GameMode {
    SURVIVAL(GameType.SURVIVAL),
    CREATIVE(GameType.CREATIVE),
    ADVENTURE(GameType.ADVENTURE),
    SPECTATOR(GameType.SPECTATOR);

    private final GameType vanillaType;

    GameMode(final GameType vanillaType) {
        this.vanillaType = vanillaType;
    }

    public GameType toGameType() {
        return this.vanillaType;
    }
}
