package com.blamejared.crafttweaker.api.network;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.data.IData;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

/**
 * A function that is fired when data is received from the network
 *
 * An example receiver that just prints the data received would look like this:
 * <code>
 * (data, context) => println(data.getAsString());
 * </code>
 *
 * @docParam this receiver
 */
@FunctionalInterface
@ZenRegister
@ZenCodeType.Name("vanilla/api/network/NetworkReceiver")
@Document("vanilla/api/network/NetworkReceiver")
public interface CTNetworkReceiver {
    
    /**
     * Acts on the received data.
     *
     * @param data    The data that was received.
     * @param context information related to the received data.
     */
    void receive(IData data, CTNetworkContext context);
    
}
