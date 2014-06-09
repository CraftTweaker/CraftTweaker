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
public class ZenNamespaceLocal implements IZenNamespace {
	private final IZenNamespace parent;
	private final Map<String, IZenSymbol> values;
	
	public ZenNamespaceLocal(IZenNamespace parent) {
		this.parent = parent;
		this.values = new HashMap<String, IZenSymbol>();
	}

	@Override
	public IZenCompileEnvironment getEnvironment() {
		return parent.getEnvironment();
	}

	@Override
	public IPartialExpression getValue(String name, ZenPosition position) {
		if (values.containsKey(name)) {
			return values.get(name).instance(position);
		} else {
			return parent.getValue(name, position);
		}
	}

	@Override
	public void putValue(String name, IZenSymbol value) {
		values.put(name, value);
	}
}
