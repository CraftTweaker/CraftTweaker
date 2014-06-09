/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.expression;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class ExpressionLocalGet extends Expression {
	private final SymbolLocal variable;
	
	public ExpressionLocalGet(ZenPosition position, SymbolLocal variable) {
		super(position);
		
		this.variable = variable;
	}
	
	@Override
	public ZenType getType() {
		return variable.getType();
	}

	@Override
	public void compile(boolean result, IEnvironmentMethod environment) {
		int local = environment.getLocal(variable);
		environment.getOutput().load(variable.getType().toASMType(), local);
	}
}
