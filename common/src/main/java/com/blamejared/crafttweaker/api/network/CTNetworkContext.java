package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.world.entity.player.Player;
import org.openzen.zencode.java.ZenCodeType;

/**
 * Context that is provided when data is received from the network.
 *
 * @docParam this context
 */
@ZenRegister
@ZenCodeType.Name("vanilla/api/network/NetworkContext")
@Document("vanilla/api/network/NetworkContext")
public class CTNetworkContext {
    
    private final String id;
    private final Player player;
    
    public CTNetworkContext(String id, Player player) {
        
        this.id = id;
        this.player = player;
    }
    
    /**
     * The id of the network request.
     *
     * @return The id of the network request.
     */
    @ZenCodeType.Getter("id")
    public String id() {
        
        return id;
    }
    
    /**
     * The player that received the network request, this is usually the client player.
     *
     * @return The player that received the network request.
     */
    @ZenCodeType.Getter("player")
    public Player player() {
        
        return player;
    }
    
}
