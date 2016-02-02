package minetweaker.mc1710.brackets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.mc1710.liquid.MCLiquidStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.runtime.GlobalRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
@BracketHandler
public class LiquidBracketHandler implements IBracketHandler {
    private static final Map<String, Fluid> fluidNames = new HashMap<String, Fluid>();

    @SuppressWarnings("unchecked")
    public static void rebuildLiquidRegistry() {
        fluidNames.clear();
        for (String fluidName : FluidRegistry.getRegisteredFluids().keySet()) {
            fluidNames.put(fluidName.replace(" ", ""), FluidRegistry.getFluid(fluidName));
        }
    }

    public static ILiquidStack getLiquid(String name) {
        Fluid fluid = fluidNames.get(name);
        if (fluid != null) {
            return new MCLiquidStack(new FluidStack(fluid, 1));
        } else {
            return null;
        }
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
        if (tokens.size() > 2) {
            if (tokens.get(0).getValue().equals("liquid") && tokens.get(1).getValue().equals(":")) {
                return find(environment, tokens, 2, tokens.size());
            }
        }

        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        Fluid fluid = fluidNames.get(valueBuilder.toString());
        if (fluid != null) {
            return new LiquidReferenceSymbol(environment, valueBuilder.toString());
        }

        return null;
    }

    private class LiquidReferenceSymbol implements IZenSymbol {
        private final IEnvironmentGlobal environment;
        private final String name;

        public LiquidReferenceSymbol(IEnvironmentGlobal environment, String name) {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition position) {
            IJavaMethod method = JavaMethod.get(GlobalRegistry.getTypeRegistry(), LiquidBracketHandler.class, "getLiquid", String.class);

            return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
        }
    }
}
