/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.symbols;

import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.expression.partial.PartialStaticGenerated;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stan
 */
public class SymbolZenStaticMethod implements IZenSymbol {
	private final String className;
	private final String methodName;
	private final String signature;
	private final ZenType[] argumentTypes;
	private final ZenType returnType;
	
	public SymbolZenStaticMethod(String className, String methodName, String signature, ZenType[] argumentTypes, ZenType returnType) {
		this.className = className;
		this.methodName = methodName;
		this.signature = signature;
		this.argumentTypes = argumentTypes;
		this.returnType = returnType;
	}

	@Override
	public IPartialExpression instance(ZenPosition position) {
		return new PartialStaticGenerated(position, className, methodName, signature, argumentTypes, returnType);
	}
}
