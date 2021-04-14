package crafttweaker.mods.jei.commands;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Range;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.CraftTweaker;
import crafttweaker.mc1120.commands.CraftTweakerCommand;
import crafttweaker.mods.jei.JEIAddonPlugin;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.ingredients.Ingredients;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.plugins.vanilla.crafting.TippedArrowRecipeWrapper;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Loader;

import java.io.Serializable;
import java.util.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

/**
 * @author BloodWorkXGaming
 */
public class ConflictCommand extends CraftTweakerCommand {

    public static final CraftRecipeEntryComparator CRAFT_RECIPE_ENTRY_COMPARATOR = new CraftRecipeEntryComparator();


    private List<CraftingRecipeEntry> craftingRecipeEntries = new ArrayList<>();
    private TIntObjectMap<Range<Integer>> rangeMap;

    public ConflictCommand() {
        super("conflict");
    }

    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct conflict", "/ct conflict", true), getNormalMessage(" \u00A73Lists all conflicting crafting recipes in the game"), getNormalMessage(" \u00A73Might take a bit of time depending on the size of the pack"), getNormalMessage(" \u00A73This needs to be run on a client and with JEI installed"));
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

        if (JEIAddonPlugin.recipeRegistry == null || !Loader.isModLoaded("jei")) {
            sender.sendMessage(getNormalMessage("\u00A74This command can only be executed on the Client and with JEI installed"));
            CraftTweakerAPI.logWarning("JEI plugin not loaded!");
        } else {

            sender.sendMessage(getNormalMessage("\u00A76Running Conflict scan. This might take a while"));

            new Thread("ConflictScan_mc_thread") {
                @Override
                public void run() {
                    try {
                        runConflictScan();
                        sender.sendMessage(getLinkToCraftTweakerLog("Conflicting Recipe list generated", sender));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }


    private void runConflictScan() {
        if (JEIAddonPlugin.recipeRegistry == null || !Loader.isModLoaded("jei")) {
            CraftTweakerAPI.logWarning("JEI plugin not loaded yet!");
            CraftTweakerAPI.logWarning("This command can only be used on a client and with JEI installed");
            return;
        }

        // cleans up before running the command
        craftingRecipeEntries.clear();
        if (rangeMap != null)
            rangeMap.clear();

        // prepares data needed for conflict scan
        gatherRecipes();
        craftingRecipeEntries.sort(CRAFT_RECIPE_ENTRY_COMPARATOR);
        rangeMap = getRangeForSize();

        rangeMap.forEachEntry((k, v) -> {
            CraftTweaker.LOG.info(k + ": " + v);
            return true;
        });

        // scans for conflicts
        List<Map.Entry<CraftingRecipeEntry, CraftingRecipeEntry>> conflictingRecipes = new ArrayList<>();
        int maxCount = craftingRecipeEntries.size();
        float lastProgress = 0;

        for (int i = 0; i < maxCount; i++) {
            craftingRecipeEntries.get(i).checkAllConflicting(conflictingRecipes);

            float progress = (float) i / (float) maxCount;

            if (progress - lastProgress > 0.05) {
                int percentage = (int) ((progress) * 100);
                CraftTweaker.LOG.info("Current Progress on the scan: " + percentage + "%");
                lastProgress = progress;
            }
        }


        // prints conflicts
        CraftTweakerAPI.logInfo("Conflicting: " + conflictingRecipes.size());
        conflictingRecipes.forEach(it -> {
            String shape1 = it.getKey().shapedRecipe ? "[Shaped] " : "[Shapeless] ";
            String shape2 = it.getValue().shapedRecipe ? "[Shaped] " : "[Shapeless] ";

            String name1 = "<" + it.getKey().output.getItem().getRegistryName() + ":" + it.getKey().output.getMetadata() + "> * " + it.getKey().output.getCount();
            String name2 = "<" + it.getValue().output.getItem().getRegistryName() + ":" + it.getValue().output.getMetadata() + "> * " + it.getValue().output.getCount();

            String name1dp = "[" + it.getKey().recipeName + "](" + it.getKey().output.getDisplayName() + ") ";
            String name2dp = "[" + it.getValue().recipeName + "](" + it.getValue().output.getDisplayName() + ") ";

            CraftTweakerAPI.logInfo(shape1 + name1dp + name1 + " conflicts with " + shape2 + name2dp + name2);
        });

    }

    /**
     * Collects all the ranges for the recipe Size so it doesn't have to check every Recipe
     * Has to be sorted beforehand
     */
    private TIntObjectMap<Range<Integer>> getRangeForSize() {
        TIntObjectMap<Range<Integer>> rangeMap = new TIntObjectHashMap<>();

        int recipeSize = 0;
        int recipeCount = 0;
        for (int i = 0; i < craftingRecipeEntries.size(); i++) {
            CraftingRecipeEntry current = craftingRecipeEntries.get(i);


            if (recipeSize == current.recipeSize) {
                recipeCount++;
            }

            if (current.recipeSize > recipeSize) {
                rangeMap.put(recipeSize, Range.closedOpen(i - recipeCount, i));
                recipeSize = current.recipeSize;
                recipeCount = 1;
            }

        }

        rangeMap.put(recipeSize, Range.closedOpen(craftingRecipeEntries.size() - recipeCount, craftingRecipeEntries.size()));
        return rangeMap;
    }

    /**
     * Collects all recipes, converts them to the own class and adds them to the List
     */
    private void gatherRecipes() {
        IRecipeRegistry reg = JEIAddonPlugin.recipeRegistry;

        for (IRecipeCategory<?> category : reg.getRecipeCategories()) {
            if (category instanceof CraftingRecipeCategory) {

                List<? extends IRecipeWrapper> wrappers = reg.getRecipeWrappers(category);
                for (IRecipeWrapper wrapper : wrappers) {

                    if (wrapper instanceof ICraftingRecipeWrapper && !(wrapper instanceof TippedArrowRecipeWrapper)) {

                        ICraftingRecipeWrapper wrap = ((ICraftingRecipeWrapper) wrapper);
                        IIngredients ing = new Ingredients();
                        wrap.getIngredients(ing);


                        List<List<ItemStack>> inputs = ing.getInputs(VanillaTypes.ITEM);
                        List<List<ItemStack>> outputs = ing.getOutputs(VanillaTypes.ITEM);

                        // checks for having no outputs or having a "null" output
                        ItemStack output = outputs.size() > 0 ? outputs.get(0) != null ? outputs.get(0).size() > 0 ? outputs.get(0).get(0) : null : null : null;

                        // prevent checking recipes with "null" output
                        if (output == null) {
                            continue;
                        }

                        // check the lists for duplicate items if they are over 50 entries long
                        for (List<ItemStack> input : inputs) {
                            if (input.size() > 50) {
                                CraftTweakerAPI.logInfo("trimming list of size = " + input.size() + " for recipe with output " + output.getItem().getRegistryName() + ":" + output.getMetadata());

                                Set<Map.Entry<Item, Integer>> hasAlready = new HashSet<>();
                                Iterator<ItemStack> iter;
                                iter = input.iterator();

                                while (iter.hasNext()) {
                                    ItemStack stack = iter.next();
                                    AbstractMap.SimpleEntry<Item, Integer> entry = new AbstractMap.SimpleEntry<>(stack.getItem(), stack.getMetadata());
                                    if (hasAlready.contains(entry)) {
                                        iter.remove();
                                    } else {
                                        hasAlready.add(entry);
                                    }
                                }
                            }
                        }

                        // differs shaped an shapeless recipes
                        if (wrapper instanceof IShapedCraftingRecipeWrapper) {
                            craftingRecipeEntries.add(new CraftingRecipeEntry(inputs, output, ((IShapedCraftingRecipeWrapper) wrapper).getWidth(), ((IShapedCraftingRecipeWrapper) wrapper).getHeight(), wrap.getRegistryName() == null ? "noname" : wrap.getRegistryName().toString()));
                        } else {
                            craftingRecipeEntries.add(new CraftingRecipeEntry(inputs, output, wrap.getRegistryName() == null ? "noname" : wrap.getRegistryName().toString()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Dumps all recipe to the log
     */
    public void dumpRecipes() {
        for (CraftingRecipeEntry entry : craftingRecipeEntries) {
            String s = entry.toString();
            CraftTweakerAPI.logInfo(s);
        }
    }

    /**
     * Comparator for sorting the recipes according to size of the recipe
     */
    private static class CraftRecipeEntryComparator implements Comparator<CraftingRecipeEntry>, Serializable {

        private static final long serialVersionUID = 9061636968374415469L;

        @Override
        public int compare(CraftingRecipeEntry o1, CraftingRecipeEntry o2) {
            return Integer.compare(o1.recipeSize, o2.recipeSize);
        }
    }

    /**
     * Own class for keeping track of the recipes
     * Makes it easier to compare
     */
    public class CraftingRecipeEntry {

        List<List<ItemStack>> inputs;
        ItemStack output;
        int width, height;
        boolean shapedRecipe;
        String recipeName;
        int recipeSize;

        /**
         * Shaped recipes
         */
        CraftingRecipeEntry(List<List<ItemStack>> inputs, ItemStack output, int width, int height, String recipeName) {
            this.inputs = inputs;
            this.output = output;
            this.width = width;
            this.height = height;
            this.recipeName = recipeName;
            this.recipeSize = inputs.size();
            this.shapedRecipe = true;
        }

        /**
         * Shapeless recipes
         */
        CraftingRecipeEntry(List<List<ItemStack>> inputs, ItemStack output, String recipeName) {
            this.inputs = inputs;
            this.output = output;
            this.recipeName = recipeName;
            this.recipeSize = inputs.size();
            this.shapedRecipe = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(shapedRecipe ? "(shaped)" : "(shapeless)");
            sb.append(output.toString());
            sb.append(" > ");
            int i = 0;
            for (List<ItemStack> list : inputs) {
                sb.append("\nList ").append(i++).append(" ").append("[").append(list.size()).append("]: ");
                for (ItemStack stack : list) {
                    sb.append(stack.toString()).append(" | ");
                }
            }

            return sb.toString();
        }

        void checkAllConflicting(List<Map.Entry<CraftingRecipeEntry, CraftingRecipeEntry>> entryList) {
            // gets the range where it has to search
            Range<Integer> range = rangeMap.get(this.recipeSize);
            for (int index : ContiguousSet.create(range, DiscreteDomain.integers())) {
                CraftingRecipeEntry otherEntry = craftingRecipeEntries.get(index);

                // skips comparison with self
                if (otherEntry == this)
                    continue;

                if (this.checkConflict(otherEntry)) {
                    // checks whether it is already in the list
                    if (!(entryList.contains(new AbstractMap.SimpleEntry<>(this, otherEntry))
                            || entryList.contains(new AbstractMap.SimpleEntry<>(otherEntry, this)))) {
                        entryList.add(new AbstractMap.SimpleEntry<>(this, otherEntry));
                    }
                }
            }
        }

        boolean checkConflict(CraftingRecipeEntry other) {
            if (shapedRecipe && other.shapedRecipe) {
                return checkShapedShaped(other);
            }

            if (!shapedRecipe && !other.shapedRecipe) {
                return checkShapelessShapeless(other);
            }

            return shapedRecipe && checkShapedShapeless(other);
        }

        boolean checkForEmptySlots(List<ItemStack> inputThis, List<ItemStack> inputOther) {

            //region >>> Checks whether both have empty slots
            // both really empty
            if (inputThis.isEmpty() && inputOther.isEmpty()) {
                return true;
            }

            // both ItemStack.Empty
            if (inputThis.size() == 1 && inputOther.size() == 1) {
                if (inputThis.get(0).isEmpty() && inputOther.get(0).isEmpty()) {
                    return true;
                }
            }

            // other == ItemStack.Empty
            if (inputThis.isEmpty() && inputOther.size() == 1) {
                if (inputOther.get(0).isEmpty()) {
                    return true;
                }
            }

            // this == ItemStack.Empty
            if (inputOther.isEmpty() && inputThis.size() == 1) {
                return inputThis.get(0).isEmpty();
            }
            //endregion


            return false;
        }

        boolean checkShapedShaped(CraftingRecipeEntry other) {
            int overlapping = 0;

            if (width == other.width && height == other.height) {

                // loops over all slots in the crafting table
                for (int i = 0; i < recipeSize; i++) {
                    // when it has one non conflict it is done for the recipe
                    if (overlapping != i)
                        break;

                    List<ItemStack> inputThis = inputs.get(i);
                    List<ItemStack> inputOther = other.inputs.get(i);

                    if (checkForEmptySlots(inputThis, inputOther)) {
                        overlapping++;
                        continue;
                    }

                    if (checkConflictingItemStacks(inputThis, inputOther)) {
                        overlapping++;
                    }
                }
            } else {
                return false;
            }

            return overlapping == recipeSize && !compareItemStack(this.output, other.output);
        }

        boolean checkConflictingItemStacks(List<ItemStack> stackListThis, List<ItemStack> stackListOther) {
            // has one item which is the same
            boolean hasOneConflict = false;

            for (ItemStack stackListThi : stackListThis) {
                for (ItemStack aStackListOther : stackListOther) {
                    if (compareItemStack(stackListThi, aStackListOther)) {
                        hasOneConflict = true;
                    }
                }
            }

            return hasOneConflict;
        }

        boolean checkShapelessShapeless(CraftingRecipeEntry other) {
            int overlapping = 0;

            if (recipeSize == other.recipeSize) {

                for (int i = 0; i < recipeSize; i++) {
                    // when it has one non conflict it is done for the recipe
                    if (overlapping != i)
                        break;

                    List<ItemStack> inputThis = inputs.get(i);
                    List<ItemStack> inputOther = other.inputs.get(i);

                    // has one item which is the same
                    boolean hasOneConflict = false;
                    for (ItemStack stack : inputThis) {

                        for (ItemStack otherStack : inputOther) {

                            if (compareItemStack(stack, otherStack)) {
                                hasOneConflict = true;
                            }
                        }
                    }

                    // Counts as overlap when it has at least one conflict
                    if (hasOneConflict) {
                        overlapping++;
                    }
                }
            } else {
                return false;
            }

            return overlapping == recipeSize && !compareItemStack(this.output, other.output);
        }

        boolean checkShapedShapeless(CraftingRecipeEntry other) {
            int overlapping = 0;

            // As shaped > shapeless only 3x3 recipes could conflict
            if ((recipeSize == 9 && other.recipeSize == 9)) {

                // loops over all slots in the crafting table
                for (int i = 0; i < recipeSize; i++) {

                    // when it has one non conflict it is done for the recipe
                    if (overlapping != i)
                        break;

                    List<ItemStack> inputThis = inputs.get(i);
                    List<ItemStack> inputOther = other.inputs.get(i);

                    // shaped recipe is empty, shapeless recipes can't be empty though
                    if (inputThis.isEmpty()) {
                        continue;
                    }

                    // has one item which is the same
                    boolean hasOneConflict = false;
                    for (ItemStack stack : inputThis) {
                        for (ItemStack otherStack : inputOther) {
                            if (compareItemStack(stack, otherStack)) {
                                hasOneConflict = true;
                            }
                        }
                    }

                    // Counts as overlap when it has at least one conflict
                    if (hasOneConflict) {
                        overlapping++;
                    }
                }
            }

            return overlapping == recipeSize && !compareItemStack(this.output, other.output);
        }

        /**
         * Compares the two {@link ItemStack} and checks for nbt
         */
        boolean compareItemStack(ItemStack stack1, ItemStack stack2) {
            if (stack1 == null || stack2 == null || stack1.isEmpty() || stack2.isEmpty())
                return false;
            boolean itemsAreSame = stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata();
            return itemsAreSame && Objects.equals(stack1.getTagCompound(), stack2.getTagCompound());
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof CraftingRecipeEntry
                    && inputs.equals(((CraftingRecipeEntry) obj).inputs)
                    && output.equals(((CraftingRecipeEntry) obj).output)
                    && width == ((CraftingRecipeEntry) obj).width
                    && height == ((CraftingRecipeEntry) obj).height
                    && shapedRecipe == ((CraftingRecipeEntry) obj).shapedRecipe
                    && recipeName.equals(((CraftingRecipeEntry) obj).recipeName)
                    && recipeSize == ((CraftingRecipeEntry) obj).recipeSize;
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(new Object[]{inputs, output, recipeName, recipeSize, shapedRecipe});
        }
    }
}
