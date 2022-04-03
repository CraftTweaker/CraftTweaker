package com.blamejared.crafttweaker.api.plugin;

import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import net.minecraft.world.entity.npc.VillagerTrades;

import java.util.function.Function;

/**
 * Manages the registration of components necessary to manipulate villager trades.
 *
 * <p>Refer to the documentation of the various methods for more information.</p>
 *
 * @since 9.1.0
 */
public interface IVillagerTradeRegistrationHandler {
    
    /**
     * Registers a trade converter for the specified trade class.
     *
     * <p>A trade converter is defined as a function that obtains an object of the given type and transforms it into an
     * instance of {@link CTTradeObject}. This allows CraftTweaker to be able to understand the data of the trade and
     * manipulate it as needed to allow for removal or inspection.</p>
     *
     * @param tradeClass     The {@link Class} of the trade for which the converter is being registered.
     * @param tradeConverter A {@link Function} that can convert an object of the given class type into a
     *                       {@link CTTradeObject} so that it can be understood by CraftTweaker.
     * @param <T>            The type of the trade for which the converter is for.
     *
     * @since 9.1.0
     */
    <T extends VillagerTrades.ItemListing> void registerTradeConverter(final Class<T> tradeClass, final Function<T, CTTradeObject> tradeConverter);
    
}
