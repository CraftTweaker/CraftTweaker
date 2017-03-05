package com.blamejared.ctgui.client;

import com.blamejared.ctgui.MTRecipe;
import com.blamejared.ctgui.api.GuiRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.*;

/**
 * Created by Jared.
 */
public class GuiHandler implements IGuiHandler {
    
    public GuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(MTRecipe.INSTANCE, this);
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(GuiRegistry.getHandlerForID(ID) == null) {
            return null;
        }
        return GuiRegistry.getHandlerForID(ID).getServerGuiElement(ID, player, world, x, y, z);
    }
    
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(GuiRegistry.getHandlerForID(ID) == null) {
            return null;
        }
        return GuiRegistry.getHandlerForID(ID).getClientGuiElement(ID, player, world, x, y, z);
    }
}
