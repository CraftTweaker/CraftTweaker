/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1710.oredict;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.*;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.oredict.IngredientOreDict;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1710.util.MineTweakerHacks;
import minetweaker.util.ArrayUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static minetweaker.api.minecraft.MineTweakerMC.getIItemStackWildcardSize;
import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class MCOreDictEntry implements IOreDictEntry {
    private static final List<ArrayList<ItemStack>> OREDICT_CONTENTS = MineTweakerHacks.getOreIdStacks();
    private static final List<ArrayList<ItemStack>> OREDICT_CONTENTS_UN = MineTweakerHacks.getOreIdStacksUn();

    private final int id;

    public MCOreDictEntry(Integer id) {
        this.id = id;
    }

    public MCOreDictEntry(String id) {
        this.id = OreDictionary.getOreID(id);
    }

    // ####################################
    // ### IOreDictEntry implementation ###
    // ####################################

    @Override
    public String getName() {
        return OreDictionary.getOreName(id);
    }

    @Override
    public boolean isEmpty() {
        return OreDictionary.getOres(id).isEmpty();
    }

    @Override
    public void add(IItemStack item) {
        ItemStack stack = getItemStack(item);
        if (stack != null) {
            MineTweakerAPI.apply(new ActionAddItem(id, stack));
        }
    }

    @Override
    public void addItems(IItemStack[] items) {
        for (IItemStack item : items) {
            ItemStack stack = getItemStack(item);
            if (stack != null) {
                MineTweakerAPI.apply(new ActionAddItem(id, stack));
            }
        }
    }

    @Override
    public IItemStack getFirstItem() {
        List<ItemStack> items = OreDictionary.getOres(id);
        if (items.isEmpty()) {
            return null;
        }
        return getIItemStackWildcardSize(items.get(0));
    }

    @Override
    public void addAll(IOreDictEntry entry) {
        if (entry instanceof MCOreDictEntry) {
            MineTweakerAPI.apply(new ActionAddAll(id, ((MCOreDictEntry) entry).id));
        } else {
            MineTweakerAPI.logError("not a valid entry");
        }
    }

    @Override
    public void remove(IItemStack item) {
        ItemStack result = null;
        for (ItemStack itemStack : OreDictionary.getOres(id)) {
            if (item.matches(getIItemStackWildcardSize(itemStack))) {
                result = itemStack;
                break;
            }
        }

        if (result != null) {
            MineTweakerAPI.apply(new ActionRemoveItem(id, result));
        }
    }

    @Override
    public void removeItems(IItemStack[] items) {
        for (IItemStack item : items) {
            ItemStack result = null;
            for (ItemStack itemStack : OreDictionary.getOres(id)) {
                if (item.matches(getIItemStackWildcardSize(itemStack))) {
                    result = itemStack;
                    break;
                }
            }

            if (result != null) {
                MineTweakerAPI.apply(new ActionRemoveItem(id, result));
            }
        }
    }

    @Override
    public boolean contains(IItemStack item) {
        for (ItemStack itemStack : OreDictionary.getOres(id)) {
            if (getIItemStackWildcardSize(itemStack).matches(item)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void mirror(IOreDictEntry other) {
        if (other instanceof MCOreDictEntry) {
            MineTweakerAPI.apply(new ActionMirror(id, ((MCOreDictEntry) other).id));
        } else {
            MineTweakerAPI.logError("not a valid oredict entry");
        }
    }

    @Override
    public String getMark() {
        return null;
    }

    @Override
    public int getAmount() {
        return 1;
    }

    @Override
    public List<IItemStack> getItems() {
        List<IItemStack> result = new ArrayList<IItemStack>();
        for (ItemStack item : OreDictionary.getOres(id)) {
            result.add(getIItemStackWildcardSize(item));
        }
        return result;
    }

    @Override
    public List<ILiquidStack> getLiquids() {
        return Collections.emptyList();
    }

    @Override
    public IIngredient amount(int amount) {
        return new IngredientStack(this, amount);
    }

    @Override
    public IIngredient transform(IItemTransformer transformer) {
        return new IngredientOreDict(this, null, ArrayUtil.EMPTY_CONDITIONS, new IItemTransformer[]{transformer});
    }

    @Override
    public IIngredient only(IItemCondition condition) {
        return new IngredientOreDict(this, null, new IItemCondition[]{condition}, ArrayUtil.EMPTY_TRANSFORMERS);
    }

    @Override
    public IIngredient marked(String mark) {
        return new IngredientOreDict(this, mark, ArrayUtil.EMPTY_CONDITIONS, ArrayUtil.EMPTY_TRANSFORMERS);
    }

    @Override
    public IIngredient or(IIngredient ingredient) {
        return new IngredientOr(this, ingredient);
    }

    @Override
    public boolean matches(IItemStack item) {
        return contains(item);
    }

    @Override
    public boolean matchesExact(IItemStack item) {
        return contains(item);
    }

    @Override
    public boolean matches(ILiquidStack liquid) {
        return false;
    }

    @Override
    public boolean contains(IIngredient ingredient) {
        List<IItemStack> items = ingredient.getItems();
        for (IItemStack item : items) {
            if (!matches(item))
                return false;
        }

        return true;
    }

    @Override
    public IItemStack applyTransform(IItemStack item, IPlayer byPlayer) {
        return item;
    }

    @Override
    public boolean hasTransformers() {
        return false;
    }

    @Override
    public Object getInternal() {
        return OreDictionary.getOreName(id);
    }

    // #############################
    // ### Object implementation ###
    // #############################

    @Override
    public String toString() {
        return "<ore:" + OreDictionary.getOreName(id) + ">";
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof MCOreDictEntry))
            return false;
        return ((MCOreDictEntry) other).id == id;
    }

    // ######################
    // ### Action classes ###
    // ######################

    private static class ActionAddItem implements IUndoableAction {
        private final Integer id;
        private final ItemStack item;

        public ActionAddItem(Integer id, ItemStack item) {
            this.id = id;
            this.item = item;
        }

        @Override
        public void apply() {
            OreDictionary.registerOre(id, item);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            OREDICT_CONTENTS.get(id).remove(item);
        }

        @Override
        public String describe() {
            return "Adding " + item.getDisplayName() + " to ore dictionary entry " + OreDictionary.getOreName(id);
        }

        @Override
        public String describeUndo() {
            return "Removing " + item.getDisplayName() + " from ore dictionary entry " + OreDictionary.getOreName(id);
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class ActionMirror implements IUndoableAction {
        private final Integer idTarget;
        private final Integer idSource;

        private final ArrayList<ItemStack> targetCopy;
        private final ArrayList<ItemStack> targetCopyUn;

        public ActionMirror(Integer idTarget, Integer idSource) {
            this.idTarget = idTarget;
            this.idSource = idSource;

            targetCopy = OREDICT_CONTENTS.get(idTarget);
            targetCopyUn = OREDICT_CONTENTS_UN.get(idTarget);
        }

        @Override
        public void apply() {
            OREDICT_CONTENTS.set(idTarget, OREDICT_CONTENTS.get(idSource));
            OREDICT_CONTENTS_UN.set(idTarget, OREDICT_CONTENTS_UN.get(idSource));
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            OREDICT_CONTENTS.set(idTarget, targetCopy);
            OREDICT_CONTENTS_UN.set(idTarget, targetCopyUn);
        }

        @Override
        public String describe() {
            return "Mirroring " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
        }

        @Override
        public String describeUndo() {
            return "Undoing mirror of " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class ActionRemoveItem implements IUndoableAction {
        private final Integer id;
        private final ItemStack item;

        public ActionRemoveItem(Integer id, ItemStack item) {
            this.id = id;
            this.item = item;
        }

        @Override
        public void apply() {
            OREDICT_CONTENTS.get(id).remove(item);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            OREDICT_CONTENTS.get(id).add(item);
        }

        @Override
        public String describe() {
            return "Removing " + item.getDisplayName() + " from ore dictionary entry " + OreDictionary.getOreName(id);
        }

        @Override
        public String describeUndo() {
            return "Restoring " + item.getDisplayName() + " to ore dictionary entry " + OreDictionary.getOreName(id);
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class ActionAddAll implements IUndoableAction {
        private final Integer idTarget;
        private final Integer idSource;

        public ActionAddAll(Integer idTarget, Integer idSource) {
            this.idTarget = idTarget;
            this.idSource = idSource;
        }

        @Override
        public void apply() {
            for (ItemStack stack : OreDictionary.getOres(idSource)) {
                OreDictionary.registerOre(idTarget, stack);
            }
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            for (ItemStack stack : OreDictionary.getOres(idSource)) {
                OREDICT_CONTENTS.get(idTarget).remove(stack);
            }
        }

        @Override
        public String describe() {
            return "Copying contents of ore dictionary entry " + OreDictionary.getOreName(idSource) + " to " + OreDictionary.getOreName(idTarget);
        }

        @Override
        public String describeUndo() {
            return "Removing contents of ore dictionary entry " + OreDictionary.getOreName(idSource) + " from " + OreDictionary.getOreName(idTarget);
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
