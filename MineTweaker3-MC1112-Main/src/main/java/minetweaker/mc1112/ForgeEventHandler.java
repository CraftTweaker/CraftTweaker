package minetweaker.mc1112;

import minetweaker.*;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.tooltip.IngredientTooltips;
import minetweaker.mc1112.formatting.IMCFormattedString;
import minetweaker.mc1112.item.MCItemStack;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;

/**
 * @author Stan
 */
public class ForgeEventHandler {

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent ev) {
        minetweaker.api.event.PlayerInteractEvent event = new minetweaker.api.event.PlayerInteractEvent(MineTweakerMC.getIPlayer(ev.getEntityPlayer()), MineTweakerMC.getDimension(ev.getWorld()), ev.getPos() == null ? 0 : ev.getPos().getX(), ev.getPos() == null ? 0 : ev.getPos().getY(), ev.getPos() == null ? 0 : ev.getPos().getZ());
        MineTweakerImplementationAPI.events.publishPlayerInteract(event);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(ev.getItemStack() != null) {
            IItemStack itemStack = MineTweakerMC.getIItemStack(ev.getItemStack());
            for(IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
            }
            if(!Keyboard.isCreated()) {
                return;
            }
            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                for(IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                    ev.getToolTip().add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }
    }

    @SubscribeEvent
    public void mobDrop(LivingDropsEvent ev) {
        Entity entity = ev.getEntity();
        IEntityDefinition entityDefinition = MineTweakerAPI.game.getEntity(EntityList.getEntityString(ev.getEntity()));
        if(entityDefinition != null) {
            if(!entityDefinition.getDropsToAdd().isEmpty()) {
                entityDefinition.getDropsToAdd().forEach((key, val) -> {
                    EntityItem item = null;
                    if(val.getMin() == 0 && val.getMax() == 0) {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                    } else {
                        item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                    }
                    ev.getDrops().add(item);
                });
            }
            if(ev.getSource().getEntity() instanceof EntityPlayer) {
                if(!entityDefinition.getDropsToAddPlayerOnly().isEmpty()) {
                    entityDefinition.getDropsToAddPlayerOnly().forEach((key, val) -> {
                        EntityItem item = null;
                        if(val.getMin() == 0 && val.getMax() == 0) {
                            item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                        } else {
                            item = new EntityItem(entity.world, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                        }
                        ev.getDrops().add(item);
                    });
                }
            }
            if(!entityDefinition.getDropsToRemove().isEmpty()) {
                List<EntityItem> removedDrops = new ArrayList<>();
                entityDefinition.getDropsToRemove().forEach(drop -> {
                    //				if(drop.matches(new MCItemStack()))
                    ev.getDrops().forEach(drops -> {
                        if(drop.matches(new MCItemStack(drops.getEntityItem()))) {
                            removedDrops.add(drops);
                        }
                    });
                });
                ev.getDrops().removeAll(removedDrops);
            }
        }
    }
}
