package com.blamejared.crafttweaker.natives.capability;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/capability/IFluidHandlerItem")
@NativeTypeRegistration(value = IFluidHandlerItem.class, zenCodeName = "crafttweaker.api.capability.IFluidHandlerItem")
public class ExpandIFluidHandlerItem {

    /**
     * Gets the container of this handler.
     *
     * @return The container of this handler.
     */
    @ZenCodeType.Getter("container")
    public static ItemStack getContainer(IFluidHandlerItem internal) {

        return internal.getContainer();
    }

}
