package crafttweaker.mc1120.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IItemUtils;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotion;
import crafttweaker.api.potions.IPotionEffect;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.commands.Commands;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;
import stanhebben.zenscript.annotations.Optional;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author BloodWorkXGaming
 */
public class MCItemUtils implements IItemUtils {
    
    private static ArrayList<ItemStack> ITEMLIST;
    
    public static void createItemList() {
        ArrayList<ItemStack> itemList = new ArrayList<>();
        
        List<Item> items = new ArrayList<>(BracketHandlerItem.getItemNames().values());
        items.sort(Commands.ITEM_COMPARATOR);
        
        for(Item item : items) {
            if(item != null) {
                // gets list of subitems
                NonNullList<ItemStack> list = NonNullList.create();
                item.getSubItems(CreativeTabs.SEARCH, list);
                
                if(!list.isEmpty()) {
                    itemList.addAll(list);
                } else {
                    ItemStack stack = new ItemStack(item, 1, 0);
                    itemList.add(stack);
                }
            }
        }
        
        ITEMLIST = itemList;
    }
    
    
    /**
     * @param params has to be in this syntax:
     *               [<effect1>, strength, time], [<effect2>, strength, time], ...
     *
     * @return returns the {@link IItemStack} of the potion
     */
    public IItemStack createPotion(Object[]... params) {
        ItemStack item = new ItemStack(Items.POTIONITEM, 1, 0);
        
        List<PotionEffect> potionEffects = new ArrayList<>();
        
        for(Object[] param : params) {
            IPotion iPotion;
            int amplifier;
            int duration;
            
            if(param.length == 3) {
                if(param[0] instanceof IPotion) {
                    iPotion = (IPotion) param[0];
                } else {
                    continue;
                }
                
                if(param[1] instanceof Integer) {
                    amplifier = (int) param[1];
                } else {
                    continue;
                }
                
                if(param[2] instanceof Integer) {
                    duration = (int) param[2];
                } else {
                    continue;
                }
                PotionEffect effect = new PotionEffect(((Potion) iPotion.getInternal()), duration, amplifier);
                potionEffects.add(effect);
                
            }
        }
        
        // potionEffects.forEach(it -> System.out.println(it.toString()));
        PotionUtils.appendEffects(item, potionEffects);
        
        return new MCItemStack(item);
    }

    /**
    * @param params is a series of IPotionEffects.
    *
    * @return returns the {@link IItemStack} of the potion
    */
    @Override
    public IItemStack createPotion(IPotionEffect... params) {
        ItemStack item = new ItemStack(Items.POTIONITEM, 1, 0);

        List<PotionEffect> potionEffects = Stream.of(params).map(k -> (PotionEffect) k.getInternal()).collect(Collectors.toList());
        PotionUtils.appendEffects(item, potionEffects);

        return new MCItemStack(item);
    }

    public IItemStack[] getItemsByRegexRegistryName(String regex) {
        final HashSet<String> alreadyChecked = new HashSet<>();
        final Predicate<String> predicate = Pattern.compile(regex).asPredicate();
        
        return ITEMLIST.stream()
                //No null resource locations, no already checked locations, and only those who match
                .filter(stack -> {
                    final String currentRegName = Objects.toString(stack.getItem().getRegistryName(), null);
                    return currentRegName != null && alreadyChecked.add(currentRegName) && predicate.test(currentRegName);
                })
                .map(stack -> stack.hasTagCompound() ? new ItemStack(stack.getItem(), 1, stack.getMetadata()) : stack)
                .map(CraftTweakerMC::getIItemStack)
                .filter(Objects::nonNull)
                .toArray(IItemStack[]::new);
    }
    
    @Override
    public IItemStack getItem(String location, @Optional int meta) {
        return BracketHandlerItem.getItem(location, meta);
    }
    
    
    public IItemStack[] getItemsByRegexUnlocalizedName(String regex) {
        final Predicate<String> predicate = Pattern.compile(regex).asPredicate();
        
        return ITEMLIST.stream()
                .filter(stack -> predicate.test(stack.getUnlocalizedName()))
                .map(CraftTweakerMC::getIItemStack)
                .filter(Objects::nonNull)
                .toArray(IItemStack[]::new);
    }
    
    
    public IItemStack createSpawnEgg(IEntityDefinition entity, @Optional IData customNBT) {
        ItemStack item = new ItemStack(Items.SPAWN_EGG, 1, 0);
        NBTTagCompound baseTag;
        if(customNBT != null) {
            baseTag = (NBTTagCompound) NBTConverter.from(customNBT);
        } else {
            baseTag = new NBTTagCompound();
        }
        
        NBTTagCompound entityTag;
        if(baseTag.hasKey("EntityTag")) {
            entityTag = baseTag.getCompoundTag("EntityTag");
        } else {
            entityTag = new NBTTagCompound();
        }
        
        entityTag.setString("id", entity.getId());
        baseTag.setTag("EntityTag", entityTag);
        item.setTagCompound(baseTag);
        
        return new MCItemStack(item);
    }
    
    
}
