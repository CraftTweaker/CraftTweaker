package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.entity.IEntityDefinition;
import crafttweaker.api.world.IBiomeType;
import crafttweaker.mc1120.world.MCBiomeType;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraftforge.common.BiomeDictionary;
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
public class BracketHandlerBiomeType implements IBracketHandler {
    
    public static final Map<String, IBiomeType> biomeTypes = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerBiomeType() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerBiomeType.class, "getBiomeType", String.class);
    }
    
    public static void rebuildBiomeTypeRegistry() {
        biomeTypes.clear();
        Collection<BiomeDictionary.Type> types = BiomeDictionary.Type.getAll();
        for(BiomeDictionary.Type t : types) {
            biomeTypes.put(t.getName().toLowerCase(), new MCBiomeType(t));
        }
    }
    
    public static IBiomeType getBiomeType(String name) {
        return biomeTypes.get(name.toLowerCase());
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equals("biomeTypes") && tokens.get(1).getValue().equals(":")) {
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
        return "biomeTypes:.*";
    }
    
    @Override
    public Class<?> getReturnedClass() {
        return IEntityDefinition.class;
    }
}
