package crafttweaker.mc1120.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IItemUtils;
import crafttweaker.api.potions.IPotion;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author BloodWorkXGaming
 */
public class MCItemUtils implements IItemUtils {

    private static ArrayList<ItemStack> ITEMLIST;

    public static void createItemList() {
        ArrayList<ItemStack> itemList = new ArrayList<>();

        List<Item> items = new ArrayList<>(BracketHandlerItem.getItemNames().values());
        items.sort(Commands.ITEM_COMPARATOR);

        for (Item item : items) {
            if (item != null) {
                // gets list of subitems
                NonNullList<ItemStack> list = NonNullList.create();
                item.getSubItems(CreativeTabs.SEARCH, list);

                if (list.size() > 0) {
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
     * @return returns the {@link IItemStack} of the potion
     */
    public IItemStack createPotion(Object[]... params) {
        ItemStack item = new ItemStack(Items.POTIONITEM, 1, 0);

        List<PotionEffect> potionEffects = new ArrayList<>();

        for (Object[] param : params) {
            IPotion iPotion;
            int amplifier;
            int duration;

            if (param.length == 3) {
                if (param[0] instanceof IPotion) {
                    iPotion = (IPotion) param[0];
                } else {
                    continue;
                }

                if (param[1] instanceof Integer) {
                    amplifier = (int) param[1];
                } else {
                    continue;
                }

                if (param[2] instanceof Integer) {
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


    public IItemStack[] getItemsByRegexRegistryName(String regex) {
        ArrayList<ItemStack> matchedItems = new ArrayList<>();
        HashSet<String> alreadyChecked = new HashSet<>();
        Pattern p = Pattern.compile(regex);

        for (ItemStack stack : ITEMLIST) {
            String currentRegName = stack.getItem().getRegistryName().toString() + ":" + stack.getMetadata();
            // prevent duplicate checks on items with different NBT(which I ignore here)
            if (alreadyChecked.contains(currentRegName)) {
                continue;
            }
            alreadyChecked.add(currentRegName);

            Matcher m = p.matcher(currentRegName);
            if (m.matches()) {
                // clears away meta if it has
                if (stack.hasTagCompound()) {
                    matchedItems.add(new ItemStack(stack.getItem(), 1, stack.getMetadata()));
                } else {
                    matchedItems.add(stack);
                }
                // System.out.println("Matched regName: " + currentRegName);
            }
        }

        IItemStack[] iItemStacks = new IItemStack[matchedItems.size()];
        for (int i = 0; i < matchedItems.size(); i++) {
            iItemStacks[i] = new MCItemStack(matchedItems.get(i));
        }

        return iItemStacks;
    }


    public IItemStack[] getItemsByRegexUnlocalizedName(String regex) {
        ArrayList<ItemStack> matchedItems = new ArrayList<>();
        Pattern p = Pattern.compile(regex);

        for (ItemStack stack : ITEMLIST) {
            String currentUnlocalizedName = stack.getUnlocalizedName();

            Matcher m = p.matcher(currentUnlocalizedName);
            if (m.matches()) {
                matchedItems.add(stack);
                // String currentRegName = stack.getItem().getRegistryName().toString() + ":" + stack.getMetadata();
                // System.out.println("Matched unlocalized: " + currentUnlocalizedName + " [" + currentRegName + "]");
            }
        }

        IItemStack[] iItemStacks = new IItemStack[matchedItems.size()];
        for (int i = 0; i < matchedItems.size(); i++) {
            iItemStacks[i] = new MCItemStack(matchedItems.get(i));
        }

        return iItemStacks;
    }


    public IItemStack createSpawnEgg(IEntityDefinition entity, @Optional IData customNBT) {
        ItemStack item = new ItemStack(Items.SPAWN_EGG, 1, 0);
        NBTTagCompound baseTag;
        if (customNBT != null) {
            baseTag = (NBTTagCompound) NBTConverter.from(customNBT);
        } else {
            baseTag = new NBTTagCompound();
        }

        NBTTagCompound entityTag;
        if (baseTag.hasKey("EntityTag")) {
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
