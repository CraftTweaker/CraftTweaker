package minetweaker.mods.ic2;

import ic2.api.item.IC2Items;
import java.util.List;
import minetweaker.IBracketHandler;
import minetweaker.MineTweakerAPI;
import minetweaker.annotations.BracketHandler;
import minetweaker.mc172.item.TweakerItemStack;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.ZenTokener;
import stanhebben.zenscript.expression.ExpressionInt;
import stanhebben.zenscript.expression.ExpressionJavaCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.JavaMethod;
import stanhebben.zenscript.util.ZenPosition;

/**
 * Makes a ic2-item specific item syntax available in the form &lt;item-ic2:name[:meta]&gt;.
 * 
 * @author Stan Hebben
 */
@BracketHandler
public class IC2BracketHandler implements IBracketHandler {
	public static IItemStack getItem(String name, int meta) {
		ItemStack stack = IC2Items.getItem(name);
		stack.setItemDamage(meta);
		return new TweakerItemStack(stack);
	}
	
	private final JavaMethod method;
	
	public IC2BracketHandler() {
		method = MineTweakerAPI.getJavaMethod(IC2BracketHandler.class, "getItem", String.class, int.class);
	}
	
	@Override
	public IZenSymbol resolve(List<Token> tokens) {
		if (tokens.size() >= 5) {
			if (tokens.get(2).getValue().equals("ic2")
					&& tokens.get(0).getValue().equals("item")
					&& tokens.get(1).getValue().equals("-")
					&& tokens.get(3).getValue().equals(":")) {
				String name = tokens.get(4).getValue();
				int meta = 0;
				if (tokens.size() > 6 && tokens.get(5).getValue().equals(":")) {
					if (tokens.get(6).getType() == ZenTokener.T_INTVALUE) {
						meta = Integer.parseInt(tokens.get(6).getValue());
					} else if (tokens.get(6).getValue().equals("*")) {
						meta = OreDictionary.WILDCARD_VALUE;
					} else {
						MineTweakerAPI.logger.logError("Not a valid meta value: " + tokens.get(6).getValue());
					}
				}
				ItemStack item = IC2Items.getItem(name);
				if (item == null) {
					MineTweakerAPI.logger.logError("Not a valid IC2 item: " + name);
					return null;
				} else {
					return new ItemReferenceSymbol(name, meta);
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	// #############################
	// ### Private inner classes ###
	// #############################
	
	private class ItemReferenceSymbol implements IZenSymbol {
		private final String name;
		private final int meta;
		
		public ItemReferenceSymbol(String name, int meta) {
			this.name = name;
			this.meta = meta;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			return new ExpressionJavaCallStatic(
					position,
					method,
					new ExpressionString(position, name),
					new ExpressionInt(position, meta, ZenType.INT));
		}
	}
}
