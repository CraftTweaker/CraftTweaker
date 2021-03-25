package crafttweaker.mc1120.item;

import crafttweaker.api.data.IData;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IMutableItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.data.NBTUpdater;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class MCMutableItemStack extends MCItemStack implements IMutableItemStack {
    public MCMutableItemStack(ItemStack itemStack) {
        super(itemStack);
        NBTTagCompound nbt = itemStack.getTagCompound();
        tag = nbt == null ? null : NBTConverter.from(nbt, false);
    }

    @Override
    public void shrink(int quality) {
        stack.shrink(quality);
    }

    @Override
    public void grow(int quality) {
        stack.grow(quality);
    }

    @Override
    public IItemStack withTag(IData tag, boolean matchTagExact) {
        this.matchTagExact = matchTagExact;
        this.tag = tag;
        this.stack.setTagCompound(CraftTweakerMC.getNBTCompound(tag));
        return this;
    }

    @Override
    public IItemStack updateTag(IData tagUpdate, boolean matchTagExact) {
        if (tag == null) {
            if(stack.getTagCompound() == null) {
                return withTag(tagUpdate, matchTagExact);
            }

            tag = NBTConverter.from(stack.getTagCompound(), false);
        }
        NBTUpdater.updateMap(stack.getTagCompound(), tagUpdate);
        tag = tag.update(tagUpdate);
        return this;
    }

    @Override
    public IItemStack withEmptyTag() {
        NBTTagCompound nbt = new NBTTagCompound();
        tag = NBTConverter.from(nbt, false);
        stack.setTagCompound(nbt);
        return this;
    }

    @Override
    public IItemStack removeTag(String tag) {
        if(tag == null) {
            stack.setTagCompound(null);
        } else {
            stack.getTagCompound().removeTag(tag);
        }
        this.tag = NBTConverter.from(stack.getTagCompound(), false);
        return this;
    }

    @Override
    public IItemStack withAmount(int amount) {
        stack.setCount(amount);
        return this;
    }

    @Override
    public IItemStack withDamage(int damage) {
        stack.setItemDamage(damage);
        return this;
    }

    @Override
    public IItemStack withDisplayName(String name) {
        NBTTagCompound tagComp;

        if(!this.stack.hasTagCompound() || this.stack.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.stack.getTagCompound();
        }

        NBTTagCompound display;
        if(!tagComp.hasKey("display") || !(tagComp.getTag("display") instanceof NBTTagCompound)) {
            display = new NBTTagCompound();
        } else {
            display = (NBTTagCompound) tagComp.getTag("display");
        }

        display.setString("Name", name);
        tagComp.setTag("display", display);

        stack.setTagCompound(tagComp);
        return this;
    }

    @Override
    public IItemStack withLore(String[] lore) {
        NBTTagCompound tagComp;

        if(!this.stack.hasTagCompound() || this.stack.getTagCompound() == null) {
            tagComp = new NBTTagCompound();
        } else {
            tagComp = this.stack.getTagCompound();
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

        stack.setTagCompound(tagComp);
        return this;
    }

    @Override
    public IItemStack copy() {
        return new MCItemStack(stack.copy());
    }

    @Override
    public Object getInternal() {
        return stack;
    }
}
