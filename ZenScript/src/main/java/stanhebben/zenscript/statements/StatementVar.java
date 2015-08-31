/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.statements;

import stanhebben.zenscript.compiler.IEnvironmentMethod;
import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.parser.expression.ParsedExpression;
import stanhebben.zenscript.symbols.SymbolLocal;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.ZenTypeAny;
import stanhebben.zenscript.util.ZenPosition;

/**
 *
 * @author Stanneke
 */
public class StatementVar extends Statement {
	private final String name;
	private final ZenType type;
	private final ParsedExpression initializer;
	private final boolean isFinal;

	public StatementVar(ZenPosition position, String name, ZenType type, ParsedExpression initializer, boolean isFinal) {
		super(position);

		this.name = name;
		this.type = type;
		this.initializer = initializer;
		this.isFinal = isFinal;
	}

	@Override
	public void compile(IEnvironmentMethod environment) {
		environment.getOutput().position(getPosition());

		Expression cInitializer = initializer == null
				? null
				: initializer.compile(environment, type).eval(environment);
		ZenType cType = type == null ? (cInitializer == null ? ZenTypeAny.INSTANCE : cInitializer.getType()) : type;
		SymbolLocal symbol = new SymbolLocal(cType, isFinal);

		environment.putValue(name, symbol, getPosition());

		if (cInitializer != null) {
			cInitializer.compile(true, environment);
			environment.getOutput().store(symbol.getType().toASMType(), environment.getLocal(symbol));
		}
	}
}
