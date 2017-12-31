package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.mc1120.item.MCItemUtils;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionInt;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static crafttweaker.api.minecraft.CraftTweakerMC.getIItemStackWildcardSize;

/**
 * @author Stan
 */
@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerItem implements IBracketHandler {

    private static final Map<String, Item> itemNames = new HashMap<>();
    private static final Map<String, Block> blockNames = new HashMap<>();

    private final IZenSymbol symbolAny;
    private final IJavaMethod method;

    public BracketHandlerItem() {
        symbolAny = CraftTweakerAPI.getJavaStaticFieldSymbol(IngredientAny.class, "INSTANCE");
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerItem.class, "getItem", String.class, int.class);
    }

    public static Map<String, Item> getItemNames() {
        return itemNames;
    }

    public static Map<String, Block> getBlockNames() {
        return blockNames;
    }

    @SuppressWarnings("unchecked")
    public static void rebuildItemRegistry() {
        itemNames.clear();
        blockNames.clear();
        for (ResourceLocation itemName : Item.REGISTRY.getKeys()) {
            String domain = itemName.toString().replace(" ", "").replace("'", "");
            itemNames.put(domain, Item.REGISTRY.getObject(itemName));
        }
        for (ResourceLocation blockName : Block.REGISTRY.getKeys()) {
            String domain = blockName.toString().replace(" ", "").replace("'", "");
            blockNames.put(domain, Block.REGISTRY.getObject(blockName));
        }

        MCItemUtils.createItemList();
    }

    public static IItemStack getItem(String name, int meta) {
        // Item item = (Item) Item.itemRegistry.getObject(name);
        Item item = itemNames.get(name);
        if (item != null) {
            return getIItemStackWildcardSize(item, meta);
        } else if (blockNames.containsKey(name)) {
            return getIItemStackWildcardSize(new ItemStack(blockNames.get(name), 1, meta));
        } else {
            return null;
        }
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        // any symbol
        if (tokens.size() == 1 && tokens.get(0).getValue().equals("*")) {
            return symbolAny;
        }

        // detect special cases:
        // item: at the start means item-specific syntax
        // :xxx with xxx an integer means sub-item syntax
        // :* means any-subitem-syntax
        int fromIndex = 0;
        int toIndex = tokens.size();
        int meta = 0;

        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("item") && tokens.get(1).getValue().equals(":")) {
                fromIndex = 2;
            }
            if (tokens.get(tokens.size() - 1).getType() == ZenTokener.T_INTVALUE && tokens.get(tokens.size() - 2).getValue().equals(":")) {
                toIndex = tokens.size() - 2;
                meta = Integer.parseInt(tokens.get(tokens.size() - 1).getValue());
            } else if (tokens.get(tokens.size() - 1).getValue().equals("*") && tokens.get(tokens.size() - 2).getValue().equals(":")) {
                toIndex = tokens.size() - 2;
                meta = OreDictionary.WILDCARD_VALUE;
            }
        }

        return find(environment, tokens, fromIndex, toIndex, meta);
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex, int meta) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        String itemName = valueBuilder.toString();
        if (itemNames.containsKey(itemName) || blockNames.containsKey(itemName)) {
            return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, valueBuilder.toString()), new ExpressionInt(position, meta, ZenType.INT));
        }

        return null;
    }

}
