package com.blamejared.ctgui.api.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CTGUIEvent extends Event {

    private final EntityPlayer player;
    private final World world;
    private final String guiName;

    public CTGUIEvent(EntityPlayer player, World world, String guiName) {
        this.player = player;
        this.world = world;
        this.guiName = guiName;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    public World getWorld() {
        return world;
    }

    public String getGuiName() {
        return guiName;
    }
}
