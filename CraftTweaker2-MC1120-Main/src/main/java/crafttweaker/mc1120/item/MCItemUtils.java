package crafttweaker.mc1120.item;

import crafttweaker.api.item.*;
import crafttweaker.api.potions.IPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;

import java.util.*;

public class MCItemUtils implements IItemUtils{
    
    /**
     *
     * @param params has to be in this syntax:
     *               [<effect1>, strength, time], [<effect2>, strength, time], ...
     * @return returns the {@link IItemStack} of the potion
     */
    public IItemStack createPotion(int count, Object[]... params){
        NBTTagCompound tag = new NBTTagCompound();
    
        List<PotionEffect> potionEffects = new ArrayList<>();
        
        for(Object[] param: params) {
            IPotion iPotion;
            int amplifier;
            int duration;
            
            if (param.length == 3){
                if (param[0] instanceof IPotion){
                    iPotion = (IPotion) param[0];
                }else{
                    continue;
                }
                
                if (param[1] instanceof Integer){
                    amplifier = (int) param[1];
                }else{
                    continue;
                }
                
                if (param[2] instanceof Integer){
                    duration = (int) param[2];
                }else{
                    continue;
                }
                potionEffects.add(new PotionEffect(((Potion) iPotion.getInternal()), duration, amplifier));
            
            }
        }
        PotionUtils.addCustomPotionEffectToList(tag, potionEffects);
        
        return new MCItemStack(new ItemStack(Items.POTIONITEM, count, 0, tag));
    }


}
