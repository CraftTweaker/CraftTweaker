package crafttweaker.mc1120.commands;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.mc1120.brackets.BracketHandlerItem;
import crafttweaker.mc1120.data.NBTConverter;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

import static crafttweaker.mc1120.commands.SpecialMessagesChat.*;

/**
 * @author BloodWorkXGaming
 */
public class NamesCommand extends CraftTweakerCommand {
    
    private ArrayList<String> subCommands;
    
    public NamesCommand() {
        super("names");
    }
    
    @Override
    protected void init() {
        setDescription(getClickableCommandText("\u00A72/ct names", "/ct names", true), getNormalMessage(" \u00A73Outputs a list of all item names in the game to the CraftTweaker log"), getClickableCommandText(" \u00A7a/ct names desc", "/ct names desc", true), getNormalMessage("  \u00A7bAdds the Display name of the Item to the output"));
        
        subCommands = new ArrayList<>(2);
        for(NameCommandParams para : NameCommandParams.values()) {
            if(para == NameCommandParams.REGISTRY_NAME)
                continue;
            subCommands.add(para.getCommandEquivalent());
        }
    }
    
    @Override
    public void executeCommand(MinecraftServer server, ICommandSender sender, String[] args) {
        List<NameCommandParams> paramList = new ArrayList<>();
        paramList.add(NameCommandParams.REGISTRY_NAME);
        
        for(String arg : args) {
            for(NameCommandParams para : NameCommandParams.values()) {
                if(para.getCommandEquivalent().equals(arg)) {
                    paramList.add(para);
                    break;
                }
            }
            
        }
        
        List<Item> items = new ArrayList<>(BracketHandlerItem.getItemNames().values());
        items.sort(Commands.ITEM_COMPARATOR);
        
        // Lists what the syntax of the csv is
        CraftTweakerAPI.logCommand("List of all registered Items:");
        StringBuilder syntaxStringBuilder = new StringBuilder();
        HashMap<NameCommandParams, Integer> timesUsed = new HashMap<>();
        for(NameCommandParams param : paramList) {
            if(param == NameCommandParams.REGISTRY_NAME) {
                syntaxStringBuilder.append("\"").append(param.toString()).append("\"");
            } else {
                syntaxStringBuilder.append(",\"").append(param.toString());
                
                // Adds a 2 (3, 4, ...) when the key already exists
                if(!timesUsed.containsKey(param)) {
                    timesUsed.put(param, 1);
                } else {
                    int t = timesUsed.get(param) + 1;
                    timesUsed.replace(param, t);
                    syntaxStringBuilder.append(t);
                }
                syntaxStringBuilder.append("\"");
            }
        }
        CraftTweakerAPI.logCommand(syntaxStringBuilder.toString());
        
        
        int totalAmount = 0;
        
        for(Item item : items) {
            if(item != null) {
                
                // gets list of subitems
                NonNullList<ItemStack> list = NonNullList.create();
                item.getSubItems(CreativeTabs.SEARCH, list);
                
                
                if(list.size() > 0) {
                    for(ItemStack stack : list) {
                        CraftTweakerAPI.logCommand(createOutputForItem(stack, paramList));
                        totalAmount++;
                    }
                } else {
                    // gets ItemName when it is not in any creative window
                    ItemStack stack = new ItemStack(item, 1, 0);
                    CraftTweakerAPI.logCommand(createOutputForItem(stack, paramList));
                }
            }
        }
        CraftTweakerAPI.logCommand("A total of " + items.size() + " unique Items registered by CraftTweaker.");
        CraftTweakerAPI.logCommand("A total of " + totalAmount + " effective Items registered by CraftTweaker.");
        sender.sendMessage(getLinkToCraftTweakerLog("List generated", sender));
    }
    
    @Override
    public List<String> getSubSubCommand(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> currentPossibleCommands = new ArrayList<>();
        
        if(args.length <= 0) {
            return new ArrayList<>(subCommands);
        }
        
        // First sub-command
        for(String cmd : subCommands) {
            if(cmd.startsWith(args[args.length - 1])) {
                currentPossibleCommands.add(cmd);
            }
        }
        return currentPossibleCommands;
        
    }
    
    public static String prepareForCSV(String string){
        return string.replace("\"", "\"\"");
    }
    
    /**
     * Creates the output for the given
     *
     * @param stack     {@link ItemStack} which the data is going to be dumped for
     * @param paramList List of how it should be formatted
     *
     * @return String of data that is going to be put in the log
     */
    protected String createOutputForItem(ItemStack stack, List<NameCommandParams> paramList) {
        
        StringBuilder sb = new StringBuilder();
        
        for(NameCommandParams type : paramList) {
            
            if(type == NameCommandParams.REGISTRY_NAME) {
                sb.append("\"");
            } else {
                sb.append(",\"");
            }
            
            sb.append(type.getInformation(stack)).append("\"");
        }
        
        
        return prepareForCSV(sb.toString());
    }
    
    /**
     * For easy copy paste of the command
     * /ct names display unloc maxstack maxuse maxdamage rarity repaircost damageable repairable creativetabs
     */
    @SuppressWarnings("unused")
    private enum NameCommandParams {
        
        
        REGISTRY_NAME("23498z9384z9283z49", stack -> {
            StringBuilder sb = new StringBuilder();
            
            sb.append("<").append(stack.getItem().getRegistryName() != null ? stack.getItem().getRegistryName().toString() : "[NO REGISTRY NAME] {" + stack.getItem().getClass().toString() + "}").append(stack.getMetadata() == 0 ? "" : ":" + stack.getMetadata()).append(">");
            if(stack.serializeNBT().hasKey("tag")) {
                String nbt = NBTConverter.from(stack.serializeNBT().getTag("tag"), false).toString();
                if(nbt.length() > 0)
                    sb.append(".withTag(").append(nbt).append(")");
            }
            
            return sb.toString();
        }),
        
        DISPLAY_NAME("display", ItemStack::getDisplayName),
        
        MOD_ID("modid", stack -> {
            ResourceLocation resLoc = stack.getItem().getRegistryName();
            return resLoc != null ? stack.getItem().getRegistryName().getResourceDomain() : "[NO REGISTRY NAME] {" + stack.getItem().getClass().toString() + "}";
        }),
        
        UNLOCALIZED("unloc", ItemStack::getUnlocalizedName),
        
        MAX_STACK_SIZE("maxstack", stack -> Integer.toString(stack.getMaxStackSize())),
        
        MAX_ITEM_USE_DURATION("maxuse", stack -> Integer.toString(stack.getMaxItemUseDuration())),
        
        MAX_ITEM_DAMAGE("maxdamage", stack -> Integer.toString(stack.getMaxDamage())),
    
        RARITY("rarity", stack -> stack.getRarity().rarityName),
        
        REPAIR_COST("repaircost", stack -> Integer.toString(stack.getRepairCost())),
        
        DAMAGEABLE("damageable", stack -> Boolean.toString(stack.getItem().isDamageable())),
        
        REPAIRABLE("repairable", stack -> Boolean.toString(stack.getItem().isRepairable())),
        
        CREATIVE_TABS("creativetabs", stack -> {
            StringBuilder tabString = new StringBuilder();
            
            tabString.append("[");
            for(CreativeTabs creativeTabs : stack.getItem().getCreativeTabs()) {
                if (tabString.length() != 1) tabString.append(", ");
                if (creativeTabs != null)tabString.append(creativeTabs.getTabLabel());
            }
            tabString.append("]");
            
            return tabString.toString();
        });
    
    
        /**
         * Stuff to make this enum work
         */
        private String commandEquivalent;
        private Function<ItemStack, String> informationProducer;
        
        NameCommandParams(String commandEquivalent, Function<ItemStack, String> informationProducer) {
            this.commandEquivalent = commandEquivalent;
            this.informationProducer = informationProducer;
        }
        
        public String getCommandEquivalent() {
            return commandEquivalent;
        }
        
        public String getInformation(ItemStack stack) {
            return informationProducer.apply(stack);
        }
    }
    
}
