/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc18;

import minetweaker.MineTweakerImplementationAPI;
import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.tooltip.IngredientTooltips;
import minetweaker.mc18.formatting.IMCFormattedString;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

/**
 * @author Stan
 */
public class ForgeEventHandler {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent ev) {
        minetweaker.api.event.PlayerInteractEvent event = new minetweaker.api.event.PlayerInteractEvent(
                MineTweakerMC.getIPlayer(ev.entityPlayer),
                MineTweakerMC.getDimension(ev.world),
                ev.pos == null ? 0 : ev.pos.getX(),
                ev.pos == null ? 0 : ev.pos.getY(),
                ev.pos == null ? 0 : ev.pos.getZ());

        MineTweakerImplementationAPI.events.publishPlayerInteract(event);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent ev) {
        IItemStack itemStack = MineTweakerMC.getIItemStack(ev.itemStack);
        for (IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
            ev.toolTip.add(((IMCFormattedString) tooltip).getTooltipString());
        }
        if (!Keyboard.isCreated()) {
            return;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            for (IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                ev.toolTip.add(((IMCFormattedString) tooltip).getTooltipString());
            }
        }
    }
}
