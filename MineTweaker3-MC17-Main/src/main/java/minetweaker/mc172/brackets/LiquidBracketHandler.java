package minetweaker.mc172.brackets;

import java.util.List;
import minetweaker.IBracketHandler;
import minetweaker.annotations.BracketHandler;
import minetweaker.mc172.liquid.MCLiquidStack;
import minetweaker.api.liquid.ILiquidStack;
import minetweaker.runtime.GlobalRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.expression.ExpressionJavaCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
@BracketHandler
public class LiquidBracketHandler implements IBracketHandler {
	public static ILiquidStack getLiquid(String name) {
		Fluid fluid = FluidRegistry.getFluid(name);
		if (fluid != null) {
			return new MCLiquidStack(new FluidStack(fluid, 1));
		} else {
			return null;
		}
	}

	@Override
	public IZenSymbol resolve(List<Token> tokens) {
		if (tokens.size() > 2) {
			if (tokens.get(0).getValue().equals("liquid") && tokens.get(1).getValue().equals(":")) {
				return find(tokens, 2, tokens.size());
			}
		}
		
		return null;
	}
	
	private IZenSymbol find(List<Token> tokens, int startIndex, int endIndex) {
		StringBuilder valueBuilder = new StringBuilder();
		for (int i = startIndex; i < endIndex; i++) {
			Token token = tokens.get(i);
			valueBuilder.append(token.getValue());
		}
		
		Fluid fluid = FluidRegistry.getFluid(valueBuilder.toString());
		if (fluid != null) {
			return new LiquidReferenceSymbol(valueBuilder.toString());
		}
		
		return null;
	}
	
	private class LiquidReferenceSymbol implements IZenSymbol {
		private final String name;
		
		public LiquidReferenceSymbol(String name) {
			this.name = name;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			JavaMethod method = JavaMethod.get(
					GlobalRegistry.getTypeRegistry(),
					LiquidBracketHandler.class,
					"getLiquid",
					String.class);
			
			return new ExpressionJavaCallStatic(
					position,
					method,
					new ExpressionString(position, name));
		}
	}
}
