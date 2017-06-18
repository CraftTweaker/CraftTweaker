package crafttweaker.mc1120.brackets;

import crafttweaker.*;
import crafttweaker.annotations.*;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.mc1120.oredict.MCOreDictEntry;
import crafttweaker.zenscript.GlobalRegistry;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Stan
 */
@BracketHandler(priority = 100)
@ZenRegister
public class OreBracketHandler implements IBracketHandler {

    public static IOreDictEntry getOre(String name) {
        return new MCOreDictEntry(name);
    }

    public static List<IOreDictEntry> getOreList(String wildcardName) {
        List<IOreDictEntry> result = new ArrayList<>();
        Pattern wildcardPattern = Pattern.compile(wildcardName.replaceAll("\\*", ".+"));

        for(IOreDictEntry someOreDict : CraftTweakerAPI.oreDict.getEntries()) {
            String oreDictName = someOreDict.getName();
            if(wildcardPattern.matcher(oreDictName).matches()) {
                result.add(getOre(oreDictName));
            }
        }

        return result;
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if(tokens.get(0).getValue().equals("ore") && tokens.get(1).getValue().equals(":")) {
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

        return new OreReferenceSymbol(environment, valueBuilder.toString());
    }

    private class OreReferenceSymbol implements IZenSymbol {

        private final IEnvironmentGlobal environment;
        private final String name;

        public OreReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypes(), OreBracketHandler.class, name.contains("*") ? "getOreList" : "getOre", String.class);

            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
        }
    }
}
