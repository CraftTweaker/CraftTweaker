/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.oredict;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.*;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.api.oredict.IOreDictEntry;
import minetweaker.api.oredict.IngredientOreDict;
import minetweaker.api.player.IPlayer;
import minetweaker.mc1102.util.MineTweakerHacks;
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
    private static final List<List<ItemStack>> OREDICT_CONTENTS_UN = MineTweakerHacks.getOreIdStacksUn();


    private final String id;

    public MCOreDictEntry(String id) {
        this.id = id;
    }


    // ####################################
    // ### IOreDictEntry implementation ###
    // ####################################

    @Override
    public String getName() {
        return id;
    }

    @Override
    public boolean isEmpty() {
        return OreDictionary.getOres(getName()).isEmpty();
    }

    @Override
    public IItemStack getFirstItem() {
        return getItems().get(0);
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
        for (ItemStack itemStack : OreDictionary.getOres(getName())) {
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
        for (ItemStack itemStack : OreDictionary.getOres(getName())) {
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
        for (ItemStack item : OreDictionary.getOres(getName())) {
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
        return id;
    }

    // #############################
    // ### Object implementation ###
    // #############################

    @Override
    public String toString() {
        return "<ore:" + id + ">";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
        private final String id;
        private final ItemStack item;

        public ActionAddItem(String id, ItemStack item) {
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
            int oreId = OreDictionary.getOreID(id);
            OREDICT_CONTENTS.get(oreId).remove(item);
        }

        @Override
        public String describe() {
            return "Adding " + item.getDisplayName() + " to ore dictionary entry " + id;
        }

        @Override
        public String describeUndo() {
            return "Removing " + item.getDisplayName() + " from ore dictionary entry " + id;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }

    }

    private static class ActionMirror implements IUndoableAction {
        private final String idTarget;
        private final String idSource;

        private final ArrayList<ItemStack> targetCopy;
        private final List<ItemStack> targetCopyUn;

        public ActionMirror(String idTarget, String idSource) {
            this.idTarget = idTarget;
            this.idSource = idSource;

            int targetOreId = OreDictionary.getOreID(idTarget);
            targetCopy = OREDICT_CONTENTS.get(targetOreId);
            targetCopyUn = OREDICT_CONTENTS_UN.get(targetOreId);
        }

        @Override
        public void apply() {
            int sourceOreId = OreDictionary.getOreID(idSource);
            int targetOreId = OreDictionary.getOreID(idTarget);
            OREDICT_CONTENTS.set(targetOreId, OREDICT_CONTENTS.get(sourceOreId));
            OREDICT_CONTENTS_UN.set(targetOreId, OREDICT_CONTENTS_UN.get(sourceOreId));
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            int targetOreId = OreDictionary.getOreID(idTarget);
            OREDICT_CONTENTS.set(targetOreId, targetCopy);
            OREDICT_CONTENTS_UN.set(targetOreId, targetCopyUn);
        }

        @Override
        public String describe() {
            return "Mirroring " + idSource + " to " + idTarget;
        }

        @Override
        public String describeUndo() {
            return "Undoing mirror of " + idSource + " to " + idTarget;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class ActionRemoveItem implements IUndoableAction {
        private final String id;
        private final ItemStack item;

        public ActionRemoveItem(String id, ItemStack item) {
            this.id = id;
            this.item = item;
        }

        @Override
        public void apply() {
            int oreId = OreDictionary.getOreID(id);
            OREDICT_CONTENTS.get(oreId).remove(item);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            int oreId = OreDictionary.getOreID(id);
            OREDICT_CONTENTS.get(oreId).add(item);
        }

        @Override
        public String describe() {
            return "Removing " + item.getDisplayName() + " from ore dictionary entry " + id;
        }

        @Override
        public String describeUndo() {
            return "Restoring " + item.getDisplayName() + " to ore dictionary entry " + id;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    private static class ActionAddAll implements IUndoableAction {
        private final String idTarget;
        private final String idSource;

        public ActionAddAll(String idTarget, String idSource) {
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
            int targetOreId = OreDictionary.getOreID(idTarget);
            for (ItemStack stack : OreDictionary.getOres(idSource)) {
                OREDICT_CONTENTS.get(targetOreId).remove(stack);
            }
        }

        @Override
        public String describe() {
            return "Copying contents of ore dictionary entry " + idSource + " to " + idTarget;
        }

        @Override
        public String describeUndo() {
            return "Removing contents of ore dictionary entry " + idSource + " from " + idTarget;
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }
}
