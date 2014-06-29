/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc172.brackets;

import java.util.List;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import static minetweaker.api.minecraft.MineTweakerMC.getOreDict;
import minetweaker.api.oredict.IOreDictEntry;
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
public class OreBracketHandler implements IBracketHandler {
	public static IOreDictEntry getOre(String name) {
		return getOreDict(name);
	}
	
	@Override
	public IZenSymbol resolve(List<Token> tokens) {
		if (tokens.size() > 2) {
			if (tokens.get(0).getValue().equals("ore") && tokens.get(1).getValue().equals(":")) {
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
		
		return new OreReferenceSymbol(valueBuilder.toString());
	}
	
	private class OreReferenceSymbol implements IZenSymbol {
		private final String name;
		
		public OreReferenceSymbol(String name) {
			this.name = name;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			JavaMethod method = MineTweakerAPI.getJavaMethod(
					OreBracketHandler.class,
					"getOre",
					String.class);
			
			return new ExpressionJavaCallStatic(
					position,
					method,
					new ExpressionString(position, name));
		}
	}
}
