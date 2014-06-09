/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import java.util.HashMap;
import java.util.Map;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ZenNamespaceScript implements IZenNamespace {
	private final IZenCompileEnvironment environment;
	private final Map<String, IZenSymbol> values;
	
	public ZenNamespaceScript(IZenCompileEnvironment environment) {
		this.environment = environment;
		this.values = new HashMap<String, IZenSymbol>();
	}

	@Override
	public IZenCompileEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public IPartialExpression getValue(String name, ZenPosition position) {
		if (values.containsKey(name)) {
			return values.get(name).instance(position);
		} else {
			IZenSymbol symbol = environment.getGlobal(name);
			if (symbol == null) {
				return null;
			} else {
				return symbol.instance(position);
			}
		}
	}

	@Override
	public void putValue(String name, IZenSymbol value) {
		values.put(name, value);
	}
}
