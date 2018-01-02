package crafttweaker.mc1120.brackets;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.creativetabs.ICreativeTab;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.zenscript.IBracketHandler;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.List;
import java.util.stream.Collectors;

@BracketHandler
@ZenRegister
public class BracketHandlerCreativetab implements IBracketHandler {
    
    private final IJavaMethod method;
    
    public BracketHandlerCreativetab() {
        method = CraftTweakerAPI.getJavaMethod(BracketHandlerCreativetab.class, "getTabFromString", String.class);
    }
    
    public static ICreativeTab getTabFromString(String name) {
        for(ICreativeTab tab : CraftTweakerMC.creativeTabs) {
            if(tab.getTabLabel().equalsIgnoreCase(name))
                return tab;
        }
        CraftTweakerAPI.logError("Couldn't find creative tab with name " + name);
        return null;
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() < 3)
            return null;
        if(!(tokens.get(0).getValue().equalsIgnoreCase("creativetab") && tokens.get(1).getValue().equals(":")))
            return null;
        return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, tokens.subList(2, tokens.size()).stream().map(Token::getValue).collect(Collectors.joining())));
    }
    
}
