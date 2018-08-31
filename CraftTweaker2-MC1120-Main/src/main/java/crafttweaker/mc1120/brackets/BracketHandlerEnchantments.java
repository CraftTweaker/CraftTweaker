package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.mc1120.enchantments.MCEnchantmentDefinition;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerEnchantments implements IBracketHandler {
    
    private static final HashMap<String, IEnchantmentDefinition> enchantments = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerEnchantments() {
        updateEnchantmentRegistry();
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerEnchantments.class, "getEnchantment", String.class);
    }
    
    @SuppressWarnings("unused")
    public static IEnchantmentDefinition getEnchantment(String id) {
        IEnchantmentDefinition result = enchantments.get(id);
        if(result == null)
            CraftTweakerAPI.logError("Could not find Enchantment for the id " + id);
        return result;
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() < 3)
            return null;
        if(!(tokens.get(0).getValue().equalsIgnoreCase("enchantment") && tokens.get(1).getValue().equals(":")))
            return null;
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.joining())));
    }
    
    @Override
    public String getRegexMatchingString() {
        return "enchantment:.*";
    }
    
    @Override
    public Class<?> getReturnedClass() {
        return IEnchantmentDefinition.class;
    }
    
    public static void updateEnchantmentRegistry() {
        Enchantment.REGISTRY.getKeys().forEach(key -> enchantments.computeIfAbsent(key.toString(), name -> new MCEnchantmentDefinition(Enchantment.REGISTRY.getObject(key))));
    }
}
