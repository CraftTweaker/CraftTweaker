package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.network.ActionAddDataReceiver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker.impl.network.packet.ClientBoundDataPacket;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Allows scripts to send data from the server to the client.
 * <br>
 * This system provides a way for server side scripts to send data to client side only scripts, and trigger events from the server.
 * <br>
 * An example of this would be (neoforge only):
 * <code>
 * // Listens to the ItemTossEvent
 * events.register<ItemTossEvent>(event => {
 *     if event.player is ServerPlayer {
 *         // If the player is a ServerPlayer, send data to the client using the "toss" id
 *         network.sendTo(event.player as ServerPlayer, "toss", {custom: "data"});
 *     }
 * });
 * // Using the 'onlyif side client' preprocessor, we can ensure script lines are only ran on the Client distribution.
 * #onlyif side client
 * // Listen for data sent to the "toss" id
 * network.onData("toss", (data, context) => {
 *      // Prints the data that was received
 *      println(data.getAsString());
 * });
 * #endif
 * </code>
 *
 * @docParam this network
 */
@ZenRegister
@ZenCodeType.Name("crafttweaker.api.network.Network")
@Document("vanilla/api/network/Network")
public class CTNetwork {
    
    /**
     * Gets the global network instance
     */
    @ZenCodeGlobals.Global("network")
    public static final CTNetwork INSTANCE = new CTNetwork();
    
    public final Map<String, List<CTNetworkReceiver>> clientReceivers = new HashMap<>();
    
    private CTNetwork() {}
    
    /**
     * Sends the given data to the specified player on the specified id.
     *
     * @param player The player to send the data to.
     * @param id     A string that connects data receivers with data senders.
     * @param data   The data to send to the client
     *
     * @docParam player player
     * @docParam id my_notification
     * @docParam data { custom: "data" }
     */
    @ZenCodeType.Method
    public void sendTo(ServerPlayer player, String id, IData data) {
        
        Services.NETWORK.sendPacket(player, new ClientBoundDataPacket(id, data));
    }
    
    /**
     * Receives data on the client.
     *
     * @param id       A string that connects data receivers with data senders.
     * @param receiver A receiver that is run when the data is received from the server.
     *
     * @docParam id my_notification
     * @docParam receiver (data, context) => println(data.getAsString());
     */
    @ZenCodeType.Method
    public void onData(String id, CTNetworkReceiver receiver) {
        
        CraftTweakerAPI.apply(new ActionAddDataReceiver(id, receiver));
    }
    
    public void receive(String id, IData data, Player player) {
        
        if(!player.level().isClientSide()) {
            // Data is limited to Server -> Client right now
            return;
        }
        CTNetworkContext context = new CTNetworkContext(id, player);
        List<CTNetworkReceiver> receivers = clientReceivers.getOrDefault(id, List.of());
        for(CTNetworkReceiver receiver : receivers) {
            receiver.receive(data, context);
        }
    }
    
}
