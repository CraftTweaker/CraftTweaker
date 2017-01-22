/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc1102.brackets;

import minetweaker.*;
import minetweaker.annotations.BracketHandler;
import minetweaker.api.entity.IEntityDefinition;
import minetweaker.mc1102.entity.MCEntityDefinition;
import net.minecraft.entity.EntityList;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.*;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

import java.util.*;

/**
 * @author Jared
 */
@BracketHandler(priority = 100)
public class EntityBracketHandler implements IBracketHandler {
	
	private static final Map<String, IEntityDefinition> entityNames = new HashMap<>();
	private final IJavaMethod method;
	
	public EntityBracketHandler() {
		method = MineTweakerAPI.getJavaMethod(EntityBracketHandler.class, "getEntity", String.class);
	}
	
	@SuppressWarnings("unchecked")
	public static void rebuildEntityRegistry() {
		entityNames.clear();
		MineTweakerAPI.game.getEntities().forEach(ent->{
			entityNames.put(ent.getName().toLowerCase(), ent);
		});
	}
	
	public static IEntityDefinition getEntity(String name) {
		return entityNames.get(name.toLowerCase());
	}
	
	@Override
	public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) {
		if(tokens.size() > 2) {
			if(tokens.get(0).getValue().equals("entity") && tokens.get(1).getValue().equals(":")) {
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
		
		return new EntityReferenceSymbol(environment, valueBuilder.toString());
	}
	
	private class EntityReferenceSymbol implements IZenSymbol {
		
		private final IEnvironmentGlobal environment;
		private final String name;
		
		public EntityReferenceSymbol(IEnvironmentGlobal environment, String name) {
			this.environment = environment;
			this.name = name;
		}
		
		@Override
		public IPartialExpression instance(ZenPosition position) {
			return new ExpressionCallStatic(position, environment, method, new ExpressionString(position, name));
		}
	}
}
