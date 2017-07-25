package crafttweaker.mc1120.commands;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

/**
 * @author BloodWorkXGaming
 */
public class CommandUtils {
    
    public static List<String> getOreDictOfItem(ItemStack stack) {
        int[] ids = OreDictionary.getOreIDs(stack);
        List<String> names = new ArrayList<>();
        
        for(int id : ids) {
            names.add(OreDictionary.getOreName(id));
        }
        
        return names;
    }
    
    
    public static RayTraceResult getPlayerLookat(EntityPlayer player, double range) {
        Vec3d eyes = player.getPositionEyes(1.0F);
        return player.getEntityWorld().rayTraceBlocks(eyes, eyes.add(player.getLookVec().scale(range)));
    }
}
