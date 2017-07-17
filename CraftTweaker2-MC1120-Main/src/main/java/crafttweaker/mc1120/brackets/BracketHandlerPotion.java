package crafttweaker.mc1120.brackets;


import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.*;
import crafttweaker.api.potions.IPotion;
import crafttweaker.mc1120.potions.MCPotion;
import crafttweaker.zenscript.*;
import net.minecraft.potion.Potion;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.*;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerPotion implements IBracketHandler {
    
    private static final Map<String, Potion> potionNames = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerPotion() {
        this.method = CraftTweakerAPI.getJavaMethod(BracketHandlerPotion.class, "getPotion", String.class);
    }
    
    public static Map<String, Potion> getPotionNames() {
        return potionNames;
    }
    
    public static void rebuildRegistry() {
        potionNames.clear();
        Potion.REGISTRY.getKeys().forEach(key ->{
            potionNames.put(key.getResourcePath(), Potion.getPotionFromResourceLocation(key.toString()));
        });
    }
    
    public static IPotion getPotion(String name) {
        Potion pot = potionNames.get(name);
        if(pot == null){
            return null;
        }
        return new MCPotion(pot);
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        return tokens.size() > 2 && tokens.get(0).getValue().equals("potion") && tokens.get(1).getValue().equals(":") ? this.find(environment, tokens, 2, tokens.size()) : null;
    }
    
    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        
        for(int i = startIndex; i < endIndex; ++i) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }
        
        return new PotionReferenceSymbol(environment, valueBuilder.toString());
    }
    
    private class PotionReferenceSymbol implements IZenSymbol {
        
        private final IEnvironmentGlobal environment;
        private final String name;
        
        public PotionReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }
        
        public IPartialExpression instance(ZenPosition position) {
            return new ExpressionCallStatic(position, this.environment, BracketHandlerPotion.this.method, new ExpressionString(position, this.name));
        }
    }
}