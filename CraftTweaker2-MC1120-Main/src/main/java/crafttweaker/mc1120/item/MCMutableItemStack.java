package crafttweaker.mc1120.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IMutableItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.player.IPlayer;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.data.NBTUpdater;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

import java.util.Random;

/**
 * @author youyihj
 */
public class MCMutableItemStack extends MCItemStack implements IMutableItemStack {
    public MCMutableItemStack(ItemStack itemStack) {
        super(itemStack, NBTConverter.from(itemStack.getTagCompound(), false));
    }

    public MCMutableItemStack(ItemStack itemStack, boolean wildcardSize) {
        super(itemStack, NBTConverter.from(itemStack.getTagCompound(), false), wildcardSize);
    }

    @Override
    public void shrink(int quality) {
        origin.shrink(quality);
    }

    @Override
    public void grow(int quality) {
        origin.grow(quality);
    }

    @Override
    public boolean attemptDamageItem(int amount, IPlayer player) {
        EntityPlayer mcplayer = CraftTweakerMC.getPlayer(player);
        return origin.attemptDamageItem(amount, mcplayer != null ? mcplayer.world.rand : new Random(), (mcplayer instanceof EntityPlayerMP) ? (EntityPlayerMP) mcplayer : null);
    }

    @Override
    public IItemStack withTag(IData tag, boolean matchTagExact) {
        this.matchTagExact = matchTagExact;
        this.tag = tag;
        this.origin.setTagCompound(CraftTweakerMC.getNBTCompound(tag));
        return this;
    }

    @Override
    public IItemStack updateTag(IData tagUpdate, boolean matchTagExact) {
        this.matchTagExact = matchTagExact;
        if (tag == null) {
            if(origin.getTagCompound() == null) {
                return withTag(tagUpdate, matchTagExact);
            }

            tag = NBTConverter.from(origin.getTagCompound(), false);
        }
        NBTUpdater.updateMap(origin.getTagCompound(), tagUpdate);
        tag = tag.update(tagUpdate);
        return this;
    }

    @Override
    public IItemStack withEmptyTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        tag = NBTConverter.from(nbt, false);
        origin.setTagCompound(nbt);
        return this;
    }

    @Override
    public IItemStack removeTag(String tag) {
        if(tag == null) {
            origin.setTagCompound(null);
        } else {
            origin.getTagCompound().removeTag(tag);
        }
        this.tag = NBTConverter.from(origin.getTagCompound(), false);
        return this;
    }

    @Override
    public IItemStack withAmount(int amount) {
        origin.setCount(amount);
        return this;
    }

    @Override
    public IItemStack withDamage(int damage) {
        origin.setItemDamage(damage);
        return this;
    }

    @Override
    public IItemStack withDisplayName(String name) {
        NBTTagCompound tagComp;

        if(!this.origin.hasTagCompound() || this.origin.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.origin.getTagCompound();
        }

        NBTTagCompound display;
        if(!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)) {
            display = new NBTTagCompound();
        } else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }

        display.setString("Name", name);
        tagComp.setTag("display", display);

        origin.setTagCompound(tagComp);
        return this;
    }

    @Override
    public IItemStack withLore(String[] lore) {
        NBTTagCompound tagComp;

        if(!this.origin.hasTagCompound() || this.origin.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.origin.getTagCompound();
        }

        NBTTagCompound display;
        if(!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)) {
            display = new NBTTagCompound();
        } else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }

        NBTTagList loreList;
        if(!display.hasKey("Lore") || !(display.getTag("Lore") instanceof NBTTagList)) {
            loreList = new NBTTagList();
        } else {
            loreList = (NBTTagList) display.getTag("Lore");
        }

        for(String s : lore) {
            loreList.appendTag(new NBTTagString(s));
        }

        display.setTag("Lore", loreList);
        tagComp.setTag("display", display);

        origin.setTagCompound(tagComp);
        return this;
    }

    @Override
    public IItemStack copy() {
        return new MCItemStack(origin.copy());
    }

    @Override
    public IMutableItemStack mutable() {
        return origin.isEmpty() ? null : this;
    }

    @Override
    public void damageItem(int amount, IEntity entity) {
        if(entity.getInternal() instanceof EntityLivingBase)
            origin.damageItem(amount, (EntityLivingBase) entity.getInternal());
    }

    @Override
    public Object getInternal() {
        return origin;
    }
}
