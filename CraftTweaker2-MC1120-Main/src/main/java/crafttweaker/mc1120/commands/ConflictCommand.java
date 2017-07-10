package crafttweaker.mc1120.commands;

import crafttweaker.CraftTweakerAPI;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.ingredients.Ingredients;
import mezz.jei.plugins.vanilla.crafting.CraftingRecipeCategory;
import mezz.jei.plugins.vanilla.crafting.TippedArrowRecipeWrapper;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

import java.util.*;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

/**
 * @author BloodWorkXGaming
 */
public class ConflictCommand  extends CraftTweakerCommand{

    private List<CraftingRecipeEntry> craftingRecipeEntries = new ArrayList<>();

    public ConflictCommand() {
        super("conflict");
    }

    @Override
    protected void init() {
        setDescription(
                getClickableCommandText("\u00A72/ct conflict", "/ct conflict", true),
                getNormalMessage(" \u00A73Lists all conflicting crafting recipes in the game"),
                getNormalMessage(" \u00A73Might take a bit of time depending on the size of the pack")
        );
    }

    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {

        if (CrTJEIPlugin.JEI_RUNTIME == null){
            sender.sendMessage(getNormalMessage("\u00A74This command can only be executed on the Client and with JEI installed"));
            CraftTweakerAPI.logWarning("JEI plugin not loaded!");
            return;
        }

        sender.sendMessage(getNormalMessage("\u00A76Running Conflict scan. This might take a while"));

        runConflictScan();

        sender.sendMessage(getLinkToCraftTweakerLog("Conflicting Recipe list generated", sender));
    }



    public void runConflictScan(){
        if (CrTJEIPlugin.JEI_RUNTIME == null){
            CraftTweakerAPI.logWarning("JEI plugin not loaded yet!");
            CraftTweakerAPI.logWarning("This command can only be used on a client and with JEI installed");
            return;
        }

        gatherRecipes();

        craftingRecipeEntries.sort(new CraftRecipeEntryComparator());

        List<Map.Entry<CraftingRecipeEntry, CraftingRecipeEntry>> conflictingRecipes = new ArrayList<>();

        craftingRecipeEntries.forEach(it -> it.checkAllConflicting(conflictingRecipes));

        CraftTweakerAPI.logInfo("Conflicting: " + conflictingRecipes.size());
        conflictingRecipes.forEach(it -> {
            String name1 = "<" + it.getKey().output.getItem().getRegistryName().toString() + ":" + it.getKey().output.getMetadata() + "> * " + it.getKey().output.getCount();
            String name2 = "<" + it.getValue().output.getItem().getRegistryName().toString() + ":" + it.getValue().output.getMetadata() + "> * " + it.getValue().output.getCount();

            CraftTweakerAPI.logInfo(name1 + " conflicts with " + name2);
        });
    }


    /**
     * Collects all recipes, converts them to the own class and adds them to the List
     */
    private void gatherRecipes(){
        IRecipeRegistry reg = CrTJEIPlugin.JEI_RUNTIME.getRecipeRegistry();

        for (IRecipeCategory category :reg.getRecipeCategories()) {
            if (category instanceof CraftingRecipeCategory){

                List wrappers = reg.getRecipeWrappers(category);
                for (Object wrapper: wrappers) {

                    if (wrapper instanceof IRecipeWrapper && !(wrapper instanceof TippedArrowRecipeWrapper)){

                        IRecipeWrapper wrap = ((IRecipeWrapper) wrapper);
                        IIngredients ing = new Ingredients();
                        wrap.getIngredients(ing);


                        List<List<ItemStack>> inputs = ing.getInputs(ItemStack.class);


                        List<List<ItemStack>> outputs = ing.getOutputs(ItemStack.class);
                        ItemStack output = outputs.get(0) == null ? null : outputs.get(0).get(0);
                        // prevent checking recipes with "null" output
                        if (output == null){
                            continue;
                        }

                        // differs shaped an shapeless recipes
                        if (wrapper instanceof IShapedCraftingRecipeWrapper){
                            craftingRecipeEntries.add(new CraftingRecipeEntry(inputs, output, ((IShapedCraftingRecipeWrapper) wrapper).getWidth(), ((IShapedCraftingRecipeWrapper) wrapper).getHeight(), "noname"));
                        }else {
                            craftingRecipeEntries.add(new CraftingRecipeEntry(inputs, output, inputs.size(), "noname"));
                        }
                    }
                }
            }
        }
    }

    /**
     * Dumps all recipe to the log
     */
    public void dumpRecipes(){
        for (CraftingRecipeEntry entry :craftingRecipeEntries) {
            String s = entry.toString();
            CraftTweakerAPI.logInfo(s);
        }
    }

    public class CraftingRecipeEntry{
        public List<List<ItemStack>> inputs;
        public ItemStack output;
        public int width, height;
        public boolean shapedRecipe;
        public String recipeName;
        public int recipeSize;


        // Shaped recipes
        public CraftingRecipeEntry(List<List<ItemStack>> inputs, ItemStack output, int width, int height, String recipeName){
            this.inputs = inputs;
            this.output = output;
            this.width = width;
            this.height = height;
            this.recipeName = recipeName;
            this.recipeSize = height * width;
            this.shapedRecipe = true;
        }

        // Shapeless recipes
        public CraftingRecipeEntry(List<List<ItemStack>> inputs, ItemStack output, int recipeSize, String recipeName){
            this.inputs = inputs;
            this.output = output;
            this.recipeName = recipeName;
            this.recipeSize = recipeSize;
            this.shapedRecipe = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(shapedRecipe ? "(shaped)" : "(shapeless)");
            sb.append(output.toString());
            sb.append(" > ");
            int i = 0;
            for (List<ItemStack> list :inputs) {
                sb.append("\nList ").append(i++).append(" ").append("[").append(list.size()).append("]: ");
                for (ItemStack stack: list) {
                    sb.append(stack.toString()).append(" | ");
                }
            }

            return sb.toString();
        }

        public void checkAllConflicting(List<Map.Entry<CraftingRecipeEntry, CraftingRecipeEntry>> entryList){
            for (CraftingRecipeEntry oneEntry :craftingRecipeEntries) {
                if (oneEntry == this) continue;

                if (this.checkConflict(oneEntry)){

                    if (entryList.contains(new AbstractMap.SimpleEntry<>(this, oneEntry)) || entryList.contains(new AbstractMap.SimpleEntry<>(oneEntry, this))){
                        //already in the list
                    }else {
                        entryList.add(new AbstractMap.SimpleEntry<>(this, oneEntry));
                    }
                }
            }
        }

        public boolean checkConflict(CraftingRecipeEntry other){

            if (shapedRecipe && other.shapedRecipe){
                if(width != other.width || height != other.height) return false;

                int overlapping = 0;
                for (int i = 0; i < recipeSize; i++) {
                    List<ItemStack> inputThis = inputs.get(i);
                    List<ItemStack> inputOther = other.inputs.get(i);

                    //region >>> Checks whether both have empty slots
                    // both really empty
                    if (inputThis.isEmpty() && inputOther.isEmpty()) {
                        // CraftTweakerAPI.logInfo("[1]" + this.output.toString() + " has a empty with " + other.output.toString() + " at " + i);
                        overlapping++;
                        continue;
                    }

                    // both ItemStack.Empty
                    if (inputThis.size() == 1 && inputOther.size() == 1){
                        if (inputThis.get(0).isEmpty() && inputOther.get(0).isEmpty()){
                            // CraftTweakerAPI.logInfo("[2]" +this.output.toString() + " has a empty with " + other.output.toString() + " at " + i);
                            overlapping++;
                            continue;
                        }
                    }

                    // other == ItemStack.Empty
                    if (inputThis.isEmpty() && inputOther.size() == 1){
                        if (inputOther.get(0).isEmpty()){
                            // CraftTweakerAPI.logInfo("[3]" +this.output.toString() + " has a empty with " + other.output.toString() + " at " + i);
                            overlapping++;
                            continue;
                        }
                    }

                    // this == ItemStack.Empty
                    if (inputOther.isEmpty() && inputThis.size() == 1){
                        if (inputThis.get(0).isEmpty()){
                            // CraftTweakerAPI.logInfo("[4]" +this.output.toString() + " has a empty with " + other.output.toString() + " at " + i);
                            overlapping++;
                            continue;
                        }
                    }
                    //endregion


                    // has one item which is the same
                    boolean hasOneConflict = false;
                    for (ItemStack stack :inputThis) {

                        for (ItemStack otherStack :inputOther) {

                            if (compareItemStack(stack, otherStack)){
                                hasOneConflict = true;
                                // System.out.println(stack.toString() + " conflicts with " + otherStack.toString()+ ";");
                            }
                        }
                    }
                    if (hasOneConflict) {
                        // System.out.println(this.toString() + " has a Conflict with " + other.toString());
                        overlapping++;
                    }

                }

                // check whether all items are overlapping
                // CraftTweakerAPI.logInfo(this.toString() + " has a Conflict with " + other.toString() + " with " + overlapping);

                if (overlapping == recipeSize){
                    // System.out.println("overlapping = " + overlapping);
                    // System.out.println(this.output.toString() + " has a Conflict with " + other.output.toString());
                    return !compareItemStack(this.output, other.output);
                }
            }
            return false;
        }

        /**
         * Comapres the two itemstacks stacks and even checks for nbt
         */
        public boolean compareItemStack(ItemStack stack1, ItemStack stack2){
            boolean itemsAreSame = stack1.getItem() == stack2.getItem() && stack1.getMetadata() == stack2.getMetadata();
            if (!itemsAreSame) return false;

            if (stack1.hasTagCompound() || stack2.hasTagCompound()) {
                return stack1.hasTagCompound() && stack2.hasTagCompound() && stack1.getTagCompound().equals(stack2.getTagCompound());
            }else {
                return true;
            }
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
    }

    /**
     * Sorter for sorting the recipes according to size of the recipe
     * Sortetness of the list is not really used yet
     */
    private static class CraftRecipeEntryComparator implements Comparator<CraftingRecipeEntry> {

        @Override
        public int compare(CraftingRecipeEntry o1, CraftingRecipeEntry o2) {
            return o1.recipeSize > o2.recipeSize ? +1 : o1.recipeSize < o2.recipeSize ? -1 : 0;
        }
    }

    public static class ConflictingSet{
        public CraftingRecipeEntry entry1;
        public CraftingRecipeEntry entry2;


        public ConflictingSet(){

        }


    }

}
