package crafttweaker.mc1120.brackets;

import crafttweaker.zenscript.IBracketHandler;
import crafttweaker.annotations.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.mc1120.liquid.MCLiquidStack;
import crafttweaker.zenscript.GlobalRegistry;
import net.minecraftforge.fluids.*;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.*;

import java.util.*;

/**
 * @author Stan
 */
@BracketHandler(priority = 100)
@ZenRegister
public class BracketHandlerLiquid implements IBracketHandler {
    
    private static final Map<String, Fluid> fluidNames = new HashMap<>();
    private final IJavaMethod method;
    
    public BracketHandlerLiquid() {
        method = JavaMethod.get(GlobalRegistry.getTypes(), BracketHandlerLiquid.class, "getLiquid", String.class);
    }
    
    @SuppressWarnings("unchecked")
    public static void rebuildLiquidRegistry() {
        fluidNames.clear();
        for(String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
            fluidNames.put(fluidName.replace(" ", ""), FluidRegistry.getFluid(fluidName));
        }
    }
    
    public static ILiquidStack getLiquid(String name) {
        Fluid fluid = fluidNames.get(name);
        if(fluid != null) {
            return new MCLiquidStack(new FluidStack(fluid, 1));
        } else {
            return null;
        }
    }
    
    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if(tokens.size() > 2) {
            if((tokens.get(0).getValue().equals("liquid") || tokens.get(0).getValue().equals("fluid")) && tokens.get(1).getValue().equals(":")) {
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
        
        Fluid fluid = fluidNames.get(valueBuilder.toString());
        if(fluid != null) {
            return position -> new ExpressionCallStatic(position, environment, method, new ExpressionString(position, valueBuilder.toString()));
        }
        
        return null;
    }
    
    @Override
    public Class<?> getReturnedClass() {
        return ILiquidStack.class;
    }
    
    @Override
    public String getRegexMatchingString() {
        return "(fluid|liquid):.*";
    }
}
