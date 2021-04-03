package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.potions.IPotionType;
import crafttweaker.zenscript.IBracketHandler;
import net.minecraft.potion.PotionType;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerPotionType implements IBracketHandler {

    private final IJavaMethod method;

    public BracketHandlerPotionType() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerPotionType.class, "getFromString", String.class);
    }

    public static IPotionType getFromString(String id) {
        IPotionType result = CraftTweakerMC.getIPotionType(PotionType.getPotionTypeForName(id));
        if(result == null)
            CraftTweakerAPI.logError("Could not find PotionType for the id " + id);
        return result;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equalsIgnoreCase("potiontype") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, tokens.size());
            }
        }
        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for(int i = 2; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, valueBuilder.toString()));
    }

    @Override
    public String getRegexMatchingString() {
        return "potiontype:.*";
    }

    @Override
    public Class<?> getReturnedClass() {
        return IPotionType.class;
    }
}