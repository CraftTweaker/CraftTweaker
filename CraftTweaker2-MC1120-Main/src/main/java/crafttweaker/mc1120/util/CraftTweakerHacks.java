package crafttweaker.mc1120.util;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.WeightedItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.*;
import java.util.*;
import java.util.logging.*;

/**
 * Common class for all runtime hacks (stuff requiring reflection). It is not
 * unexpected to have it break with new minecraft versions and may need regular
 * adjustment - as such, those have been collected here.
 *
 * @author Stan Hebben
 */
public class CraftTweakerHacks {
    
    private static final Field OREDICTIONARY_IDTOSTACK = getField(OreDictionary.class, "idToStack");
    private static final Field OREDICTIONARY_IDTOSTACKUN = getField(OreDictionary.class, "idToStackUn");
    
    private static final Field SEEDENTRY_SEED;
    private static final Constructor<? extends WeightedRandom.Item> SEEDENTRY_CONSTRUCTOR;
    
    static {
        Class<? extends WeightedRandom.Item> forgeSeedEntry = null;
        try {
            forgeSeedEntry = (Class<? extends WeightedRandom.Item>) Class.forName("net.minecraftforge.common.ForgeHooks$SeedEntry");
        } catch(ClassNotFoundException ignored) {
        }
        
        SEEDENTRY_SEED = getField(forgeSeedEntry, "seed");
        
        Constructor<? extends WeightedRandom.Item> seedEntryConstructor = null;
        
        try {
            seedEntryConstructor = forgeSeedEntry.getConstructor(ItemStack.class, int.class);
            seedEntryConstructor.setAccessible(true);
        } catch(NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(CraftTweakerHacks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SEEDENTRY_CONSTRUCTOR = seedEntryConstructor;
    }
    
    private CraftTweakerHacks() {
    }
    
    public static List<WeightedRandom.Item> getSeeds() {
        return getPrivateStaticObject(ForgeHooks.class, "seedList");
    }
    
    
    public static List<NonNullList<ItemStack>> getOreIdStacks() {
        try {
            return (List<NonNullList<ItemStack>>) OREDICTIONARY_IDTOSTACK.get(null);
        } catch(IllegalAccessException ex) {
            CraftTweakerAPI.logError("ERROR - could not load ore dictionary stacks!");
            return null;
        }
    }
    
    public static List<NonNullList<ItemStack>> getOreIdStacksUn() {
        try {
            return (List<NonNullList<ItemStack>>) OREDICTIONARY_IDTOSTACKUN.get(null);
        } catch(IllegalAccessException ex) {
            CraftTweakerAPI.logError("ERROR - could not load ore dictionary stacks!");
            return null;
        }
    }
    
    public static ItemStack getSeedEntrySeed(Object entry) {
        try {
            return (ItemStack) SEEDENTRY_SEED.get(entry);
        } catch(IllegalAccessException ex) {
            CraftTweakerAPI.logError("could not get SeedEntry seed");
            return null;
        }
    }
    
    
    public static WeightedRandom.Item constructSeedEntry(WeightedItemStack stack) {
        try {
            return SEEDENTRY_CONSTRUCTOR.newInstance(CraftTweakerMC.getItemStack(stack.getStack()), (int) stack.getPercent());
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            CraftTweakerAPI.logError("could not construct SeedEntry");
        }
        
        return null;
    }
    
    public static Map<String, String> getTranslations() {
        return getPrivateObject(getPrivateStaticObject(I18n.class, "localizedName", "field_74839_a"), "languageList", "field_74816_c");
    }
    
    public static <T> T getPrivateObject(Object object, String... names) {
        Class<?> cls = object.getClass();
        for(String name : names) {
            try {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return (T) field.get(object);
            } catch(NoSuchFieldException | SecurityException | IllegalAccessException ignored) {
            
            }
        }
        
        return null;
    }
    
    public static <T> T getPrivateStaticObject(Class<?> cls, String... names) {
        for(String name : names) {
            try {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return (T) field.get(null);
            } catch(NoSuchFieldException | SecurityException | IllegalAccessException ignored) {
    
            }
        }
        
        return null;
    }

    private static Field getField(Class<?> cls, String... names) {
        for(String name : names) {
            try {
                Field field = cls.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch(NoSuchFieldException | SecurityException ignored) {
            }
        }

        return null;
    }
}
