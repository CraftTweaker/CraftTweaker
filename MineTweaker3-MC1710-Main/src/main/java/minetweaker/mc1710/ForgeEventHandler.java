/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import minetweaker.*;
import minetweaker.api.entity.*;
import minetweaker.api.formatting.IFormattedText;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.tooltip.IngredientTooltips;
import minetweaker.mc1710.formatting.IMCFormattedString;
import minetweaker.mc1710.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import org.lwjgl.input.Keyboard;
import stanhebben.zenscript.value.*;

import java.util.*;

/**
 * @author Stan
 */
public class ForgeEventHandler {
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent ev) {
        minetweaker.api.event.PlayerInteractEvent event = new minetweaker.api.event.PlayerInteractEvent(
                MineTweakerMC.getIPlayer(ev.entityPlayer),
                MineTweakerMC.getDimension(ev.world),
                ev.x, ev.y, ev.z
        );

        MineTweakerImplementationAPI.events.publishPlayerInteract(event);
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent ev) {
        if(ev.itemStack!= null) {
            IItemStack itemStack = MineTweakerMC.getIItemStack(ev.itemStack);
            for(IFormattedText tooltip : IngredientTooltips.getTooltips(itemStack)) {
                ev.toolTip.add(((IMCFormattedString) tooltip).getTooltipString());
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                for(IFormattedText tooltip : IngredientTooltips.getShiftTooltips(itemStack)) {
                    ev.toolTip.add(((IMCFormattedString) tooltip).getTooltipString());
                }
            }
        }
    }
    
    
    @SubscribeEvent
    public void onLivingDeathDrops(LivingDropsEvent ev) {
        final EntityLivingBase entity = ev.entityLiving;
        final IEntityDefinition iEntity = MineTweakerAPI.game.getEntity(EntityList.getEntityString(entity));
        if(iEntity == null) {
            return;
        }
    
        if(!iEntity.getDropsToAdd().isEmpty()) {
            for(Map.Entry<IItemStack, IntRange> dropToAdd : iEntity.getDropsToAdd().entrySet()) {
                final IItemStack key = dropToAdd.getKey();
                final IntRange val = dropToAdd.getValue();
                
                final EntityItem item;
                if(val.getMin() == 0 && val.getMax() == 0) {
                    item = new EntityItem(entity.worldObj, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                } else {
                    item = new EntityItem(entity.worldObj, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                }
                ev.drops.add(item);
            }
        }
    
        if(!iEntity.getDropsToAddPlayerOnly().isEmpty() && ev.source.getEntity() instanceof EntityPlayer) {
            for(Map.Entry<IItemStack, IntRange> dropToAdd : iEntity.getDropsToAddPlayerOnly().entrySet()) {
                final IItemStack key = dropToAdd.getKey();
                final IntRange val = dropToAdd.getValue();
            
                final EntityItem item;
                if(val.getMin() == 0 && val.getMax() == 0) {
                    item = new EntityItem(entity.worldObj, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.getInternal()).copy());
                } else {
                    item = new EntityItem(entity.worldObj, entity.posX + 0.5, entity.posY + 0.5, entity.posZ + 0.5, ((ItemStack) key.withAmount(val.getRandom()).getInternal()).copy());
                }
                ev.drops.add(item);
            }
        }
        
        if(!iEntity.getDropsToRemove().isEmpty()) {
            for(IItemStack iItemStack : iEntity.getDropsToRemove()) {
                for(Iterator<EntityItem> iterator = ev.drops.iterator(); iterator.hasNext(); ) {
                    EntityItem drop = iterator.next();
                    if(iItemStack.matches(new MCItemStack(drop.getEntityItem()))) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
