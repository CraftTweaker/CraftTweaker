package crafttweaker.mc1120.enchantments;

import crafttweaker.api.data.*;
import crafttweaker.api.enchantments.*;
import crafttweaker.api.item.IItemStack;
import crafttweaker.mc1120.item.MCItemStack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.*;

public class MCEnchantment implements IEnchantment {
    
    private final Enchantment definition;
    private int level;
    
    public MCEnchantment(int id, int lvl) {
        this(Enchantment.getEnchantmentByID(id), lvl);
    }
    
    public MCEnchantment(Enchantment enchantment, int level) {
        this.definition = enchantment;
        this.level = level;
    }
    
    @Override
    public IEnchantmentDefinition getDefinition() {
        return new MCEnchantmentDefinition(definition);
    }

    @Override
    public Map<String, IData> makeTagInternal() {
        Map<String, IData> map = new HashMap<>();
        map.put("id", new DataShort((short) getDefinition().getID()));
        map.put("lvl", new DataShort((short) level));
        return map;
    }

    @Override
    public IData makeTag() {
        return new DataMap(Collections.singletonMap("ench", new DataList(Collections.singletonList(new DataMap(makeTagInternal(), false)), false)), false);
    }

    @Override
    public IData makeBookTag () {
        return new DataMap(Collections.singletonMap("StoredEnchantments", new DataList(Collections.singletonList(new DataMap(makeTagInternal(), false)), false)), false);
    }

    @Override
    public IItemStack makeBook () {
        NBTTagCompound result = new NBTTagCompound();
        result.setTag("StoredEnchantments", new NBTTagList());
        result.getTagList("StoredEnchantments", Constants.NBT.TAG_COMPOUND).appendTag(makeNBTInternal());
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        book.setTagCompound(result);
        return new MCItemStack(book);
    }

    public NBTTagCompound makeNBTInternal () {
        NBTTagCompound enchant = new NBTTagCompound();
        enchant.setShort("id", (short) getDefinition().getID());
        enchant.setShort("lvl", (short) level);
        return enchant;
    }

    @Override
    public int getLevel() {
        return level;
    }
    
    @Override
    public void setLevel(int level) {
        this.level = level;
    }
    
    @Override
    public String displayName() {
        return definition.getTranslatedName(level);
    }
}
