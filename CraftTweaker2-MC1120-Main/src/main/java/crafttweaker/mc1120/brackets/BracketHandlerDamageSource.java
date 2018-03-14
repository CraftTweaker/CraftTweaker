package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.mc1120.damage.MCDamageSource;
import crafttweaker.mc1120.damage.expand.MCDamageSourceExpand;
import crafttweaker.zenscript.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.*;

import java.util.List;

@BracketHandler(priority = 10)
@ZenRegister
public class BracketHandlerDamageSource implements IBracketHandler {
    
    private final IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), MCDamageSource.class, "createOfType", String.class);
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens == null || tokens.size() < 3 || !tokens.get(0).getValue().equalsIgnoreCase("damageSource"))
            return null;
        String name = tokens.get(3).getValue();
        switch(name) {
            case "IN_FIRE":
            case "LIGHTNING_BOLT":
            case "ON_FIRE":
            case "LAVA":
            case "HOT_FLOOR":
            case "IN_WALL":
            case "CRAMMING":
            case "DROWN":
            case "STARVE":
            case "CACTUS":
            case "FALL":
            case "FLY_INTO_WALL":
            case "OUT_OF_WORLD":
            case "GENERIC":
            case "MAGIC":
            case "WITHER":
            case "ANVIL":
            case "FALLING_BLOCK":
            case "DRAGON_BREATH":
            case "FIREWORKS":
                return makeSymbolSwitched(name, environment);
            default:
                return makeSymbol(name, environment);
        }
    }
    
    
    private IZenSymbol makeSymbolSwitched(String name, IEnvironmentGlobal environment) {
        return position -> new ExpressionCallStatic(position, environment, JavaMethod.get(environment.getEnvironment().getTypeRegistry(), MCDamageSourceExpand.class, name));
    }
    
    private IZenSymbol makeSymbol(String name, IEnvironmentGlobal environment) {
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
    }
}