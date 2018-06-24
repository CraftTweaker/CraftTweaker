package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.world.IBiome;
import crafttweaker.mc1120.item.MCItemStack;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.world.biome.Biome;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.*;

/**
 * @author Jared
 */
@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerBiome implements IBracketHandler {
    
    private static final Map<String, IBiome> biomeNames = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerBiome() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerBiome.class, "getBiome", String.class);
    }
    
    public static void rebuildBiomeRegistry() {
        biomeNames.clear();
        CraftTweakerAPI.game.getBiomes().forEach(iBiome -> biomeNames.put(iBiome.getId().split(":")[1], iBiome));
    }
    
    @SuppressWarnings("unused")
    public static IBiome getBiome(String name) {
        return biomeNames.get(name);
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equals("biome") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }
        
        return null;
    }
    
    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for(int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, valueBuilder.toString()));
    }
    
    @Override
    public String getRegexMatchingString() {
        return "biome:.*";
    }
    
    @Override
    public Class<?> getReturnedClass() {
        return IEntityDefinition.class;
    }
}
